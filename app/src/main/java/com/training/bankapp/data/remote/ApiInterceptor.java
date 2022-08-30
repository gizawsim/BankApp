package com.training.bankapp.data.remote;

import java.io.IOException;


import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiInterceptor implements Interceptor {

    private ApiHeader mApiHeader;

    public ApiInterceptor(final ApiHeader header) {
        mApiHeader = header;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request();
        final Request.Builder builder = request.newBuilder();
        String token = mApiHeader.getProtectedApiHeader().getMAccessToken();

        if (token != null && !token.isEmpty())
            builder.addHeader(ApiHeader.AUTH, ApiHeader.AUTH_TOKEN + token);

        return chain.proceed(builder.build());
    }
}
