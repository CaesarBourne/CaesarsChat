package com.caesar.ken.caesarschat.core.registration;

import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.util.Log;

import com.caesar.ken.caesarschat.core.users.AddUserMainCore;
import com.caesar.ken.caesarschat.ui.fragments.RegisterFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by e on 3/5/2018.
 */

public class RegisterMainCore {
    RegisterFragment passtheUserToFragment;
    private static final String TAG = RegisterMainCore.class.getSimpleName();
//this is passed when the object is created to get the instance of that object
    public RegisterMainCore(RegisterFragment passtheUserToFragment) {
        this.passtheUserToFragment = passtheUserToFragment;
    }

    public void onPerformFirebaseRegistration(final Activity activity, final String email, final String password){
//a token is created when registered
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e(TAG, "performFirebaseRegistration:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()){
                            Log.e(TAG,"REGISTRATION NOT SUCCESSFUL");
                            passtheUserToFragment.onRegistrationFailure(task.getException().getMessage());
                        }else {
 //after the registration the usr is gotten back with the task and passed back to the fragment to add to the list in the database
                            passtheUserToFragment.onRegistrationSuccess(task.getResult().getUser());
                        }
                    }
                });
    }
}
