package com.gdgssu.rowclient;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.gdgssu.rowclient.model.Person;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

/**
 *
 * Created by lk on 15. 11. 1..
 */
public class ListenerService extends WearableListenerService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("TAG", "onCreate");

        mGoogleApiClient = new GoogleApiClient.Builder(this.getBaseContext())
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();

//        if (mGoogleApiClient.isConnected()){
//            Log.d("TAG", "isConnected");
//        }
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {

        Log.d("TAG", "onDataChanged");

        for (DataEvent event : dataEvents){
            DataMapItem mapDataItem = DataMapItem.fromDataItem(event.getDataItem());
            DataMap dataMap = mapDataItem.getDataMap();

            int data = dataMap.getInt("INT");

            if (data==0){
                HttpRequestHelper.get("/TempUp");
            }else if (data==1){
                HttpRequestHelper.get("/TempDown");
            }else if (data==2){
                HttpRequestHelper.get("/AirConPower");
            }

        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("TAG", "onConnected");

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("TAG", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("TAG", connectionResult.getErrorMessage());
    }
}
