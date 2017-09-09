package com.zerren.chainreaction.utility;

/**
 * Created by Zerren on 3/9/2015.
 */
public final class CRMath {

    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * (5/9);
    }

    public static double fahrenheitToKelvin(double fahrenheit) {
        return fahrenheitToCelsius(fahrenheit) + 273.15;
    }

    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * (9/5)) + 32;
    }

    public static double celsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }

    public static double kelvinToFahrenheit(double kelvin) {
        return celsiusToFahrenheit(kelvinToCelsius(kelvin));
    }

    public static double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    public static boolean isWithin(float var, float lower, float upper) {
        return var > lower && var <= upper;
    }

    public static boolean isWithin(int var, int lower, int upper) {
        return var > lower && var <= upper;
    }

    public static double reducedByPercent(double number, double percent) {
        return number - (number * percent);
    }

    public static double increasedByPercent(double original, double percent) {
        return original * (1 + (percent));
    }

    public static double getFuelLevelAfterOneDayDecay(double fuelLevel, int halfLifeInDays) {
        double halfLifeMod = (double)1 / halfLifeInDays;
        return fuelLevel * (Math.pow(0.5, halfLifeMod));
    }
}
