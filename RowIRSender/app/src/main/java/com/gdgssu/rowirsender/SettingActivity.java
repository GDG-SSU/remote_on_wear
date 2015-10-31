package com.gdgssu.rowirsender;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lge.hardware.IRBlaster.IRBlaster;
import com.lge.hardware.IRBlaster.IRBlasterCallback;

public class SettingActivity extends Activity {

    private IRBlaster mIR;
    private Handler mHandler;
    private boolean mIR_ready = false;
    private IRBlasterCallback mIrBlasterReadyCallBack = new IRBlasterCallback() {
        @Override
        public void IRBlasterReady() {
            Log.d("IRBlaster", "IRBlaster is really ready");

            final Runnable r = new Runnable() {
                public void run() {
                    mIR.getDevices();
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

        }

        @Override
        public void failure(int i) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

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
                if(mIR_ready == true && mIR != null)
                    result = mIR.addDevice();
            }
        });
    }
}
