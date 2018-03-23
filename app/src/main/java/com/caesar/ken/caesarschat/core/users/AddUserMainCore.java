package com.caesar.ken.caesarschat.core.users;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.caesar.ken.caesarschat.models.User;
import com.caesar.ken.caesarschat.ui.fragments.RegisterFragment;
import com.caesar.ken.caesarschat.utils.Constants;
import com.caesar.ken.caesarschat.utils.SharedPrefUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by e on 3/5/2018.
 */

public class AddUserMainCore {
    RegisterFragment registerFragment;
    public AddUserMainCore(RegisterFragment registerFragment) {
        this.registerFragment = registerFragment;
    }

    //as the name implies after user is created with FirebaseAuth he is saved to the database
    public void addUserToFirebase(final Context context, FirebaseUser firebaseUser){

        DatabaseReference databaseChild = FirebaseDatabase.getInstance().getReference();
        User user = new
                User(firebaseUser.getUid(),
                firebaseUser.getEmail(),new SharedPrefUtil(context).getString(Constants.ARG_FIREBASE_TOKEN));
        databaseChild.child(Constants.ARG_USERS)
                .child(Constants.ARG_UID)
                .setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            registerFragment.onAddUserSuccess(" adding the user to firebase is succesful");
                        }
                        else {
                            registerFragment.onAddUserFailure(task.getException().getMessage());
                        }
                    }
                });
    }
}
