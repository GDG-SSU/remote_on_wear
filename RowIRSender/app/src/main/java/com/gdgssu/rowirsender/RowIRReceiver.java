package com.gdgssu.rowirsender;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RowIRReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("gdgssu.com.rowirsender.getaction")){
            context.startService(new Intent(context, RowIRService.class));
        }else{
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
