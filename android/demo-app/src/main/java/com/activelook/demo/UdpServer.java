package com.activelook.demo;


import android.appwidget.AppWidgetProvider;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;


public class UdpServer extends AppCompatActivity implements Runnable {

    public int Port = 10562;
    public String Host = "239.255.255.255";
    public int Timeout = 5000;
    private MulticastSocket socket = null;
    private MainActivity  owner;

    // The object member variable can be shared between multiple threads if the same object instance is passed to MyTask executing in multiple threads
    public UdpServer(MainActivity  parentowner) {
        owner = parentowner;
    }

    public void open(String host, int port, int timeout) {
        this.Host = host;
        this.Port = port;
        this.Timeout = timeout;
        this.open();
    }

    public void open(){
        if(socket != null){
            socket.disconnect();
            socket.close();
            MainActivity.IsMulticastConnected = false;
        }
        try {
            InetAddress group = InetAddress.getByName(Host);
            socket = null;
            socket = new MulticastSocket(Port);
            socket.joinGroup(group);
            socket.setSoTimeout(Timeout);
            socket.setReuseAddress(true);
            System.out.println("Open UDP multicast socket in group " + Host + " on port: " + Port);
            MainActivity.IsMulticastConnected = true;
        }
        catch (Exception ex) {
            MainActivity.IsMulticastConnected = false;
            socket = null;
            System.out.println("Error to create socket on port: " + Port);
        }

    }

    @Override
    public void run() {

        // this.object
        System.out.printf("Start thread to received variables in UDP multicast (" + Thread.currentThread().getName() + ")");


        System.out.println("Start receiving");
        DatagramPacket packet;
        int bufferSize = 4096;
        byte[] buf = new byte[bufferSize];
        packet = new DatagramPacket(buf, buf.length);
        long start = System.currentTimeMillis();


        while (true) {

            if (MainActivity.IsMulticastConnected) {

                try {
                    if (socket != null) {
                        socket.receive(packet);
                        MainActivity.IsMulticastConnected = true;
                        long finish = System.currentTimeMillis();
                    } else {
                        System.out.println("UDP multicast socket is null");
                        MainActivity.IsMulticastConnected = false;
                        Thread.sleep(200);
                        continue;
                    }

                } catch (IOException e) {
                    System.out.println("An error occurred: " + e.getMessage());
                    MainActivity.IsMulticastConnected = false;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(owner.getBaseContext(), "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    continue;

                } catch (InterruptedException e) {
                    System.out.println("Error in thread sleep !!!");
                    MainActivity.IsMulticastConnected = false;
                    throw new RuntimeException(e);
                }

                try {
                    String strData = new String(buf, StandardCharsets.UTF_8);
                    MainActivity.IlsObject = new JSONObject(strData);
                    MainActivity.IsMulticastConnected = true;
                    //System.out.println("TopAwsMeas: " + MainActivity.IlsObject.getString("TopAwsMeas"));
                    //System.out.println("Elapse: " + (System.currentTimeMillis() - start));
                    start = System.currentTimeMillis();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }else{
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Try to connect to " + Host + " on port: " + Port);
                this.open();
            }


        }


    }

    /*
    private Runnable show_toast = new Runnable() {
        public void run()
        {
            Toast.makeText(owner.getBaseContext(), "My Toast message", Toast.LENGTH_SHORT).show();
        }
    };
     */

/*
    public void postToastMessage(final String message) {
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(MainActivity.getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private Toast toast(Object data) {
        Log.d("viewerCommands", data.toString());
        Toast toast = Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

 */


}
