package com.github.particlepoc;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        checkGooglePlayServices();
        FirebaseMessaging.getInstance().subscribeToTopic("alerts");

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkGooglePlayServices();
    }

    private void checkGooglePlayServices() {
        GoogleApiAvailability gaa = GoogleApiAvailability.getInstance();

        int errorCode = gaa.isGooglePlayServicesAvailable(this);
        if (errorCode != ConnectionResult.SUCCESS && gaa.isUserResolvableError(errorCode)) {
            Dialog dialog = gaa.getErrorDialog(this, errorCode, 0);
            dialog.show();
        }
    }

    private void handleIntent(Intent intent) {
        System.out.println(intent);
    }

}
