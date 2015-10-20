package com.gdgssu.rowirsender;

import com.lge.hardware.IRBlaster.Device;

public class ControlInfo {
    private Device device;
    private int function;

    public int getDeviceFunctionId(){
        return device.KeyFunctions.get(function).Id;
    }
}
