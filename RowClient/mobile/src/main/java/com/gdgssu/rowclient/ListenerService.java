package com.gdgssu.rowclient;

import android.util.Log;
import android.widget.Toast;

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
public class ListenerService extends WearableListenerService {

    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();

        mGoogleApiClient = new GoogleApiClient.Builder(this.getApplicationContext())
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);

        Log.d("TAG", "onDataChanged");
//
//        for (DataEvent event : dataEvents) {
//            DataMapItem mapdataItem = DataMapItem.fromDataItem(event.getDataItem());
//            DataMap data = mapdataItem.getDataMap();
//            String funcname = data.getString("func_name");
//
//            Toast.makeText(getApplicationContext(), "TAG", Toast.LENGTH_SHORT).show();
//        }
    }
}
