package com.gdgssu.rowirsender;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lge.hardware.IRBlaster.Device;
import com.lge.hardware.IRBlaster.IRBlaster;
import com.lge.hardware.IRBlaster.IRBlasterCallback;

public class SettingActivity extends Activity {

    private final String TAG = SettingActivity.class.getSimpleName();

    private IRBlaster mIR;
    private Handler mHandler;
    private boolean mIR_ready = false;
    private Device[] mDevices;

    private TextView mDeviceList;

    private IRBlasterCallback mIrBlasterReadyCallBack = new IRBlasterCallback() {
        @Override
        public void IRBlasterReady() {
            Log.d("IRBlaster", "IRBlaster is really ready");

            final Runnable r = new Runnable() {
                public void run() {
                    mDevices = mIR.getDevices();
                    for(Device i: mDevices){
                        mDeviceList.append(i.Name+'\n');
                    }
                }
            };
            mHandler.post(r);
            mIR_ready = true;
        }

        @Override
        public void learnIRCompleted(int i) {

        }

        @Override
        public void newDeviceId(int i) {
            Log.i(TAG, i+"");
        }

        @Override
        public void failure(int i) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        mHandler = new Handler();


        if (IRBlaster.isSdkSupported(getApplicationContext())) {
            mIR = IRBlaster.getIRBlaster(this, mIrBlasterReadyCallBack);
        } else {
            Log.e("TAG", "sdk not supported IR");
        }

        Button addDeviceButton = (Button) findViewById(R.id.add_device);

        addDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 디바이스를 추가하는 Activity로 Intent를 발생시키는 SDK 함수
                int result;
                if (mIR_ready == true && mIR != null)
                    result = mIR.addDevice();
            }
        });
    }

    private void initView() {
        mDeviceList = (TextView) findViewById(R.id.tv_setting_devicelist);

    }
}
