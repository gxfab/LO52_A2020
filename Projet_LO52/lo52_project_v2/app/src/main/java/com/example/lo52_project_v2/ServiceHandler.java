package com.example.lo52_project_v2;

import android.app.Activity;
import android.widget.Toast;

public class ServiceHandler extends  Thread /** Whichever class you extend */ {

    private final Activity mActivity;
     static String message;

    public ServiceHandler(Activity activity, String message) {
        this.mActivity = activity;
        this.message = message;
    }

    @Override
    public void run() {
        mActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(mActivity, ServiceHandler.message, Toast.LENGTH_LONG).show();
            }

        });
    }
}