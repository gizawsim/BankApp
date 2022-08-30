package com.training.bankapp.animation;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.training.bankapp.animation.ui.AnimationViewModel;
import com.training.bankapp.databinding.ActivityAnimationBinding;

import java.util.Objects;

public class AnimationActivity extends AppCompatActivity {

    private ActivityAnimationBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityAnimationBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        AnimationViewModel animationViewModel = new ViewModelProvider(this).get(AnimationViewModel.class);
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
        AnimationPagerAdapter mAdapter = new AnimationPagerAdapter(this, getSupportFragmentManager());
        mBinding.viewPagerAnim.setAdapter(mAdapter);
        mBinding.tabLayoutAnim.setupWithViewPager(mBinding.viewPagerAnim);
    }
}
