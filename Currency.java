package com.azranozeri.finalproject;

public class Currency {
    private String name;
    private int unit;
    private String code;
    private String countryName;
    private double rate;
    private double change;

    public Currency(){}

    public Currency(String name, int unit, String code, String countryName ,double rate, double change) {
        setChange(change);
        setCode(code);
        setName(name);
        setRate(rate);
        setUnit(unit);
        setCountryName(countryName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.isEmpty()){
            return;
        }
        this.name = name;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        if(unit <= 0){
            return;
        }
        this.unit = unit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if(code == null || code.isEmpty()) {
            return;
        }
        this.code = code;
    }

    public String getCountryName(){
        return countryName;
    }

    public void setCountryName(String countryName){
        if(countryName == null || countryName.isEmpty()){
            return;
        }
        this.countryName = countryName;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        if(rate <= 0){
            return;
        }
        this.rate = rate;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "name = '" + name + '\'' +
                ", unit = " + unit +
                ", code = '" + code + '\'' +
                ", rate = " + rate +
                ", change = " + change +
                '}';
    }
}
