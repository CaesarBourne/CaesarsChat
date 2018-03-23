package com.caesar.ken.caesarschat.core.users;

import android.text.TextUtils;

import com.caesar.ken.caesarschat.models.User;
import com.caesar.ken.caesarschat.ui.fragments.UsersFragment;
import com.caesar.ken.caesarschat.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by e on 3/8/2018.
 */

public class GetUsersMainCore {
    //the user list is passed to the user fragment here
    UsersFragment usersFragmentChild;

    public GetUsersMainCore(UsersFragment usersFragmentChild) {
        this.usersFragmentChild = usersFragmentChild;
    }

    public void getAllUsersFromFirebase(){

        FirebaseDatabase.getInstance().getReference()
                .child(Constants.ARG_USERS)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                        List<User> users = new ArrayList<User>();
                        while (dataSnapshotIterator.hasNext()) {
                            DataSnapshot dataSnapshotChild = dataSnapshotIterator.next();
                            //pass the data snapshotChild to the user classs constructor for initialization
                            User user = dataSnapshotChild.getValue(User.class);
                            //if the currently signed in user is not the same fetched from the datasnapsot iterator add to the arraylist
                            if (!TextUtils.equals(user.uid, FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                users.add(user);
                            }
                        }
                        //this araylist is passed to the users fragment to list out all the users its an arraylist
                        usersFragmentChild.getallUsersSuccess(users);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        usersFragmentChild.onGetAllUsersFailure(databaseError.getMessage());
                    }
                });
    }
}
