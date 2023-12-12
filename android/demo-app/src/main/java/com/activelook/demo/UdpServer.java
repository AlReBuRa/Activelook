package com.activelook.demo;

import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;

public class UdpServer implements Runnable {

    // The count member variable is shared between multiple threads
    // that are executing the same instance of the MyTask runnable.
    private Object object;
    private JSONObject ILSobject;
    private String strData;

    private Object a;
    private Object b;

    // The object member variable can be shared between multiple threads if the same object instance is passed to MyTask executing in multiple threads
    public UdpServer(String strData) {
        this.strData = strData;
        ILSobject = MainActivity.ILSobject;
    }

    public UdpServer(Object object, JSONObject ilsData) {
        //this.IlsData = ilsData;
        this.object = object;
        ILSobject = MainActivity.ILSobject;

    }

    @Override
    public void run() {

        String threadName = Thread.currentThread().getName();

        // this.object
        System.out.printf("[%s] %s\n", threadName, this.object);

        Handler hd;
        MulticastSocket socket = null;
        InetAddress group = null;

        try {
            socket = new MulticastSocket(10562);
            group = InetAddress.getByName("239.255.255.255");
            socket.joinGroup(group);
            System.out.println("Create socket on port: " + 10562);
        }
        catch (Exception ex) {
            System.out.println("Error to create socket on port: ");

        }

        boolean running = true;
        System.out.println("Start receiving");
        DatagramPacket packet;
        byte[] buf = new byte[4096];
        while (running) {
            packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
            this.strData = new String(buf, StandardCharsets.UTF_8);

            try {
                MainActivity.ILSobject = new JSONObject(this.strData);
                synchronized (ILSobject) {
                    ILSobject = new JSONObject(this.strData);
                }
                //System.out.println("TopAwsMeas: " + ILSobject.getString("TopAwsMeas"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }



        }
        socket.close();
    }
}
