package com.activelook.demo;

import android.graphics.Point;
import android.os.Handler;

import com.activelook.activelooksdk.Glasses;
import com.activelook.activelooksdk.types.Rotation;

import java.util.concurrent.TimeUnit;

public class Worker  extends Thread{
    private String[] ils;
    private final Handler h = new Handler();
    private Glasses connectedGlasses;
    public Worker(final Glasses g, String[] _ils){
        this.connectedGlasses = g;
        this.ils = _ils;
    }
    @Override
    public void run(){

        final long msStep = 500;
        long ms = 10;
        h.postDelayed(() -> connectedGlasses.clear(), ms+=msStep);
        while (true){
            //h.postDelayed(() -> connectedGlasses.clear(), ms+=msStep);
            h.postDelayed(() -> connectedGlasses.txt(new Point(200, 200), Rotation.TOP_LR, (byte) 0x00, (byte) 0x0F, "val: " + ils[3]), ms+=0);
            System.out.println("val: " + ils[3]);
            try {
                Thread.sleep( TimeUnit.SECONDS.toMillis( 3 ) );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        /*final Handler h = new Handler();
        //final Glasses g = this.connectedGlasses;
        final long msStep = 500;
        long ms = 10;

        h.postDelayed(() -> connectedGlasses.clear(), ms+=msStep);
        //h.postDelayed(() -> g.battery(r -> snack(String.format("Battery level: %d", r))), ms+=msStep);
        //h.postDelayed(() -> g.vers(r -> snack(String.format("Version: %s [serial=%d]", r.getVersion(), r.getSerial()))), ms+=msStep);
        //h.postDelayed(() -> g.clear(), ms+=msStep);
        h.postDelayed(() -> connectedGlasses.shift((short) 0, (short) 0), ms+=msStep);
        h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "1"), ms+=msStep);
        h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "22"), ms+=msStep);
        h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "333"), ms+=msStep);
        h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "4444"), ms+=msStep);
        h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "55555"), ms+=msStep);
        h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "666666"), ms+=msStep);
        h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "7777777"), ms+=msStep);
        h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "88888888"), ms+=msStep);
        h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "999999999"), ms+=msStep);
        h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "0000000000"), ms+=msStep);
        h.postDelayed(() -> connectedGlasses.clear(), ms+=msStep);
        h.postDelayed(() -> connectedGlasses.txt(new Point(200, 200), Rotation.TOP_LR, (byte) 0x00, (byte) 0x0F, "TEST DONE"), ms+=msStep);*/
    }

}
