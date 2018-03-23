package com.caesar.ken.caesarschat.fcm;

import android.util.Log;

import com.caesar.ken.caesarschat.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by e on 3/6/2018.
 */

public class CaesarsFirebaseInstanceIDService extends FirebaseInstanceIdService {
        private static final String TAG = "FirebaseIIDService";
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is also called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    //
    @Override
    public void onTokenRefresh(){
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String token = FirebaseInstanceId.getInstance().getToken();
            Log.v(TAG, "success in getting instance "+ token);
            onSendRegistrationToServer(token);
        }
    }

    //save the token in the child for this specific user that has the uid
    public void onSendRegistrationToServer(final String token){
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference();
        databaseReference.child(Constants.ARG_USERS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(Constants.ARG_FIREBASE_TOKEN)
                .setValue(token);
    }
}
