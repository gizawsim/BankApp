package com.training.bankapp.data.remote;


import io.reactivex.Observable;
import okhttp3.Response;
import retrofit2.Retrofit;

public interface ApiHelper {
    Observable<Response> performLogin();
}
