package com.github.particlepoc;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getName();

    @Override
    public void onTokenRefresh() {
        Log.d(TAG, "Refreshed token: " + FirebaseInstanceId.getInstance().getToken());
    }

}
