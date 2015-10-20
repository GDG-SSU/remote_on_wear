package com.gdgssu.rowirsender;

public interface SocketReceiveListener {

    /**
     * SocketReceiverThread에서 RowIRService로 JsonText 데이터를 전달하는 기능을 수행하는 인터페이스입니다
     * @param message
     */

    void onReceive(String message);

}
