package com.training.bankapp.image.ui;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.training.bankapp.App;
import com.training.bankapp.data.remote.model.ImageItem;

import java.util.ArrayList;
import java.util.List;

public class ImageViewModel extends ViewModel {

    public MutableLiveData<String> urlImage = new MutableLiveData<>();
    public MutableLiveData<List<ImageItem>> images = new MutableLiveData<>();


    public ImageViewModel() {
        setImage();
    }

    public void setImage() {
        String image = App.getInstance().getDataManager().getImageProfile();
        urlImage.setValue(image);
    }

    public void onImageSet(String imageUrl) {
        App.getInstance().getDataManager().setImageProfile(imageUrl);
        setImage();
    }

    public void fetchImages() {

        App.getInstance().getDataManager().getCompositeDisposable().add(App.getInstance().getDataManager()
                .getImages()
                .subscribeOn(App.getInstance().getDataManager().getSchedulerProvider().io())
                .observeOn(App.getInstance().getDataManager().getSchedulerProvider().ui())
                .subscribe(response -> {

                    if (response.errorBody() != null) {
                        ArrayList<ImageItem> imageValues = new ArrayList<>();
                        ImageItem imageItem = new ImageItem("https://www.politico.eu/cdn-cgi/image/width=1160,height=764,quality=80,onerror=redirect,format=auto/wp-content/uploads/2022/05/30/GettyImages-1237475657-scaled.jpg",
                                "Bank One", 3);
                        imageValues.add(imageItem);
                        ImageItem imageItemSecond = new ImageItem("https://www.debt.org/wp-content/uploads/2012/04/Bank.jpg",
                                "Bank Two", 65);
                        imageValues.add(imageItemSecond);
                        ImageItem imageItemThird = new ImageItem("https://www.expatica.com/app/uploads/sites/17/2020/11/austria-bank.jpg",
                                "Bank Three", 32);
                        imageValues.add(imageItemThird);
                        ImageItem imageItemFour = new ImageItem("https://cdn.corporatefinanceinstitute.com/assets/AdobeStock_1080430-1024x686.jpeg",
                                "Bank Four", 83);
                        imageValues.add(imageItemFour);
                        ImageItem imageItemFive = new ImageItem("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a6/World_Bank_building_at_Washington.jpg/1200px-World_Bank_building_at_Washington.jpg",
                                "Bank Five", 21);
                        imageValues.add(imageItemFive);

                        images.setValue(imageValues);
                    } else if (response.body() != null) {
                        Log.e("Images", response.body().string() + "....image.");
//                        getNavigator().setEarningsList(getListEarningFromResponse(response.body().string()),
//                                0);
                    }
//                    else {
//                        JSONObject errBody = null;
//                        if (response.errorBody() != null)
//                            errBody = new JSONObject(response.errorBody().string());
//                        getNavigator().showError(errBody, response.message());
//                    }

                }, throwable -> {
                    Log.e("Image Errors: ", throwable.getStackTrace().toString() + "....errors.");

//                        getNavigator().showError(throwable);
                }));
    }

    @Override
    protected void onCleared() {
        App.getInstance().getDataManager().getCompositeDisposable().dispose();
        super.onCleared();
    }
}
