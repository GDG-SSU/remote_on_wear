package com.gdgssu.rowirsender;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.lge.hardware.IRBlaster.Device;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 유저가 컨트롤할 Device(AIRCON, TV, STB, DVD 등)을 저장하고 저장된 내용을 가져오는 PrefenceHelper 클래스
 * mIR.getDevices()를 이용해 애플리케이션을 완전히 종료하고 다시 실행해도 디바이스 목록을 가져올수있다면 이 클래스는 효용성이 없습니다
 */

public class DevicePreferenceHelper {
    private final static String PREF_NAME = "com.gdgssu.rowirsender.devicepref";
    public final static String PREF_DEVICE_STORE = "PREF_DEVICE_STORE";

    private Context mContext;

    public DevicePreferenceHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void setDevicePref(String key, List<Device> deviceList){
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        Gson gson = new Gson();
        String devicesJsonData = gson.toJson(deviceList);

        editor.putString(key, devicesJsonData);
        editor.apply();
    }

    public List<Device> getDevicePref(String key){
        List<Device> deviceList;
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);

        if (pref.contains(PREF_DEVICE_STORE)){
            String devicesJsonData = pref.getString(key, null);
            Gson gson = new Gson();
            Device[] deviceArray = gson.fromJson(devicesJsonData, Device[].class);
            deviceList = Arrays.asList(deviceArray);
        }else{
            return null;
        }

        return deviceList;
    }
}
