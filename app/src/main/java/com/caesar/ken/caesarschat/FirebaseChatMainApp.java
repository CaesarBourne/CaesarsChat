package com.caesar.ken.caesarschat;

import android.app.Application;

/**
 * Created by e on 2/20/2018.
 */

public class FirebaseChatMainApp extends Application{

    private static boolean isChatActivityOpen = false;

    public static boolean isChatActivityOpen(){
        return isChatActivityOpen;
    }

    public static void setChatActivityOpen(boolean tisChatActivityOpen){
        FirebaseChatMainApp.isChatActivityOpen = tisChatActivityOpen;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

