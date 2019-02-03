package com.azranozeri.finalproject;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

/**
 * This class implements Runnable to be ran as an independent thread.
 * It's responsibility is to query the external URL (Bank Of Israel Currency Service) on an hourly bases.
 * Every hour, a new XML file is saved, even if no changes were made.
 * It creates a new Runnable which executes a method in CurrencyGUI (creating a new list and refreshing the currency table).
 */
public class Refresher implements Runnable {
    /**
     * a Reference to the CurrencyGUI
     */
    private CurrencyGUI gui;

    /**
     * a Reference to XMLParser to be used in receiving the XML file from the service(BOI) or local file
     */
    private XMLParser parser;

    /**
     *Refresher Constructor.
     * @param gui a Reference to the CurrencyGUI used by the Client.
     * @see   CurrencyGUI
     */
    public Refresher(CurrencyGUI gui){
        setGui(gui);
        setParser(new XMLParser(gui));
    }

    /**
     * An implementation of run() in Runnable.
     * As stated in class description, this method waits for 1 hour before sending a query to BOI service to update the Currency Table.
     */
    @Override
    public void run() {
        Runnable runnable = () -> gui.createList(parser.getCurrencyList(parser.getNodeList()));

        /* Run loop. Waiting for 1 hour before invoking runnable (seen above) */
        while(true){
            try {
                TimeUnit.HOURS.sleep(1);
                SwingUtilities.invokeLater(runnable);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Setter for gui
     * @param gui a Reference to the CurrencyGUI used by the Client.
     */
    private void setGui(CurrencyGUI gui) {
        this.gui = gui;
    }

    /**
     * Setter for parser
     * @param parser a Reference to an XMLParser object.
     */
    private void setParser(XMLParser parser){
        this.parser = parser;
    }
}
