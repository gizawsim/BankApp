package com.training.bankapp.data.remote;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public interface ApiHelper {
    Observable<Response<ResponseBody>> performLogin();
    Observable<Response<ResponseBody>> getImages();
}
