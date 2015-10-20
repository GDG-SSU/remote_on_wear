package com.gdgssu.rowirsender;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lge.hardware.IRBlaster.IRAction;
import com.lge.hardware.IRBlaster.IRBlaster;
import com.lge.hardware.IRBlaster.IRBlasterCallback;
import com.lge.hardware.IRBlaster.ResultCode;

public class RowIRService extends Service implements SocketReceiveListener {

    private final static String TAG = RowIRService.class.getSimpleName();
    private IRBlaster mIR;
    private boolean mIR_ready = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");

        mIR = IRBlaster.getIRBlaster(getApplicationContext(), mIrBlasterReadyCallback);
        if (mIR == null){
            Log.e(TAG, "No IR Blaster in this device");
        }

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
        DeviceControlInfo deviceControlInfo = DeviceInfoParser.parsedInfo(message);

        int controlFunctionCode = deviceControlInfo.getFunctionKeyCode(deviceControlInfo.getFunctionName());

        mIR.sendIR(new IRAction(deviceControlInfo.getDeviceId(), controlFunctionCode, 0));
    }

    private IRBlasterCallback mIrBlasterReadyCallback = new IRBlasterCallback() {
        @Override
        public void IRBlasterReady() {
            Log.d(TAG, "IRBlaster is ready");
            mIR_ready = true;
        }

        @Override
        public void learnIRCompleted(int i) {

        }

        @Override
        public void newDeviceId(int i) {

        }

        @Override
        public void failure(int i) {
            Log.e(TAG, "Error: "+ ResultCode.getString(i));
        }
    };
}
