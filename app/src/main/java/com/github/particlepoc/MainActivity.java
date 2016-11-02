package com.github.particlepoc;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String ACTION_MESSAGE_RECEIVED = BuildConfig.APPLICATION_ID + ".ACTION_MESSAGE_RECEIVED";
    private static final String EXTRA_MESSAGE = "message";
    private static final Map<String, Integer> COLORS;
    private ViewGroup list;

    static {
        COLORS = new HashMap<>();
        COLORS.put("green", R.drawable.circle_green);
        COLORS.put("yellow", R.drawable.circle_yellow);
        COLORS.put("red", R.drawable.circle_red);
    }

    public static void onMessageReceived(Context context, RemoteMessage message) {
        context.startActivity(new Intent(context, MainActivity.class)
                .setAction(ACTION_MESSAGE_RECEIVED)
                .putExtra(EXTRA_MESSAGE, message));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        list = (ViewGroup) findViewById(R.id.list);

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
        if (ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            RemoteMessage message = intent.getParcelableExtra(EXTRA_MESSAGE);

            View item = getLayoutInflater().inflate(R.layout.main_item, list, false);

            TextView text1 = (TextView) item.findViewById(R.id.text1);
            text1.setText(message.getNotification().getTitle());
            if (TextUtils.isEmpty(text1.getText())) {
                text1.setText(R.string.message);
            }

            TextView text2 = (TextView) item.findViewById(R.id.text2);
            text2.setText(message.getNotification().getBody());

            Integer circleDrawableId = COLORS.get(message.getData().get("color"));
            if (circleDrawableId != null) {
                ((ImageView) item.findViewById(R.id.icon)).setImageResource(circleDrawableId);
            }

            list.addView(item);
        }
    }

}
