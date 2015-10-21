package com.gdgssu.rowirsender;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lge.hardware.IRBlaster.Device;
import com.lge.hardware.IRBlaster.IRAction;
import com.lge.hardware.IRBlaster.IRBlaster;
import com.lge.hardware.IRBlaster.IRBlasterCallback;
import com.lge.hardware.IRBlaster.ResultCode;

import java.util.List;

public class RowIRService extends Service implements SocketReceiveListener {

    /**
     * 이 애플리케이션에서 웨어러블의 명령을 기다리는 Thread를 생성하고,
     * 그 명령을 IR 송신을 이용하여 수행하는 중심적인 역할을 담당합니다.
     */

    private final static String TAG = RowIRService.class.getSimpleName();

    private IRBlaster mIR;
    private DevicePreferenceHelper prefHelper;

    private boolean mIR_ready = false;
    private List<Device> deviceList = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");

        initInstance();

        if (mIR != null) {
            deviceList = prefHelper.getDevicePref(DevicePreferenceHelper.PREF_DEVICE_STORE);
            new SocketReceiverThread().start();
        } else {
            Log.e(TAG, "No IR Blaster in this device");
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void initInstance() {
        mIR = IRBlaster.getIRBlaster(getApplicationContext(), mIrBlasterReadyCallback);
        prefHelper = new DevicePreferenceHelper(getApplicationContext());
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

        int controlFunctionCode = deviceControlInfo.getFunctionKeyCode(deviceList, deviceControlInfo.getFunctionName());

        if (controlFunctionCode != -1) {
            mIR.sendIR(new IRAction(deviceControlInfo.getDeviceId(), controlFunctionCode, 0));
        }
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
            Log.e(TAG, "Error: " + ResultCode.getString(i));
        }
    };
}
