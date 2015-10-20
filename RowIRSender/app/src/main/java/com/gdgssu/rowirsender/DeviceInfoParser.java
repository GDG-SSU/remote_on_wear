package com.gdgssu.rowirsender;

import com.google.gson.Gson;

public class DeviceInfoParser {
    public static DeviceControlInfo parsedInfo(String jsonData){

        Gson gson = new Gson();
        return gson.fromJson(jsonData, DeviceControlInfo.class);
    }
}
