package com.training.bankapp.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferencesHelper implements PreferencesHelper{

    private static final String PREF_IS_LOGIN = "PREF_IS_LOGIN";
    private static final String PREF_ACCESS_TOKEN = "PREF_ACCESS_TOKEN";
    SharedPreferences mPrefs;

    public AppPreferencesHelper(Context context, String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public void setLogin(Boolean isLogin) {
        mPrefs.edit().putBoolean(PREF_IS_LOGIN, isLogin).apply();
    }

    @Override
    public Boolean getLogin() {
        return mPrefs.getBoolean(PREF_IS_LOGIN, false);
    }

    @Override
    public void setAccessToken(String token) {
        mPrefs.edit().putString(PREF_ACCESS_TOKEN, token).apply();
    }

    @Override
    public String getAccessToken() {
        return mPrefs.getString(PREF_ACCESS_TOKEN, null);
    }

    @Override
    public void clear() {
        mPrefs.edit().clear().apply();
    }
}
