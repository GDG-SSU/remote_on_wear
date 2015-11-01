package com.gdgssu.rowclient.remote;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;

import com.gdgssu.rowclient.R;
import com.gdgssu.rowclient.model.FuncEvent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RemoteActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = RemoteActivity.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;
    public static int remotestatic = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initGoogleApiClient();

        int remoteState = getIntent().getIntExtra("remote", -1);

        if (remoteState == -1) {
            try {
                throw new Exception("remote status is strange");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (remoteState == 0) {
            initTV();
        } else if (remoteState == 1) {
            initAircon();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    @Override
    protected void onStop() {
        super.onStop();

        mGoogleApiClient.disconnect();
    }

    private void initGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    private void initAircon() {
        setContentView(R.layout.activity_remote_aircon);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.remote_aircon_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                stub.findViewById(R.id.remote_aircon_up).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendFuncData();
                        remotestatic = 0;
                    }
                });
                stub.findViewById(R.id.remote_aircon_down).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendFuncData();
                        remotestatic = 1;
                    }
                });
            }
        });
    }

    private void sendFuncData() {

        new SendDataTask().execute();

    }

    private void initTV() {
        setContentView(R.layout.activity_remote_tv);
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public class SendDataTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            mGoogleApiClient.blockingConnect(100, TimeUnit.MILLISECONDS);


            final PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/func");
            DataMap dataMap = putDataMapRequest.getDataMap();
            dataMap.putInt("INT", remotestatic);

            if (mGoogleApiClient.isConnected()) {
                Log.d("Shot", "SHot");
                Wearable.DataApi.putDataItem(
                        mGoogleApiClient, putDataMapRequest.asPutDataRequest()).await();
            } else {
                Log.e(TAG, "Failed to send data item: " + putDataMapRequest
                        + " - Client disconnected from Google Play Services");
            }


            return null;
        }
    }
}
