package com.gdgssu.rowclient.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdgssu.rowclient.R;
import com.gdgssu.rowclient.model.Device;

import java.util.ArrayList;

public class MainActivity extends Activity implements WearableListView.ClickListener {

    private WearableListView listview;
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                initView(stub);
            }
        });

        //new Thread(new ControlSendingThread(getApplicationContext())).start();
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
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Toast.makeText(getBaseContext(), "test", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTopEmptyRegionClick() {

    }
}
