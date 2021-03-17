package com.android.shawnclisby.javatilt;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Formatter;

public class Utils {
    private static final String DATA = "data";

    static String getJsonFromAssets(Context context, String fileName) throws JSONException {
        JSONObject data;
        try (InputStream is = context.getAssets().open(fileName)) {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String jsonString = new String(buffer, StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(jsonString);
            data = jsonObject.getJSONObject(DATA);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }

        return data.toString(1);
    }

    @NonNull
    static String formatMiles(float miles) {
        return new Formatter().format("%d mi.", (int) miles).toString();
    }

    static int getCurrentYear() {
        final Calendar cldr = Calendar.getInstance();
        return cldr.get(Calendar.YEAR);
    }

    static int getCurrentMonth() {
        final Calendar cldr = Calendar.getInstance();
        return cldr.get(Calendar.MONTH);
    }

    static int getCurrentDay() {
        final Calendar cldr = Calendar.getInstance();
        return cldr.get(Calendar.DAY_OF_MONTH);
    }

    static String formatCity(String city) {
        String tempCity = city.toLowerCase();
        return tempCity.substring(0, 1).toUpperCase() + tempCity.substring(1);
    }

    static String formatWeight(int value) {
        return new Formatter().format("%dlbs.", value).toString();
    }

    static Float toDps(int value, Context context) {
        Resources resources = context.getResources();
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                resources.getDisplayMetrics()
        );
    }
}
