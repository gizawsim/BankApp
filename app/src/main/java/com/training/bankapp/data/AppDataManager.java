package com.training.bankapp.data;

import android.content.Context;

import com.training.bankapp.data.local.prefs.AppPreferencesHelper;
import com.training.bankapp.data.remote.ApiHelper;
import com.training.bankapp.data.remote.AppApiHelper;
import com.training.bankapp.framework.util.AppConstants;
import com.training.bankapp.framework.util.rx.AppSchedulerProvider;
import com.training.bankapp.framework.util.rx.SchedulerProvider;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Response;

public class AppDataManager implements DataManager {

    private final AppPreferencesHelper mPrefHelper;
    private final ApiHelper mApiHelper;
    private final SchedulerProvider mSchedulerProvider;
    private final CompositeDisposable mCompositeDisposable;

    public AppDataManager(Context context) {
        mPrefHelper = new AppPreferencesHelper(context, AppConstants.PREF_NAME);
        mApiHelper = new AppApiHelper(context, mPrefHelper);
        mCompositeDisposable = new CompositeDisposable();
        mSchedulerProvider = new AppSchedulerProvider();
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
    public void setAccessToken(String token) {
        mPrefHelper.setAccessToken(token);
    }

    @Override
    public String getAccessToken() {
        return mPrefHelper.getAccessToken();
    }

    @Override
    public void clear() {
        mPrefHelper.clear();
    }

    @Override
    public Observable<Response> performLogin() {
        return mApiHelper.performLogin();
    }

    @Override
    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    @Override
    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }
}
