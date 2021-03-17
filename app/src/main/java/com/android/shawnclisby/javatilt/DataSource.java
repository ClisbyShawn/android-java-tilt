package com.android.shawnclisby.javatilt;

import android.content.Context;

import com.android.shawnclisby.javatilt.models.responses.ResponseCommodity;
import com.android.shawnclisby.javatilt.models.responses.ResponseLoad;
import com.google.gson.Gson;

import org.json.JSONException;

public class DataSource {

    private static final DataSource ourInstance = new DataSource();
    public static ResponseCommodity responseCommodity;
    public static ResponseLoad responseLoad;


    static DataSource getInstance() {
        return ourInstance;
    }

    private DataSource() {
    }

    public static void initializeData(Context context) {
        try {
            String commoditiesJson = Utils.getJsonFromAssets(context, "commodity.json");
            String loadsJson = Utils.getJsonFromAssets(context, "loads.json");

            Gson gson = new Gson();
            responseCommodity = gson.fromJson(commoditiesJson, ResponseCommodity.class);
            responseLoad = gson.fromJson(loadsJson, ResponseLoad.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
