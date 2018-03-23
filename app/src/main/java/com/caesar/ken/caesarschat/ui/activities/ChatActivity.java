package com.caesar.ken.caesarschat.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.caesar.ken.caesarschat.R;
import com.caesar.ken.caesarschat.ui.fragments.ChatFragment;
import com.caesar.ken.caesarschat.utils.Constants;

/**
 * Created by e on 2/21/2018.
 */

public class ChatActivity extends AppCompatActivity {
    private Toolbar mtoolbar;

    public static void startActivity(Context context, String receiver, String receiverUId, String firebaseToken) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(Constants.ARG_RECEIVER, receiver);
        intent.putExtra(Constants.ARG_RECEIVER_UID, receiverUId);
        intent.putExtra(Constants.ARG_FIREBASE_TOKEN, firebaseToken);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        bindViews();
        init();
    }
    public void bindViews(){
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
    }
    public void init(){
        setSupportActionBar(mtoolbar);
        mtoolbar.setTitle(getIntent().getExtras().getString(Constants.ARG_RECEIVER));

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_content_chat,
                ChatFragment.newInstance(getIntent().getExtras().getString(Constants.ARG_RECEIVER),
                        getIntent().getExtras().getString(Constants.ARG_RECEIVER_UID),
                        getIntent().getExtras().getString(Constants.ARG_FIREBASE_TOKEN)), ChatFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

}
