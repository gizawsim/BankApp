package com.training.bankapp.animation;

import android.content.Context;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.training.bankapp.R;
import com.training.bankapp.animation.ui.lottie.AnimLottieFragment;
import com.training.bankapp.animation.ui.rotate.AnimRotateFragment;

public class AnimationPagerAdapter extends FragmentStatePagerAdapter {

    private int[] TAB_TITLES = new int[]{R.string.tab_title_1, R.string.tab_title_2};
    private Context mContext = null;

    public AnimationPagerAdapter(Context context, @NonNull androidx.fragment.app.FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
            fragment = new AnimLottieFragment();
        else if (position == 1)
            fragment = new AnimRotateFragment();
        return fragment;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }

    @Override
    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
}
