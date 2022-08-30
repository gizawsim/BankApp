package com.training.bankapp.message;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Patterns;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.training.bankapp.R;
import com.training.bankapp.databinding.ActivityMessageBinding;
import com.training.bankapp.framework.AppCommonUtils;
import com.training.bankapp.message.ui.MessageViewModel;

import java.util.Objects;

public class MessageActivity extends AppCompatActivity {

    private ActivityMessageBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        MessageViewModel messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        setUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] { Manifest.permission.SEND_SMS}, 1);
        }
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
        mBinding.radioBtnSmsMessage.setOnClickListener(view -> onRadio(true));
        mBinding.radioBtnEmailMessage.setOnClickListener(view -> onRadio(false));
        mBinding.btnMessage.setOnClickListener(view -> {
            if (!mBinding.radioBtnSmsMessage.isChecked() && !mBinding.radioBtnEmailMessage.isChecked()) {
                Toast.makeText(this, R.string.err_message_type, Toast.LENGTH_SHORT).show();
            } else {
                checkInput(mBinding.radioBtnSmsMessage.isChecked());
            }
        });
    }

    private void onRadio(boolean isSMS) {
        if (isSMS) {
            mBinding.radioBtnSmsMessage.setChecked(true);
            mBinding.radioBtnEmailMessage.setChecked(false);
            mBinding.etToMessage.setHint(R.string.enter_phone);
        } else {
            mBinding.radioBtnEmailMessage.setChecked(true);
            mBinding.radioBtnSmsMessage.setChecked(false);
            mBinding.etToMessage.setHint(R.string.enter_email);
        }
    }

    private void checkInput(boolean isSMS) {
        if (mBinding.etToMessage.getText() == null ||
                mBinding.etToMessage.getText().toString().trim().isEmpty()) {
            mBinding.etToMessage.setError(getString(R.string.err_message_to));
        } else if (mBinding.etMessage.getText() == null ||
                mBinding.etMessage.getText().toString().trim().isEmpty()) {
            mBinding.etMessage.setError(getString(R.string.err_message_txt));
        } else if (isSMS && AppCommonUtils.isValidPhone(mBinding.etToMessage.getText().toString().trim())) {
            sendSms(mBinding.etToMessage.getText().toString().trim(), mBinding.etMessage.getText().toString().trim());
        } else if (isSMS) {
            mBinding.etToMessage.setError(getString(R.string.err_phone));
        } else if (Patterns.EMAIL_ADDRESS.matcher(mBinding.etToMessage.getText().toString().trim()).matches()) {
            sendEmail(mBinding.etToMessage.getText().toString().trim(), mBinding.etMessage.getText().toString().trim());
        } else {
            mBinding.etToMessage.setError(getString(R.string.err_email));
        }
    }

    private void sendSms(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(this, R.string.msg_success, Toast.LENGTH_SHORT).show();
        } catch (Exception exception) {
            Toast.makeText(this, getString(R.string.msg_failure) + exception.getMessage(),
                    Toast.LENGTH_SHORT).show();
            exception.printStackTrace();
        }
    }

    private void sendEmail(String email, String msg) {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri data = Uri.parse(
//                "mailto:" + email + "?subject=" + Uri.encode("$DEFAULT_SUBJECT_EMAIL : ${mViewModel?.dataManager?.currentUserName}")
//        );
//        intent.setData(data);
//        startActivity(intent);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        String[] TO = {email};
        Uri data = Uri.parse("mailto:");
        emailIntent.setData(data);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Bank App Testing");
        emailIntent.putExtra(Intent.EXTRA_TEXT, msg);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (Exception exception) {
            Toast.makeText(this, getString(R.string.msg_failure) + exception.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
