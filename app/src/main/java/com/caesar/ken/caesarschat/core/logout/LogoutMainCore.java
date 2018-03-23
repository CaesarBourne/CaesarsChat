package com.caesar.ken.caesarschat.core.logout;

import com.caesar.ken.caesarschat.ui.activities.UserListingActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by e on 3/15/2018.
 */

public class LogoutMainCore {

    UserListingActivity userListingActivity;

    public void onPerformFirebaseLogout(){

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            FirebaseAuth.getInstance().signOut();
           userListingActivity.onLogoutSuccess("succesfullylogout");
        }
        else {
            userListingActivity.logoutFailure("did not suucesfully logout");
        }
    }
}
