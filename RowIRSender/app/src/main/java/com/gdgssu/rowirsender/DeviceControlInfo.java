package com.gdgssu.rowirsender;

import android.util.Log;

import com.lge.hardware.IRBlaster.Device;
import com.lge.hardware.IRBlaster.IRFunction;

import java.util.List;

public class DeviceControlInfo {

    /**
     * 웨어러블로부터 전송받은 데이터를 파싱하며, 일시적으로 인스턴스를 통해 저장하여 활용하는 모델 클래스입니다
     */
    private final static String TAG = DeviceControlInfo.class.getSimpleName();

    public int deviceId;
    public String functionName;
    public String deviceName;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * String 형태의 funcLabel을 int형태의 Keycode로 변환합니다
     * @param deviceList
     * @param funcLabel
     * @return function id
     */
    public int getFunctionKeyCode(List<Device> deviceList, String funcLabel) {

        if (deviceList.size() == 0) {
            Log.e(TAG, "A device is not selected.");
            return -1;
        }
        for (Device device : deviceList){
            for (IRFunction function : device.KeyFunctions) {
                if (function.Name.equalsIgnoreCase(funcLabel)) {
                    return function.Id;
                }
            }
        }
        Log.e(TAG, "[" + funcLabel + "] search function failed");

        return -1;
    }

    // An example to use IR function labels.[E]
}
