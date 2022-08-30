package com.training.bankapp.main.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.training.bankapp.App;

public class MainViewModel extends ViewModel {

    public MutableLiveData<String> urlImage = new MutableLiveData<>();


    public MainViewModel() {
        setImage();
    }

    public void setImage() {
        String image = App.getInstance().getDataManager().getImageProfile();
        urlImage.setValue(image);
    }
}
