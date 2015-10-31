package com.gdgssu.rowclient.model;

import android.graphics.drawable.Drawable;

/**
 * Created by lk on 15. 10. 31..
 */
public class Device {
    
    public String deviceName;
    public Drawable deviceImg;

    public Device(String deviceName, Drawable deviceImg) {
        this.deviceName = deviceName;
        this.deviceImg = deviceImg;
    }
}
