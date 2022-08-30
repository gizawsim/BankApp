package com.training.bankapp.data.remote;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.training.bankapp.data.local.prefs.PreferencesHelper;
import com.training.bankapp.framework.util.AppConstants;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppApiHelper implements ApiHelper {

    private final Context mContext;
    private final ApiInterceptor mInterceptor;
    private static Retrofit retrofit = null;
    private static final int NETWORK_CALL_TIMEOUT = 90;

    public AppApiHelper(Context context, PreferencesHelper preferencesHelper) {
        mContext = context;
        ApiHeader.PublicApiHeader publicApiHeader = new ApiHeader.PublicApiHeader();
        ApiHeader.ProtectedApiHeader protectedApiHeader = new ApiHeader.ProtectedApiHeader(
                preferencesHelper.getAccessToken(),
                preferencesHelper);
        ApiHeader apiHeader = new ApiHeader(publicApiHeader, protectedApiHeader);
        mInterceptor = new ApiInterceptor(apiHeader);
    }

    private OkHttpClient configureTimeouts() {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(mContext.getCacheDir(), cacheSize);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(mInterceptor)
                .addInterceptor(logging)
                .connectTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
                .cache(cache)
                .build();
        return okHttpClient;
    }

    private Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .client(configureTimeouts())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private APIService getApiService() {
        return getRetrofitInstance().create(APIService.class);
    }

    @Override
    public Observable<Response<ResponseBody>> performLogin() {
        return getApiService().performLogin();
    }

    @Override
    public Observable<Response<ResponseBody>> getImages() {
        return getApiService().fetchImages();
    }
}
