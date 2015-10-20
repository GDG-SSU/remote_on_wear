package com.gdgssu.rowirsender;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class RowIRService extends Service implements SocketReceiveListener {

    private final static String TAG = RowIRService.class.getSimpleName();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand");

        new SocketReceiverThread().start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onReceive(String message) {
        //Todo : 소켓을 통해 메시지가 전달되었을 때 처리할 로직 작성
    }
}
