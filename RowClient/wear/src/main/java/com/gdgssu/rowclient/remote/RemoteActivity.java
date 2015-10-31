package com.gdgssu.rowclient.remote;

import android.app.Activity;
import android.os.Bundle;

import com.gdgssu.rowclient.R;

public class RemoteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int remoteState = getIntent().getIntExtra("remote", -1);

        if (remoteState == -1) {
            try {
                throw new Exception("remote status is strange");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (remoteState == 0) {
            setContentView(R.layout.activity_remote);
        } else if (remoteState == 1) {

        } else if (remoteState == 2) {

        } else if (remoteState == 3) {

        }
    }
}
