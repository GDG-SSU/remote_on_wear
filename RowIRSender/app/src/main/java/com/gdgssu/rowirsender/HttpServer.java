package com.gdgssu.rowirsender;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.lge.hardware.IRBlaster.Device;
import com.lge.hardware.IRBlaster.IRBlaster;
import com.lge.hardware.IRBlaster.IRBlasterCallback;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * Created by everyone on 15. 10. 31.
 */
public class HttpServer extends Service {

    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    ServerThread serverThread = null;


    private IRBlaster mIR;
    private Handler mHandler;
    private boolean mIR_ready = false;
    private Device[] mDevices;

    private String deviceData;

    private TextView mDeviceList;

    @Override
    public void onCreate() {
        mHandler = new Handler();

        if (IRBlaster.isSdkSupported(getApplicationContext())) {
            mIR = IRBlaster.getIRBlaster(this, mIrBlasterReadyCallBack);
        } else {
            Log.e("TAG", "sdk not supported IR");
        }

        Thread runnable = new Thread() {
            @Override
            public void run() {
                Log.i("HttpServer", "Start");
                try {
                    serverSocket = new ServerSocket(8080);
                    while (true) {
                        Socket client = serverSocket.accept();
                        Log.i("HttpServer", "Socket Accept");

                        if (client == null) {
                            Log.e("HttpServer", "Client Socket is null");
                            break;
                        }
                        serverThread = new ServerThread(client);
                        serverThread.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("HttpServer", e.toString());
                }
            }
        };
        runnable.start();
    }

    private IRBlasterCallback mIrBlasterReadyCallBack = new IRBlasterCallback() {
        @Override
        public void IRBlasterReady() {
            Log.d("IRBlaster", "IRBlaster is really ready");

            final Runnable r = new Runnable() {
                public void run() {
                    mDevices = mIR.getDevices();
                    deviceData="";
                    for (Device i : mDevices) {
                        deviceData = deviceData+ i.Name + "/\n";
                    }
                }
            };
            mHandler.post(r);
            mIR_ready = true;
        }

        @Override
        public void learnIRCompleted(int i) {

        }

        @Override
        public void newDeviceId(int i) {
            Log.i("HttpServer", i + "");
        }

        @Override
        public void failure(int i) {

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    class ServerThread extends Thread {

        private Socket clientSocket;
        PrintStream ps = null;

        public ServerThread(Socket connectionSocket) {
            this.clientSocket = connectionSocket;
            Log.i("HttpServer", "Connection");
        }

        @Override
        public void run() {
            BufferedReader inFromClient = null;
            DataOutputStream outputStream = null;
            try {
                clientSocket.getInputStream();
                inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                outputStream = new DataOutputStream(clientSocket.getOutputStream());
                String requestMessage = inFromClient.readLine();
                Log.i("HttpServer", requestMessage + "message");
                StringTokenizer tokenizer = new StringTokenizer(requestMessage);
                String arrParm[] = requestMessage.split(" ");

                if (!"GET".equals(arrParm[0])) {
                    outputStream.writeBytes("HTTP/1.0 400 Bad Request Message \r\n");
                    outputStream.writeBytes("Connection: close\r\n");
                    outputStream.writeBytes("\r\n");
                    Log.e("HttpServer", "Bad Request");
                    close();
                    return;
                }

                String request = arrParm[1];
                if ("/getDevice".equals(request)) {
                    outputStream.writeBytes("Http/1.0 200 Document Follows \r\n");
                    outputStream.writeBytes("Content-Type: text/html \r\n");
                    outputStream.writeBytes("Content-Length: " + deviceData.length() + "\r\n");
                    outputStream.writeBytes("\r\n");
                    outputStream.writeBytes(deviceData);
                    close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("HttpServer", e.toString());
            }
        }

        private void close() throws IOException {
            clientSocket.close();
            Log.e("HttpServer", "Close Connection");
        }

    }
}
