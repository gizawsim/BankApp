package com.training.bankapp.framework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.training.bankapp.R;

import java.util.regex.Pattern;

public class AppCommonUtils {

    public static Boolean isValidPhone(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() == 10 && (phone.charAt(1) != '9' || phone.charAt(1) != '7') && (
                    phone.matches("09[0-9]{8}") || phone.matches("07[0-9]{8}"));
        } else
            return false;
    }

    public static Bitmap getBitmapDescriptor(Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker);
        double newWidht = bitmap.getWidth() * 0.5;
        double newHeight = bitmap.getHeight() * 0.5;
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) newWidht, (int) newHeight, false);
        return bitmap;
    }
}
