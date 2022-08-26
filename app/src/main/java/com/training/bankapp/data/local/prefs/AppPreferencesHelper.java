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
    public void setLogin(Boolean isLogin) {
        mPrefs.edit().putBoolean(PREF_NAME_STATE, isLogin).apply();
    }

    @Override
    public Boolean getLogin() {
        return mPrefs.getBoolean(PREF_NAME_STATE, false);
    }

    @Override
    public void clear() {
        mPrefs.edit().clear().apply();
    }
}
