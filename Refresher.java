package com.azranozeri.finalproject;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class Refresher implements Runnable {
    private CurrencyGUI gui;
    private XMLParser parser;

    public Refresher(CurrencyGUI gui){
        setGui(gui);
        setParser(new XMLParser(gui));
    }

    @Override
    public void run() {
        Runnable runnable = () -> gui.createList(parser.getCurrencyList(parser.getNodeList()));

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

    private void setGui(CurrencyGUI gui) {
        this.gui = gui;
    }

    private void setParser(XMLParser parser){
        this.parser = parser;
    }
}
