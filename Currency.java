package com.azranozeri.finalproject;

import static com.azranozeri.finalproject.CurrencyDemo.logger;

/**
 * This class holds all information regarding a Currency.
 * Each Object should represent one Currency (e.g USD, GBP etc)
 */
public class Currency {
    /**
     * The name of the Currency (e.g Dollar)
     */
    private String name;

    /**
     * The amount that states the conversion ratio
     */
    private int unit;

    /**
     * The currency's code (e.g USD)
     */
    private String code;

    /**
     * The name of the country (e.g USA)
     */
    private String countryName;

    /**
     * The conversion rate
     */
    private double rate;

    /**
     * The change in the conversion rate from the last update.
     */
    private double change;

    /**
     * Currency Constructor
     * @param name          e.g Dollar/Pound.
     * @param unit          The amount that is converted: 100 YEN = X.XX Shekels.
     * @param code          e.g USD/GBP.
     * @param countryName   e.g USA/Great Britain
     * @param rate          The conversion rate.
     * @param change        The change in the conversion rate from the last update.
     */
    public Currency(String name, int unit, String code, String countryName ,double rate, double change) {
        setChange(change);
        setCode(code);
        setName(name);
        setRate(rate);
        setUnit(unit);
        setCountryName(countryName);
        logger.info(this.getClass().getName() + " Created");
    }

    /**
     * Getters and Setters.
     * Every setter has it's validators
     */
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

    /**
     * An Override to the toString method
     * @return a String to pring the object
     */
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
