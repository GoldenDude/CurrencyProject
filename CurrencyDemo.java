package com.azranozeri.finalproject;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
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
    public static Logger logger;

    static {
        String file = "logs.txt";
        logger = Logger.getLogger(Class.class.getName());

        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();

            logger.addAppender(new FileAppender(new PatternLayout("%-5p [%t]: %m%n"), file));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

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
