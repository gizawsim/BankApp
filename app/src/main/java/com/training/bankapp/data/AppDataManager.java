package com.training.bankapp.data;

import android.content.Context;

import com.training.bankapp.data.local.prefs.AppPreferencesHelper;
import com.training.bankapp.framework.util.AppConstants;

public class AppDataManager implements DataManager{

    private final AppPreferencesHelper mPrefHelper;

    public AppDataManager(Context context) {
        mPrefHelper = new AppPreferencesHelper(context, AppConstants.PREF_NAME);
    }

    @Override
    public void setLogin(Boolean isLogin) {
        mPrefHelper.setLogin(isLogin);
    }

    @Override
    public Boolean getLogin() {
        return mPrefHelper.getLogin();
    }

    @Override
    public void clear() {
        mPrefHelper.clear();
    }
}
