package com.azranozeri.finalproject;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.SimpleLayout;

import java.io.IOException;
import java.util.Map;

import static com.azranozeri.finalproject.CurrencyDemo.logger;

/**
 * The class is used to calculate and convert Currencies.
 * Implements Runnable in order to be sent to the EDT Thread (using SwingUtils.invokeLater(Runnable obj) ), as it changes and reads from the GUI.
 */
public class Calculator implements Runnable {
    /**
     * a Reference to the CurrencyGUI used by the client
     */
    private CurrencyGUI gui;

    /**
     * a Reference to the CurrencyMap
     * @see CurrencyMap
     */
    private CurrencyMap map;

    /**
     * Calculator Constructor
     * @param gui   a Reference to the CurrencyGUI used by the client.
     * @param map   a Reference to the CurrencyMap.
     */
    Calculator(CurrencyGUI gui, CurrencyMap map){
        setGui(gui);
        setMap(map);

        logger.info(this.getClass().getName() + " Created");
    }

    /**
     * Getters and Setters
     */
    public CurrencyGUI getGui() {
        return gui;
    }

    public void setGui(CurrencyGUI gui) {
        this.gui = gui;
    }

    public CurrencyMap getMap() {
        return map;
    }

    public void setMap(CurrencyMap map) {
        this.map = map;
    }

    /**
     * The implementation of run()
     * Reads from the CurrencyGUI and changes it.
     * @see CurrencyGUI
     */
    @Override
    public void run(){
        /* Getting the information needed from the GUI */
        String from = (String) gui.getFrom().getSelectedItem();
        String to   = (String) gui.getTo().getSelectedItem();

        /* Getting the currency map from the GUI*/
        Map<String, Currency> cMap = map.getCurrencyMap();
        Currency f = cMap.get(from);
        Currency t = cMap.get(to);

        double amount;
        double total;

        /* If the user did not write anything in the Amount text area in the GUI, an exception will be thrown by parseDouble */
        try{
          amount = Double.parseDouble(gui.getAmount().getText());
        }
        catch (NumberFormatException e){
            return;
        }

        double first, second;

        /* The conversion rate formula. Converts to Shekel first. */
        first = f.getRate() * amount / f.getUnit();
        second = t.getRate() / t.getUnit();
        total = first / second;

        /* Normalizing the result to XX.XX format*/
        String totalString = String.format("%.2f", total);
        gui.getResult().setText("");
        gui.getResult().setText(totalString);
        System.out.println("\n" + from + ": " + to + ": " + "Total:" + " " + total);
    }
}
