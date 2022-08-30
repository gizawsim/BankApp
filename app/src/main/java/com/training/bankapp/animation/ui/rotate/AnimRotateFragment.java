package com.training.bankapp.animation.ui.rotate;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.training.bankapp.databinding.FragmentAnimRotateBinding;

import java.util.Random;

public class AnimRotateFragment extends Fragment {

    FragmentAnimRotateBinding mBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AnimRotateViewModel rotateViewModel =
                new ViewModelProvider(this).get(AnimRotateViewModel.class);

        mBinding = FragmentAnimRotateBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void setUp() {
        rotateLogoFirst();
        mBinding.imgRotate.setOnClickListener(view1 -> rotateLogo());
    }

    private void rotateLogoFirst() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                mBinding.imgRotate, "rotation", 360f
        );
        animator.setDuration(2000);
        animator.start();
    }

    private void rotateLogo() {
        Random rand = new Random();
        int factor = rand.nextInt(5);
        int direction = rand.nextInt(2);
        float size = (float) (rand.nextInt(360) + 360);
        float degree = size * factor;
        if (direction == 0) {
            degree *= 1;
        } else
            degree *= -1;
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                mBinding.imgRotate, "rotation", degree
        );
        animator.setDuration(2000);
        animator.start();
    }
}
