package com.training.bankapp.data.remote;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.training.bankapp.data.local.prefs.PreferencesHelper;

public class ApiHeader {

    public static final String AUTH = "Authorization";
    public static final String AUTH_BEARER = "Bearer ";
    public static final String AUTH_TOKEN = "Token ";

    public ProtectedApiHeader mProtectedApiHeader;
    public PublicApiHeader mPublicApiHeader;

    public ApiHeader(PublicApiHeader publicApiHeader, ProtectedApiHeader protectedApiHeader) {
        mPublicApiHeader = publicApiHeader;
        mProtectedApiHeader = protectedApiHeader;
    }

    public ProtectedApiHeader getProtectedApiHeader() {
        return mProtectedApiHeader;
    }

    public PublicApiHeader getPublicApiHeader() {
        return mPublicApiHeader;
    }

    public static final class PublicApiHeader {

        public PublicApiHeader() {
        }

    }

    public static final class ProtectedApiHeader {

        @Expose
        @SerializedName("access-token")
        private String mAccessToken;

        PreferencesHelper preferencesHelper;

        public ProtectedApiHeader(String mAccessToken,
                                  PreferencesHelper preferencesHelper) {
            this.preferencesHelper = preferencesHelper;
            this.mAccessToken = mAccessToken;
        }

        public String getMAccessToken() {
            return preferencesHelper.getAccessToken();
        }

        public void setMAccessToken(String mAccessToken) {
            this.mAccessToken = mAccessToken;
        }
    }
}
