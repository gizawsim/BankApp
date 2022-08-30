package com.training.bankapp.main;

import static com.training.bankapp.framework.AppCommonUtils.getBitmapDescriptor;
import static com.training.bankapp.framework.util.AppConstants.ZOOM_DEFAULT;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.training.bankapp.App;
import com.training.bankapp.R;
import com.training.bankapp.animation.AnimationActivity;
import com.training.bankapp.data.remote.model.CurrentLocation;
import com.training.bankapp.databinding.ActivityMainBinding;
import com.training.bankapp.framework.util.AppConstants;
import com.training.bankapp.image.ImageActivity;
import com.training.bankapp.login.LoginActivity;
import com.training.bankapp.message.MessageActivity;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private ActivityMainBinding mBinding;
    private GoogleMap mGoogleMap;
    private Boolean mIsTraffic = false;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 72;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

//        MainViewModel mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mBinding.navMain.setNavigationItemSelectedListener(this);

        setUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mBinding.drawerMain.closeDrawer(GravityCompat.START);
        if (item.getItemId() == R.id.action_image) {
            openImage();
        } else if (item.getItemId() == R.id.action_message) {
            Intent intent = new Intent(this, MessageActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_animation) {
            Intent intent = new Intent(this, AnimationActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_logout) {
            checkLogout();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        View viewHeader = mBinding.navMain.getHeaderView(0);
        CircleImageView imgProfilePic = viewHeader.findViewById(R.id.img_profile_header);
        Glide.with(getApplicationContext()).load(App.getInstance().getDataManager().getImageProfile())
                .placeholder(R.drawable.ic_baseline_person_24)
                .into(imgProfilePic);
    }

    private void setUp() {
        View viewHeader = mBinding.navMain.getHeaderView(0);
        CircleImageView imgProfilePic = viewHeader.findViewById(R.id.img_profile_header);
        ImageView imgCamera = viewHeader.findViewById(R.id.img_camera_header);

        Glide.with(getApplicationContext()).load(App.getInstance().getDataManager().getImageProfile())
                .placeholder(R.drawable.ic_baseline_person_24)
                .into(imgProfilePic);

        imgProfilePic.setOnClickListener(view -> {
            mBinding.drawerMain.closeDrawer(GravityCompat.START);
            openImage();
        });

        imgCamera.setOnClickListener(view -> {
            mBinding.drawerMain.closeDrawer(GravityCompat.START);
            openImage();
        });

        initMap();
    }

    private void openImage() {
        Intent intent = new Intent(this, ImageActivity.class);
        startActivity(intent);
    }

    private void checkLogout() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.logout))
                .setMessage(R.string.msg_logout)
                .setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> {
                    openLogin();
                })
                .setNegativeButton(R.string.no, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .setCancelable(true).show();
    }

    private void openLogin() {
        App.getInstance().getDataManager().clear();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finishAffinity();
    }


    private void initMap() {
        Places.initialize(getApplicationContext(), AppConstants.API_KEY);
        mBinding.layoutMain.mapMain.onCreate(null);
        mBinding.layoutMain.mapMain.onResume();
        mBinding.layoutMain.mapMain.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        MapsInitializer.initialize(this);
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setPadding(0, 0, 0, 50);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(8.9633398,
                38.7081044), ZOOM_DEFAULT));
        googleMap.setTrafficEnabled(false);
        googleMap.setBuildingsEnabled(false);
        googleMap.setIndoorEnabled(false);
        mBinding.layoutMain.fabTraffic.setOnClickListener(view -> {
            Boolean isTraffic = !mIsTraffic;
            mIsTraffic = isTraffic;
            setIsTraffic(isTraffic);
        });
        mBinding.layoutMain.fabSearch.setOnClickListener(view -> {
            onSearchLocation();
        });
        CurrentLocation currentLocation = new CurrentLocation(AppConstants.DEFAULT_LAT, AppConstants.DEFAULT_LONG);
        setLocationUpdate(currentLocation);
    }

    private void setIsTraffic(Boolean isTraffic) {
        if (isTraffic) {
            mBinding.layoutMain.fabTraffic.setColorFilter(Color.RED);
            mGoogleMap.setTrafficEnabled(true);
        } else {
            mBinding.layoutMain.fabTraffic.setColorFilter(Color.BLACK);
            mGoogleMap.setTrafficEnabled(false);
        }
    }

    private void setLocationUpdate(CurrentLocation currentLocation) {
        LatLng latLng = new LatLng(currentLocation.getLat(), currentLocation.getLon());
        mGoogleMap.clear();
        mGoogleMap.addMarker(new MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(getBitmapDescriptor(this))));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_DEFAULT));
    }

    private void onSearchLocation() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,
                Place.Field.LAT_LNG, Place.Field.ADDRESS);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .setCountry("ET")
                .setTypeFilter(TypeFilter.ESTABLISHMENT)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                Place selectedPlace = Autocomplete.getPlaceFromIntent(data);
                if (selectedPlace.getLatLng() != null) {
                    CurrentLocation currentLocation = new CurrentLocation(selectedPlace.getLatLng().latitude, selectedPlace.getLatLng().longitude);
                    setLocationUpdate(currentLocation);
                    Log.d("Autocomplete", "Places: " + selectedPlace.getName() + ", " +
                            selectedPlace.getId());
                } else {
                    super.onActivityResult(requestCode, resultCode, data);
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                if (data != null) {
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Log.e("Autocomplete", " Status error: " + status.getStatusMessage());
                }
            } else if (resultCode == RESULT_CANCELED)
                Log.e("Autocomplete", "Cancel Code: " + resultCode);

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
