package com.gdgssu.rowirsender;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RowIRReceiver extends BroadcastReceiver {

    /**
     * 스마트폰이 부팅되자마자 RowIRService를 구동하게끔하는 기능을 담당하는 Receiver입니다
    * @param context
    * @param intent
    */

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("gdgssu.com.rowirsender.getaction")){
            context.startService(new Intent(context, RowIRService.class));
        }else{
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
