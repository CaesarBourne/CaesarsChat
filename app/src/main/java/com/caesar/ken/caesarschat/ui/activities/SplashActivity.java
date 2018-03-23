package com.caesar.ken.caesarschat.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by e on 2/20/2018.
 */

public class SplashActivity extends AppCompatActivity {

    private Runnable mRunnable;
    private Handler mhandler;
    private static final int SPLASH_TIME_MS = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mhandler = new Handler();

        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() != null){
                    UserListingActivity.startActivity(SplashActivity.this);
                }
                else {
                    LoginActivity.startIntent(SplashActivity.this);
                }
                finish();
            }
        };
        mhandler.postDelayed(mRunnable, SPLASH_TIME_MS);
    }
}
