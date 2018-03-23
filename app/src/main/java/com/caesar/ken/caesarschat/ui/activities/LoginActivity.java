package com.caesar.ken.caesarschat.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.caesar.ken.caesarschat.R;
import com.caesar.ken.caesarschat.ui.fragments.LoginFragment;


/**
 * Created by Caesar on 2/21/2018.
 */

public class LoginActivity extends AppCompatActivity {

    Toolbar mtoolbar;
    public static void startIntent(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
    public static void startIntent(Context context, int flags){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        init();
    }
    public void bindViews(){
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    public void init(){
        setSupportActionBar(mtoolbar);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_content_login,
                LoginFragment.newInstance(), LoginFragment.class.getSimpleName()  );
        fragmentTransaction.commit();
    }
}
