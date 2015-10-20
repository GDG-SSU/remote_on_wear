package com.gdgssu.rowirsender;

import android.util.Log;

import com.lge.hardware.IRBlaster.Device;
import com.lge.hardware.IRBlaster.IRFunction;

public class DeviceControlInfo {

    /**
     * 웨어러블로부터 전송받은 데이터를 파싱하며, 일시적으로 인스턴스를 통해 저장하여 활용하는 모델 클래스입니다
     */

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

    public int getFunctionKeyCode(String funcLabel) {
        /**
         * 기능에 대한 KeyCode를 얻기위해서는 해당 기능을 활용하는 Device 객체가 있어야 한다
         * 그 객체들은 유저가 이 애플리케이션에서 추가 할 수 있고 또한 애플리케이션이 꺼지면 자동으로 Gson과 SharedPreference를 이용하여
         * 저장되며, 다시 애플리케이션이 켜지면 그것을 불러오는 로직을 구현해야한다
         */


//        if (mDeviceSelected == null) {
//            Log.e(TAG, "A device is not selected.");
//            return -1;
//        }
//
//        if ((mFunctionNames != null) && (mFunctionNames.length > 0)) {
//            for (IRFunction function: mDeviceSelected.KeyFunctions) {
//
//                if (function.Name.equalsIgnoreCase(funcLabel)) {
//                    return function.Id;
//                }
//            }
//            Log.e(TAG, "[" + funcLabel + "] search function failed");
//
//            return -1;
//        } else {
//            Log.e(TAG, "The list of function names doesn't exist.");
//            return -1;
//        }

        return -1;
    }

    // An example to use IR function labels.[E]
}
