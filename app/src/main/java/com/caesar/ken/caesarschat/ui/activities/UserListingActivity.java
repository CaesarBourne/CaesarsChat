package com.caesar.ken.caesarschat.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.caesar.ken.caesarschat.R;
import com.caesar.ken.caesarschat.core.logout.LogoutMainCore;
import com.caesar.ken.caesarschat.models.User;
import com.caesar.ken.caesarschat.ui.adapters.UserListingPageAdapter;
import com.caesar.ken.caesarschat.ui.fragments.UsersFragment;
import com.caesar.ken.caesarschat.utils.Constants;

/**
 * Created by e on 3/15/2018.
 */

public class UserListingActivity extends AppCompatActivity {
    private TabLayout mytabLayout;
    private ViewPager myviewPager;
    private Toolbar myToolbar;
    private LogoutMainCore logoutMainCore;
    public static void startActivity(Context context){
        Intent intent = new Intent(context, UserListingActivity.class);
        context.startActivity(intent);
    }
    public static void startActivity(Context context, int flags){
        Intent intent = new Intent(context, UserListingActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_listing);
        mytabLayout = (TabLayout) findViewById(R.id.tab_layout_user_listing);//TabLayout provides a horizontal layout to display tabs.
        myviewPager = (ViewPager) findViewById(R.id.view_pager_user_listing);/*Layout manager is a view that allows the user to flip left
                                                                                    and right through pages of data.*/
        logoutMainCore = new LogoutMainCore(this);
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
//the support fragment manager below is gotten from the fragment attached to this activity in this case its the fragmentuserList which is a linearMa
        UserListingPageAdapter userListingPageAdapter = new UserListingPageAdapter(getSupportFragmentManager());
        myviewPager.setAdapter(userListingPageAdapter);
        //attach tab layout with the view pager  below and bind views from the viewpager to the tablayout
        mytabLayout.setupWithViewPager(myviewPager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_listing, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_logouts:
            logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void logout(){
        new AlertDialog.Builder(this)
                .setTitle(R.string.logout)
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                logoutMainCore.onPerformFirebaseLogout();
                            }
                        }
                )
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }


    public void onLogoutSuccess(String log){
        Toast.makeText(this, log, Toast.LENGTH_LONG).show();
        LoginActivity.startIntent(this,Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    public void logoutFailure(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

//    @Override
//    public void userClicked(User user) {
//        Intent intent = new Intent(UserListingActivity.this, ChatActivity.class);
//        intent.putExtra(Constants.ARG_RECEIVER, user.email);
//        intent.putExtra(Constants.ARG_RECEIVER_UID, user.uid);
//        intent.putExtra(Constants.ARG_FIREBASE_TOKEN, user.token);
//        startActivity(intent);
//    }
}
