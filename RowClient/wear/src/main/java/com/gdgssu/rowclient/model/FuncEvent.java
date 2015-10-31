package com.gdgssu.rowclient.model;

import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;

/**
 * Created by lk on 15. 11. 1..
 */
public class FuncEvent {

    public static final String FUNC_ITEM_PATH_PREFIX = "/func";
    public static final String FUNC_NAME = "func_name";

    private String funcName;

    public PutDataMapRequest toPutDataMapRequest(String func){
        final PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(makeDataItemPath(func));
        DataMap data = putDataMapRequest.getDataMap();
        data.putString(FUNC_NAME, putDataMapRequest.getUri().toString());

        return putDataMapRequest;
    }

    private static String makeDataItemPath(String func) {
        return FUNC_ITEM_PATH_PREFIX + func;
    }
}
