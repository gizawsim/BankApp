package com.training.bankapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.training.bankapp.App;
import com.training.bankapp.R;
import com.training.bankapp.databinding.ActivityLoginBinding;
import com.training.bankapp.framework.util.AppConstants;
import com.training.bankapp.login.ui.LoginViewModel;
import com.training.bankapp.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setUp();
    }

    private void setUp() {
        mBinding.btnLogin.setOnClickListener(view -> checkInput());
        if (App.getInstance().getDataManager().getLogin()) {
            openMain();
        }
    }

    private void checkInput() {
        if (mBinding.etUserNameLogin.getText() == null ||
                mBinding.etUserNameLogin.getText().toString().trim().isEmpty()) {
            mBinding.etUserNameLogin.setError(getString(R.string.err_username));
        } else if (mBinding.etPasswordLogin.getText() == null ||
                mBinding.etPasswordLogin.getText().toString().trim().isEmpty()) {
            mBinding.etPasswordLogin.setError(getString(R.string.err_password));
        } else if (mBinding.etUserNameLogin.getText().toString().equals(AppConstants.USERNAME) &&
                mBinding.etPasswordLogin.getText().toString().equals(AppConstants.PASSWORD)) {
            App.getInstance().getDataManager().setLogin(mBinding.checkBoxLogin.isChecked());
            openMain();
        } else
            Toast.makeText(this,
                    getString(R.string.err_login),
                    Toast.LENGTH_SHORT).show();
    }

    private void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
