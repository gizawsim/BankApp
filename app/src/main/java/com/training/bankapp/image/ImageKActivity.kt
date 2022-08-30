package com.training.bankapp.image

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.bankapp.R
import com.training.bankapp.databinding.ActivityImageBinding
import com.training.bankapp.image.ui.ImageAdapter
import com.training.bankapp.image.ui.ImageViewModel

class ImageKActivity : AppCompatActivity() {

    private var mBinding: ActivityImageBinding? = null
    private var mImageViewModel: ImageViewModel? = null
    private var mAdapter: ImageAdapter? = ImageAdapter(this);

//    private val mImagePickerLauncher = registerImagePicker {
//        val image = it.firstOrNull() ?: return@registerImagePicker
//        if (image != null) {
//            mImageViewModel?.onImageSet(image.path)
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)
        mImageViewModel = ViewModelProvider(this)[ImageViewModel::class.java]
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUp() {
        mBinding?.imgProfileImage?.setOnClickListener { onImage() }
        mBinding?.imgCameraImage?.setOnClickListener { onImage() }

        mAdapter = ImageAdapter(this)

        val gridLayoutManager = GridLayoutManager(
            this,
            4,
            RecyclerView.HORIZONTAL,
            false
        )
        mBinding?.rvImage?.layoutManager = gridLayoutManager
        mBinding?.rvImage?.adapter = mAdapter

        mImageViewModel?.urlImage?.observe(this) {
            Glide.with(applicationContext).load(it)
                .placeholder(R.drawable.ic_baseline_person_24)
                .into(mBinding!!.imgProfileImage)
        }
        mImageViewModel?.fetchImages()
    }

    private fun onImage() {
//        mImagePickerLauncher.launch(
//            ImagePickerConfig {
//                mode = ImagePickerMode.SINGLE
//                limit = 1
//                returnMode =
//                    ReturnMode.ALL // set whether pick action or camera action should return immediate result or not. Only works in single mode for image picker
//                isFolderMode = true // set folder mode (false by default)
//                arrowColor = -1
//                folderTitle = getString(R.string.pick_image)
//                isShowCamera = false
//                isIncludeVideo = false
//                isIncludeAnimation = false
//            }
//        )
    }
}