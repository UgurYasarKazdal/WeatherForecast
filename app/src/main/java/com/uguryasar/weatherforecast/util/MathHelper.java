package com.uguryasar.weatherforecast.util;

public class MathHelper {
    private static int kelvinToCelsius(Double _kelvinValue) {
        return ((int) (_kelvinValue - 273.15));
    }

    public static String getCelsiusStr(Double _kelvinValue) {
        return kelvinToCelsius(_kelvinValue) + "°";
    }

}