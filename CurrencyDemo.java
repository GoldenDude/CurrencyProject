package com.azranozeri.finalproject;
import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * This class runs the program.
 * Creates a new XMLParser with a new CurrencyGUI.
 * Creates a new Refresher and runs it.
 *
 * @see     XMLParser
 * @see     CurrencyGUI
 * @see     Refresher
 */
public class CurrencyDemo {
    public static void main(String[] args){
        /* Creating a new XMLParser */
        XMLParser xmlParser = new XMLParser(new CurrencyGUI());

        try {
            SwingUtilities.invokeAndWait(xmlParser);
            new Refresher(xmlParser.getGui()).run();
        }

        catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
