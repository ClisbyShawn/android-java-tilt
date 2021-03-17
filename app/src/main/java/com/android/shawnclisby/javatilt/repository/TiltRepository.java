package com.android.shawnclisby.javatilt.repository;

import android.annotation.SuppressLint;

import com.android.shawnclisby.javatilt.DataSource;
import com.android.shawnclisby.javatilt.models.Load;
import com.android.shawnclisby.javatilt.models.responses.ResponseCommodity;
import com.android.shawnclisby.javatilt.models.responses.ResponseLoad;

import java.util.List;

public class TiltRepository {

    private final ResponseCommodity responseCommodities;
    private final ResponseLoad responseLoad;

    public TiltRepository() {
        responseCommodities = DataSource.responseCommodity;
        responseLoad = DataSource.responseLoad;
    }

    @SuppressLint("NewApi")
    public String[] getCommoditiesValues() {
        String[] values = new String[responseCommodities.commodities.size()];
        responseCommodities.commodities.forEach(commodity -> {
            int index = responseCommodities.commodities.indexOf(commodity);
            values[index] = commodity.getName();
        });
        return values;
    }

    @SuppressLint("NewApi")
    public String[] getTrailerNames() {
        String[] trailerNames = new String[responseLoad.loads.size()];
        responseLoad.loads.forEach(load -> {
            int index = responseLoad.loads.indexOf(load);
            trailerNames[index] = load.trailerName;
        });

        return trailerNames;
    }

    public List<Load> getLoads() {
        return responseLoad.loads;
    }
}
