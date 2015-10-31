package com.gdgssu.rowirsender;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.lge.hardware.IRBlaster.Device;
import com.lge.hardware.IRBlaster.IRAction;
import com.lge.hardware.IRBlaster.IRBlaster;
import com.lge.hardware.IRBlaster.IRBlasterCallback;
import com.lge.hardware.IRBlaster.IRFunction;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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

    private List<Device> deviceList;
    private String deviceData;

    private TextView mDeviceList;

    @Override
    public void onCreate() {
        mHandler = new Handler();
        deviceList = new ArrayList<>();

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
                        deviceList.add(i);
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
                if(requestMessage == null) {
                    close();
                    return;
                }
                Log.i("HttpServer", requestMessage);

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
                }else if("/action?".contains(arrParm[1])){
                    String message = arrParm[1].substring(7);
                    Log.i("HttpServer", message);
                    DeviceControlInfo deviceControlInfo = DeviceInfoParser.parsedInfo(message);

                    int controlFunctionCode = deviceControlInfo.getFunctionKeyCode(deviceList, deviceControlInfo.getFunctionName());
                    if (controlFunctionCode != -1) {
                        mIR.sendIR(new IRAction(deviceControlInfo.getDeviceId(), controlFunctionCode, 0));
                    }

                    Runnable post = new Runnable() {
                        @Override
                        public void run() {
                            mIR.stopIR();
                        }
                    };

                    close();
                }else if("/VoulmUp".equals(request)) {

                }else if("/VoulmDown".equals(request)) {

                }else if("/channel?".contains(request)) {

                }else if("/TempUp".equals(request)) {
                    AirCone(outputStream ,1);
                }else if("/TempDown".equals(request)) {
                    AirCone(outputStream, 2);
                }else if("/AirConPower".equals(request)){
                    AirCone(outputStream, 0);
                }else{
                    outputStream.writeBytes("HTTP/1.0 400 Bad Request Message \r\n");
                    outputStream.writeBytes("Connection: close\r\n");
                    outputStream.writeBytes("\r\n");
                    Log.e("HttpServer", "Other Request");
                    close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("HttpServer", e.toString());
            }
        }

        private void AirCone(DataOutputStream outputStream, int code) throws IOException {
            Device device = mDevices[0];
            List<IRFunction> functions = device.KeyFunctions;
            IRFunction function = functions.get(code);
            mIR.sendIR(new IRAction(device.Id, function.Id, 0));
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIR.stopIR();
                }
            }, 1000);
            outputStream.writeBytes("Http/1.0 200 Document Follows \r\n");
            outputStream.writeBytes("Content-Type: text/html \r\n");
            outputStream.writeBytes("Content-Length: 0\r\n");
            outputStream.writeBytes("\r\n");
            close();
        }

        private void close() throws IOException {
            clientSocket.close();
            Log.e("HttpServer", "Close Connection");
        }

    }
}
