package com.training.bankapp.data.local.prefs;

public interface PreferencesHelper {
    void setLogin(Boolean isLogin);
    Boolean getLogin();

    void setAccessToken(String token);
    String getAccessToken();

    void clear();
}
