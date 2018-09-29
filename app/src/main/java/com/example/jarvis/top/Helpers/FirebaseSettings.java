package com.example.jarvis.top.Helpers;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseSettings extends FirebaseInstanceIdService {

    private static String Token;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        Token = FirebaseInstanceId.getInstance().getToken();
        Log.d("TESTEE", "Refreshed token: " + Token);
    }

    public static String getToken() {
        return Token;
    }
}
