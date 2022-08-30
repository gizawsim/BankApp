package com.training.bankapp.main

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.training.bankapp.App
import com.training.bankapp.R
import com.training.bankapp.animation.AnimationActivity
import com.training.bankapp.data.remote.model.CurrentLocation
import com.training.bankapp.databinding.ActivityMainBinding
import com.training.bankapp.framework.AppCommonUtils
import com.training.bankapp.framework.util.AppConstants
import com.training.bankapp.image.ImageActivity
import com.training.bankapp.login.LoginActivity
import com.training.bankapp.main.ui.MainViewModel
import com.training.bankapp.message.MessageActivity
import com.training.test2.databinding.ActivityMainBinding
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class MainKActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    OnMapReadyCallback {

    private var mBinding: ActivityMainBinding? = null
    private var mGoogleMap: GoogleMap? = null
    private var mIsTraffic = false

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                val selectedPlace =
                    it.data?.let { dataResult -> Autocomplete.getPlaceFromIntent(dataResult) }
                if (selectedPlace?.latLng != null) {
                    val currentLocation = selectedPlace.latLng?.latitude?.let { lat ->
                        selectedPlace.latLng?.longitude?.let { long ->
                            CurrentLocation(lat, long)
                        }
                    }
                    currentLocation?.let { location -> setLocationUpdate(location) }
                    Log.d(
                        "Autocomplete", "Places: " + selectedPlace.name + ", " +
                                selectedPlace.id
                    )
                }
            } else if (it.resultCode == AutocompleteActivity.RESULT_ERROR) {
                val status =
                    it.data?.let { dataResult -> Autocomplete.getStatusFromIntent(dataResult) }
                Log.e("Autocomplete", " Status error: " + status?.statusMessage)
            } else if (it.resultCode == RESULT_CANCELED) Log.e(
                "Autocomplete",
                "Cancel Code: ${it.resultCode}"
            )
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)
        mMainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mBinding?.navMain?.setNavigationItemSelectedListener(this)
        setUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mBinding?.drawerMain?.closeDrawer(GravityCompat.START)
        if (item.itemId == R.id.action_image) {
            openImage()
        } else if (item.itemId == R.id.action_message) {
            val intent = Intent(this, MessageActivity::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.action_animation) {
            val intent = Intent(this, AnimationActivity::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.action_logout) {
            checkLogout()
        }
        return true
    }

    private fun setUp() {
        val viewHeader = mBinding?.navMain?.getHeaderView(0)
        val imgProfilePic = viewHeader?.findViewById<CircleImageView>(R.id.img_profile_header)
        val imgCamera = viewHeader?.findViewById<ImageView>(R.id.img_camera_header)
        mMainViewModel?.urlImage?.observe(this) { imageUrl ->
            imgProfilePic?.let { imageView ->
                Glide.with(applicationContext).load(imageUrl)
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .into(imageView)
            }
        }
        imgProfilePic?.setOnClickListener {
            mBinding?.drawerMain?.closeDrawer(GravityCompat.START)
            openImage()
        }
        imgCamera?.setOnClickListener { view: View? ->
            mBinding?.drawerMain?.closeDrawer(GravityCompat.START)
            openImage()
        }
        initMap()
    }

    private fun openImage() {
        val intent = Intent(this, ImageActivity::class.java)
        startActivity(intent)
    }

    private fun checkLogout() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.logout))
            .setMessage(R.string.msg_logout)
            .setPositiveButton(getString(R.string.yes)) { _: DialogInterface?, _: Int -> openLogin() }
            .setNegativeButton(R.string.no) { dialogInterface: DialogInterface, _: Int -> dialogInterface.dismiss() }
            .setCancelable(true).show()
    }

    private fun openLogin() {
        App.getInstance().dataManager.clear()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    private fun initMap() {
        mBinding?.layoutMain?.mapMain?.onCreate(null)
        mBinding?.layoutMain?.mapMain?.onResume()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        MapsInitializer.initialize(this)
        mGoogleMap = googleMap
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap.setPadding(0, 0, 0, 50)
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    8.9633398,
                    38.7081044
                ), AppConstants.ZOOM_DEFAULT
            )
        )
        googleMap.isTrafficEnabled = false
        googleMap.isBuildingsEnabled = false
        googleMap.isIndoorEnabled = false
        mBinding?.layoutMain?.fabTraffic?.setOnClickListener {
            val isTraffic = !mIsTraffic
            mIsTraffic = isTraffic
            setIsTraffic(isTraffic)
        }
        mBinding?.layoutMain?.fabSearch?.setOnClickListener { onSearchLocation() }
        val currentLocation = CurrentLocation(AppConstants.DEFAULT_LAT, AppConstants.DEFAULT_LONG)
        setLocationUpdate(currentLocation)
    }

    private fun setIsTraffic(isTraffic: Boolean) {
        if (isTraffic) {
            mBinding?.layoutMain?.fabTraffic?.setColorFilter(Color.RED)
            mGoogleMap?.isTrafficEnabled = true
        } else {
            mBinding?.layoutMain?.fabTraffic?.setColorFilter(Color.BLACK)
            mGoogleMap?.isTrafficEnabled = false
        }
    }

    private fun setLocationUpdate(currentLocation: CurrentLocation) {
        val latLng = LatLng(currentLocation.lat, currentLocation.lon)
        mGoogleMap?.clear()
        mGoogleMap?.addMarker(
            MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(AppCommonUtils.getBitmapDescriptor(this)))
        )
        mGoogleMap?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLng,
                AppConstants.ZOOM_DEFAULT
            )
        )
    }

    private fun onSearchLocation() {
        val fields = Arrays.asList(
            Place.Field.ID, Place.Field.NAME,
            Place.Field.LAT_LNG, Place.Field.ADDRESS
        )
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .setCountry("ET")
            .setTypeFilter(TypeFilter.ESTABLISHMENT)
            .build(this)
        launcher.launch(intent)
    }

    companion object {
        @JvmStatic
        fun getStartIntent(context: Context?): Intent {
            return Intent(context, MainKActivity::class.java)
        }
    }
}