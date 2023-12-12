package com.activelook.demo;

import android.os.Handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.InputMismatchException;
import org.json.JSONArray;
import java.lang.Object;
import java.nio.charset.StandardCharsets;

import org.json.JSONException;
import org.json.JSONObject;
public class EchoServer extends Thread {
    private boolean running;
    public volatile String IlsData;
    private Handler hd;
    private MulticastSocket socket = null;
    private InetAddress group = null;
    public EchoServer(String _ils) {
        //hd = msgHandler;
        this.IlsData = _ils;
        try {
            //socket = new DatagramSocket(10563);
            socket = new MulticastSocket(10562);
            group = InetAddress.getByName("239.255.255.255");
            socket.joinGroup(group);
            System.out.println("Create socket on port: " + 10562);
        }
        catch (Exception ex) {
            System.out.println("Error to create socket on port: ");

        }
    }


    @Override
    public void run() {
        running = true;
        System.out.println("Start receiving");
        DatagramPacket packet;
        JSONArray array = null;
        byte[] buf = new byte[4096];
        while (running) {
            packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
            IlsData = new String(buf, StandardCharsets.UTF_8);

            try {
                JSONObject ils = new JSONObject(IlsData);
                System.out.println("*TopAwsMeas: " + ils.getString("TopAwsMeas"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }



        }
        socket.close();
    }

}



