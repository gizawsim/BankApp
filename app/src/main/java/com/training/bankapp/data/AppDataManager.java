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
    public void setNameState(Boolean isFather) {
        mPrefHelper.setNameState(isFather);
    }

    @Override
    public Boolean getNameState() {
        return mPrefHelper.getNameState();
    }

    @Override
    public void clear() {
        mPrefHelper.clear();
    }
}
