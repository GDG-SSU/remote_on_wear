package com.gdgssu.rowclient;

import android.content.Context;

public class StateSyncThread extends Thread {

    /**
     * IRSender 애플리케이션과 소켓으로 연결하여 현재 가정에서 컨트롤할 디바이스의 종류를 받아와야한다
     * 만약 30초동안 찾지 못한다면 본 Thread는 종료된다
     */

    private int foundDeviceSecond = 0;
    private Context mContext;

    public StateSyncThread(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void run() {
        while(true){
            try {

                //Todo : 소켓을 IRSender측으로 보내는 로직 작성

                Thread.sleep(1000);
                foundDeviceSecond++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (foundDeviceSecond==30){
                break;
            }
        }
    }
}
