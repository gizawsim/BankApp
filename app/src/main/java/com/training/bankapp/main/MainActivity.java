package com.training.bankapp.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.training.bankapp.App;
import com.training.bankapp.databinding.ActivityMainBinding;
import com.training.bankapp.login.LoginActivity;
import com.training.bankapp.main.ui.MainViewModel;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

//        mainViewModel.txtName.observe(this, mBinding.btnMain::setText);

        mBinding.btnMain.setOnClickListener(view -> openLogin());
    }

    private void openLogin() {
        App.getInstance().getDataManager().clear();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finishAffinity();
    }

}
