package com.training.bankapp.main.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.training.bankapp.App;

public class MainViewModel extends ViewModel {

    public MutableLiveData<String> txtName = new MutableLiveData<>();

    public MainViewModel() {
        setName();
    }

    public void setName() {
        String name = "Osman";

        if (App.getInstance().getDataManager().getLogin())
            name = "Welela";
        txtName.setValue(name);
    }

    public void onClickName() {
        Boolean isFather = App.getInstance().getDataManager().getLogin();
        App.getInstance().getDataManager().setLogin(!isFather);
        setName();
    }
}
