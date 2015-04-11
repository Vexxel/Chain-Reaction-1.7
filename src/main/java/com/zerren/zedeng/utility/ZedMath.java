package com.zerren.zedeng.utility;

/**
 * Created by Zerren on 3/9/2015.
 */
public final class ZedMath {

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
}
