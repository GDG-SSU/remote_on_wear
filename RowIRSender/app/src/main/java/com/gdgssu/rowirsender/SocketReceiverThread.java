package com.gdgssu.rowirsender;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketReceiverThread extends Thread {

    /**
     * 웨어러블측에서 보내오는 소켓 통신 데이터를 listen하고있는 Thread입니다.
     * 본 Thread는 RowIRSender의 핵심적인 역할을 담당하는 RowIRService 클래스를 통해 작동되기 시작합니다.
     * 현재 포트번호는 임의로 5000번을 잡아놓았습니다.
     * <p/>
     * service.onReceive()를 통해 RowIRService로 JsonText 데이터를 전달합니다.
     */

    private final static String TAG = SocketReceiverThread.class.getSimpleName();
    private RowIRService service = new RowIRService();

    @Override
    public void run() {
        while (true) {
            try{
                ServerSocket serverSocket = new ServerSocket(5000);

                Socket socket = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String deliverMessage;
                deliverMessage=reader.readLine();
                service.onReceive(deliverMessage);

            }catch (IOException e){
                Log.d(TAG, e.getMessage());
            }
        }
    }
}
