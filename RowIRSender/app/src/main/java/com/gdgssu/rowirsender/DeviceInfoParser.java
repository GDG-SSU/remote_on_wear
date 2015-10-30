package com.gdgssu.rowirsender;

import com.google.gson.Gson;

public class DeviceInfoParser {

    /**
     * 소켓을 통해서 전송된 json형태의 데이터를 파싱하는 클래스입니다
     * @param jsonData
     * @return DeviceControlInfo
     */

    public static DeviceControlInfo parsedInfo(String jsonData){

        Gson gson = new Gson();
        return gson.fromJson(jsonData, DeviceControlInfo.class);
    }
}
