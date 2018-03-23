package com.caesar.ken.caesarschat.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import  com.caesar.ken.caesarschat.R;
import com.caesar.ken.caesarschat.ui.fragments.RegisterFragment;

/**
 * Created by e on 2/21/2018.
 */

public class RegisterActivity extends AppCompatActivity {
    private Toolbar mtoolbar;
    public static void startActivity(Context context){
        Intent intent = new Intent(context, RegisterActivity.class);// make the splash activity context start the register activity
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindViews();
        init();
    }
    public void bindViews(){
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    public void init(){
        setSupportActionBar(mtoolbar);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_content_register,
                                RegisterFragment.newInstance(), RegisterFragment.class.getSimpleName() );
        fragmentTransaction.commit();
    }
}
