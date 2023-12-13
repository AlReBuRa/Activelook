package com.activelook.demo;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;


public class UdpServer implements Runnable {

    public int Port = 10562;
    public String Host = "239.255.255.255";
    public int Timeout = 5000;
    private MulticastSocket socket = null;


    // The object member variable can be shared between multiple threads if the same object instance is passed to MyTask executing in multiple threads
    public UdpServer() {

    }

    public void open(String host, int port, int timeout){
        this.Host = host;
        this.Port = port;
        this.Timeout = timeout;

        if(socket != null){
            socket.disconnect();
            socket.close();
        }
        try {
            InetAddress group = InetAddress.getByName(Host);
            socket = null;
            socket = new MulticastSocket(Port);
            socket.joinGroup(group);
            socket.setSoTimeout(Timeout);
            System.out.println("Open UDP multicast socket in group " + Host + " on port: " + Port);
        }
        catch (Exception ex) {
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


        while (true) {

            try {
                if(socket != null)
                    socket.receive(packet);
                else {
                    System.out.println("UDP multicast socket is null");
                    Thread.sleep(200);
                    continue;
                }

            } catch (IOException e) {
                String errorMessage = "An error occurred: " + e.getMessage();
                System.out.println(errorMessage);
                continue;
            } catch (InterruptedException e) {
                System.out.println("Error in thread sleep !!!");
                throw new RuntimeException(e);
            }

            try {
                String strData = new String(buf, StandardCharsets.UTF_8);
                MainActivity.IlsObject = new JSONObject(strData);
                //System.out.println("TopAwsMeas: " + MainActivity.IlsObject.getString("TopAwsMeas"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }



        }

    }

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
