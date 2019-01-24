package com.azranozeri.finalproject;

import java.util.Map;

public class Calculator implements Runnable {
    CurrencyGUI gui;
    CurrencyMap map;

    Calculator(CurrencyGUI gui, CurrencyMap map){
        setGui(gui);
        setMap(map);
    }

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

    @Override
    public void run(){
        String from = (String) gui.getFrom().getSelectedItem();
        String to   = (String) gui.getTo().getSelectedItem();
        Map<String, Currency> cMap = map.getCurrencyMap();
        Currency f = cMap.get(from);
        Currency t = cMap.get(to);

        double amount;
        double total;

        try{
          amount = Double.parseDouble(gui.getAmount().getText());
        }
        catch (NumberFormatException e){
            return;
        }

        double first, second;
        first = f.getRate() * amount / f.getUnit();
        second = t.getRate() / t.getUnit();
        total = first / second;
        String totalString = String.format("%.2f", total);
        gui.getResult().setText("");
        gui.getResult().setText(totalString);
        System.out.println("\n" + from + ": " + to + ": " + "Total:" + " " + total);
    }
}
