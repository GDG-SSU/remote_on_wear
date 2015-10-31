package com.gdgssu.rowclient.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.widget.TextView;

import com.gdgssu.rowclient.ControlSendingThread;
import com.gdgssu.rowclient.R;

public class MainActivity extends Activity {

    private TextView mTextView;
    private WearableListView listview;
    private MainAdapter mAdapter;

    String[] elements = {"List Item 1", "List Item 2", "List Item 3"};

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
        mTextView = (TextView) stub.findViewById(R.id.text);
        listview = (WearableListView) stub.findViewById(R.id.main_listview);

        mAdapter = new MainAdapter(getBaseContext(), elements);

        listview.setAdapter(mAdapter);
    }
}
