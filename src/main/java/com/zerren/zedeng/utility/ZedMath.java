package com.zerren.zedeng.utility;

/**
 * Created by Zerren on 3/9/2015.
 */
public final class ZedMath {

    //All values are assumed at standard pressure(in Pascal PA) and temperature C
    public static final class water {
        public static final double WATER_BOIL = 100;
        public static final double WATER_FREEZE = 0;
    }

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
}
