package com.gdgssu.rowirsender;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketReceiverThread extends Thread {

    private final static String TAG = SocketReceiverThread.class.getSimpleName();

    @Override
    public void run() {
        try{
            ServerSocket serverSocket = new ServerSocket(9999);

            Socket socket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String deliverMessage;
            deliverMessage=reader.readLine();

        }catch (IOException e){
            Log.d(TAG, e.getMessage());
        }
    }
}
