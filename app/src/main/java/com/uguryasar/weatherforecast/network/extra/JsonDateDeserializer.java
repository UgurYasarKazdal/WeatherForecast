

package com.uguryasar.weatherforecast.network.extra;

import android.text.TextUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.uguryasar.weatherforecast.util.DateUtil;

import java.lang.reflect.Type;
import java.util.Date;

public class JsonDateDeserializer implements JsonDeserializer<Date> {

    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String s = json.getAsJsonPrimitive().getAsString();
//    long l = Long.parseLong(s.substring(6, s.length() - 2));
        if (TextUtils.isEmpty(s)) {
            return null;
        }
        if (s.equals("")) {
            return null;
        }
        return DateUtil.stringToDateObject3(s);
    }
}