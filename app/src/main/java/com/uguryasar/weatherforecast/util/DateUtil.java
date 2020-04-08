
package com.uguryasar.weatherforecast.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {


    public static Date stringToDateObject3(String _date) {
        if (_date == null) {
            return null;
        }

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.UK);
        Date d1 = null;
        try {
            d1 = format.parse(_date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d1;
    }

}