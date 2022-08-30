package com.training.bankapp.data.remote;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.POST;

public interface APIService {

    @POST(ApiEndPoint.LOGIN)
    Observable<Response<ResponseBody>> performLogin();

    @POST(ApiEndPoint.IMAGES)
    Observable<Response<ResponseBody>> fetchImages();

}
