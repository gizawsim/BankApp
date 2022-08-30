package com.training.bankapp.image;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.training.bankapp.R;
import com.training.bankapp.databinding.ActivityImageBinding;
import com.training.bankapp.image.ui.ImageAdapter;
import com.training.bankapp.image.ui.ImageViewModel;

import java.util.Objects;

public class ImageActivity extends AppCompatActivity {

    private ActivityImageBinding mBinding;
    private ImageViewModel mViewModel;

    //    private String launcher =
//            registerForActivityResult(new ActivityResultContracts.StartActivityForResult()) {
//        if (it.resultCode == RESULT_OK && it.data != null) {
//            val selectedPlace =
//                    it.data?.let { dataResult -> Autocomplete.getPlaceFromIntent(dataResult) }
//            if (selectedPlace?.latLng != null) {
//                val currentLocation = selectedPlace.latLng?.latitude?.let { lat ->
//                        selectedPlace.latLng?.longitude?.let { long ->
//                    CurrentLocation(lat, long)
//                }
//                }
//                currentLocation?.let { location -> setLocationUpdate(location) }
//                Log.d(
//                        "Autocomplete", "Places: " + selectedPlace.name + ", " +
//                                selectedPlace.id
//                )
//            }
//        } else if (it.resultCode == AutocompleteActivity.RESULT_ERROR) {
//            val status =
//                    it.data?.let { dataResult -> Autocomplete.getStatusFromIntent(dataResult) }
//            Log.e("Autocomplete", " Status error: " + status?.statusMessage);
//        } else if (it.resultCode == RESULT_CANCELED) Log.e(
//                "Autocomplete",
//                "Cancel Code: ${it.resultCode}"
//        );
//    }
//
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
//                    val image = ImagePicker.get(data)
//                    if (image != null) {
//                        mViewModel?.setSelectedImage(image.path)
//                    }

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        // Handle the Intent
                    }
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityImageBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUp() {
        mBinding.imgProfileImage.setOnClickListener(view -> onImage());
        mBinding.imgCameraImage.setOnClickListener(view -> onImage());

        ImageAdapter mAdapter = new ImageAdapter(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                this,
                2,
                RecyclerView.HORIZONTAL,
                false
        );
        mBinding.rvImage.setLayoutManager(gridLayoutManager);
        mBinding.rvImage.setAdapter(mAdapter);

        mViewModel.urlImage.observe(this, s -> {
            Glide.with(getApplicationContext()).load(s)
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .into(mBinding.imgProfileImage);
        });

        mViewModel.images.observe(this, mAdapter::setImages);
        mViewModel.fetchImages();
    }

    private void onImage() {
        ImagePicker.create(this)
                .single()
                .limit(1)
                .folderMode(true)
                .toolbarArrowColor(getResources().getColor(R.color.black))
                .returnMode(ReturnMode.CAMERA_ONLY)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            if (image != null) {
                mViewModel.onImageSet(image.getPath());
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
