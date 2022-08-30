package com.training.bankapp.animation.ui.lottie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.training.bankapp.databinding.FragmentAnimLottieBinding;

public class AnimLottieFragment extends Fragment {

    FragmentAnimLottieBinding mBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AnimLottieViewModel lottieViewModel =
                new ViewModelProvider(this).get(AnimLottieViewModel.class);

        mBinding = FragmentAnimLottieBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
