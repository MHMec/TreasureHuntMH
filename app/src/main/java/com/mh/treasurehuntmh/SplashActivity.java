package com.mh.treasurehuntmh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private static String token;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = isAuth();
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private Intent isAuth() {
        sharedPreferences = getSharedPreferences("mhonam.token", Context.MODE_PRIVATE);

        if (sharedPreferences.contains("token") ) {
            Intent intent = new Intent(SplashActivity.this, ClueActivity.class);
            intent.putExtra("token", sharedPreferences.getString("token", null));
            return intent;
        }
        else {
            Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
            return intent;
        }
    }
}
