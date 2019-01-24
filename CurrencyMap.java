package com.azranozeri.finalproject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyMap {
    private Map<String, Currency> currencyMap;

    CurrencyMap(){
        currencyMap = new HashMap<>();
    }

    public void setMap(List<Currency> list){
        for(Currency curr: list){
            currencyMap.put(curr.getCode(), curr);
        }
        currencyMap.put("NIS", new Currency("New Israeli Shekel", 1, "NIS", "Israel", 1, 1 ));
    }

    public Map<String, Currency> getCurrencyMap(){
        return currencyMap;
    }
}
