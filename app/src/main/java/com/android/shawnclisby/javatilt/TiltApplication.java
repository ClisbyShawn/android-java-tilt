package com.android.shawnclisby.javatilt;

import android.app.Application;

public class TiltApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DataSource.getInstance();
        DataSource.initializeData(this);
    }
}
