package com.gdgssu.rowclient.main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gdgssu.rowclient.R;
import com.gdgssu.rowclient.model.Device;
import com.gdgssu.rowclient.remote.RemoteActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity implements WearableListView.ClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    private WearableListView listview;
    private MainAdapter mAdapter;
    private Handler mHandler;
    private int click = 0;
    private Runnable oneClick = null;

    private static int powerFuncCode = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.main_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                initView(stub);
            }
        });

        initGoogleApiClient();

        //new Thread(new ControlSendingThread(getApplicationContext())).start();
    }

    private void initGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    private void initView(WatchViewStub stub) {
        listview = (WearableListView) stub.findViewById(R.id.main_listview);

        ArrayList<Device> devices = new ArrayList<>();
        devices.add(new Device("Aircon", ContextCompat.getDrawable(getBaseContext(), R.drawable.airc_on)));
        devices.add(new Device("TV", ContextCompat.getDrawable(getBaseContext(), R.drawable.tv_on)));
        devices.add(new Device("DVD", ContextCompat.getDrawable(getBaseContext(), R.drawable.dvd_on)));

        mAdapter = new MainAdapter(getBaseContext(), devices);

        listview.setAdapter(mAdapter);
        listview.setClickListener(this);

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

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {

        click++;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (click >= 2) {
                    Toast.makeText(getApplicationContext(), "Double", Toast.LENGTH_SHORT).show();
                    click = 0;
                    mHandler.removeCallbacks(oneClick);
                    oneClick = null;
                    // 파워

                    new SendPowerFuncTask().execute();

                }
            }
        });

        if (oneClick == null) {
            oneClick = new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getBaseContext(), RemoteActivity.class);
                    intent.putExtra("remote", 1);
                    startActivity(intent);
                }
            };

            mHandler.postDelayed(oneClick, 700);
        }


    }

    @Override
    public void onTopEmptyRegionClick() {

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

    public class SendPowerFuncTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            mGoogleApiClient.blockingConnect(100, TimeUnit.MILLISECONDS);

            final PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/func");
            DataMap dataMap = putDataMapRequest.getDataMap();
            dataMap.putInt("INT", powerFuncCode);

            if (mGoogleApiClient.isConnected()) {
                Log.d("Shot", "SHot");
                Wearable.DataApi.putDataItem(
                        mGoogleApiClient, putDataMapRequest.asPutDataRequest()).await();
            } else {
                Log.e("TAG", "Failed to send data item: " + putDataMapRequest
                        + " - Client disconnected from Google Play Services");
            }


            return null;
        }
    }
}
