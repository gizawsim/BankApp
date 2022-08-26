package com.training.bankapp.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.training.bankapp.databinding.ActivityLoginBinding;
import com.training.bankapp.login.ui.LoginViewModel;
import com.training.bankapp.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        mBinding.btnLogin.setOnClickListener(view -> openMain());

    }

    private void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
