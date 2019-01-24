package com.azranozeri.finalproject;
import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class CurrencyDemo {
    public static void main(String[] args){
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
