package com.mh.treasurehuntmh;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class HelperMethods {
    private static final String TAG = "HelperMethods";

    private static Context context;
    private static SharedPreferences sharedPreferences;

    public HelperMethods(Context context) {
        this.context = context;
    }

    public static String token() {

        sharedPreferences = context.getSharedPreferences("mhonam.token", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("token")) {
               return sharedPreferences.getString("token", "");
        }
        Log.d(TAG, "token: false");
        return "false";
    }
}

