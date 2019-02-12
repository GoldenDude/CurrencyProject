package com.azranozeri.finalproject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.azranozeri.finalproject.CurrencyDemo.logger;

/**
 * This class holds the Currency map used for converting currencies.
 * @see Currency
 */
public class CurrencyMap {
    /**
     * The Currency map. holds each currency with Currency Code as Key (e.g USD) and the Currency as value.
     */
    private Map<String, Currency> currencyMap;

    /**
     * Currency Constructor
     */
    CurrencyMap(){
        currencyMap = new HashMap<>();
        logger.info(this.getClass().getName() + " Created");
    }

    /**
     * Given a List of Currencies, creates a Map.
     * @param list the Currency List. Received from CurrencyGUI.
     * @see   CurrencyGUI
     */
    public void setMap(List<Currency> list){
        for(Currency curr: list){
            currencyMap.put(curr.getCode(), curr);
        }
        currencyMap.put("NIS", new Currency("New Israeli Shekel", 1, "NIS", "Israel", 1, 1 ));
    }

    /**
     * Getter for the currencyMap.
     * @return a Map of <String, Currency>
     */
    public Map<String, Currency> getCurrencyMap(){
        return currencyMap;
    }
}
