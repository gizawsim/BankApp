package com.training.bankapp.data.local.prefs;

public interface PreferencesHelper {
    void setNameState(Boolean isFather);
    Boolean getNameState();
    void clear();
}
