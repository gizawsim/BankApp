package com.training.bankapp;

import android.app.Application;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.training.bankapp.data.AppDataManager;
import com.training.bankapp.data.DataManager;
import com.training.bankapp.framework.util.AppConstants;

public class App extends Application {

    private DataManager dataManager;

    private static App instance;

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dataManager = new AppDataManager(this);
        instance = this;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
