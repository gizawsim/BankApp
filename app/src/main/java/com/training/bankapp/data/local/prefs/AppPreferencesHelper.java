package com.training.bankapp.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferencesHelper implements PreferencesHelper{

    private static final String PREF_NAME_STATE = "PREF_NAME_STATE";
    SharedPreferences mPrefs;

    public AppPreferencesHelper(Context context, String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public void setNameState(Boolean isFather) {
        mPrefs.edit().putBoolean(PREF_NAME_STATE, isFather).apply();
    }

    @Override
    public Boolean getNameState() {
        return mPrefs.getBoolean(PREF_NAME_STATE, false);
    }

    @Override
    public void clear() {
        mPrefs.edit().clear().apply();
    }
}
