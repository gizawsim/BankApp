package com.training.bankapp.data;

import com.training.bankapp.data.local.prefs.PreferencesHelper;
import com.training.bankapp.data.remote.ApiHelper;
import com.training.bankapp.framework.util.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public interface DataManager extends PreferencesHelper, ApiHelper {
    SchedulerProvider getSchedulerProvider();
    CompositeDisposable getCompositeDisposable();
}
