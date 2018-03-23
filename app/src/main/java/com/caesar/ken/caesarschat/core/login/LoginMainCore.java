package com.caesar.ken.caesarschat.core.login;

import android.app.Activity;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.util.Log;

import com.caesar.ken.caesarschat.ui.fragments.LoginFragment;
import com.caesar.ken.caesarschat.utils.Constants;
import com.caesar.ken.caesarschat.utils.SharedPrefUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

/**
 * Created by e on 3/1/2018.
 */

public class LoginMainCore {
    LoginFragment loginFragment;

    public LoginMainCore(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
    }

    public void performFirebaseLogin(final Activity activity, final String email, String password){


        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "performFiirebaseLogin:onComplete"+task.isSuccessful());

                        if (task.isSuccessful()){
                            loginFragment.onLoginSuccesful(task.getResult().toString());
                            onUpdateFirebaseToken(task.getResult().getUser().getUid(),
                                    new SharedPrefUtil(activity.getApplicationContext()).getString(Constants.ARG_FIREBASE_TOKEN, null));
                        }
                        else {
                            loginFragment.onLoginFailure(task.getException().getMessage());
                        }
                    }
                });
    }
    public void onUpdateFirebaseToken(String uid, String token){
        FirebaseDatabase.getInstance().getReference()
                .child(Constants.ARG_USERS)
                .child(uid)
                .child(Constants.ARG_FIREBASE_TOKEN)
                .setValue(token);

    }
}
