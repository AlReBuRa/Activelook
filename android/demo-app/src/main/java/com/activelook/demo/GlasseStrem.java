package com.activelook.demo;

import android.graphics.Point;
import android.os.Build;

import com.activelook.activelooksdk.types.Rotation;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class GlasseStrem  implements Runnable{

    private boolean firsttime = true;
    private Object object;
    private JSONObject IlsData;

    private DecimalFormat df;
    public GlasseStrem(String strData) {
        IlsData = MainActivity.ILSobject;
        df = new DecimalFormat();
        df.setMaximumFractionDigits(1);
    }
    public GlasseStrem(Object object, String strData) {
        this.object = object;
    }
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();

        System.out.printf("[%s] %s\n", threadName, this.object);

        boolean running = true;

        while (running) {
            if(MainActivity.connectedGlasses != null) {
                if(firsttime){
                    firsttime = false;
                    MainActivity.connectedGlasses.clear();
                    //MainActivity.connectedGlasses.battery(r -> snack(String.format("Battery level: %d", r)));
                }
                if (MainActivity.ILSobject != null || MainActivity.ILSobject.length() != 0) {
                    try {

                        Thread.sleep(200);
                        //System.out.println("*TopAwsMeas: " + MainActivity.ILSobject.getString("TopAwsMeas"));

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                            MainActivity.connectedGlasses.txt(new Point(300, 100), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, "TWS");
                            MainActivity.connectedGlasses.txt(new Point(300, 70), Rotation.TOP_LR, (byte) 3, (byte) 0x0F, MainActivity.ILSobject.getString("FwdAwsMeas"));
                            MainActivity.connectedGlasses.txt(new Point(150, 100), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, "BSP");
                            MainActivity.connectedGlasses.txt(new Point(150, 70), Rotation.TOP_LR, (byte) 3, (byte) 0x0F, MainActivity.ILSobject.getString("Sog_hydrins"));
                            MainActivity.connectedGlasses.txt(new Point(150, 205), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, "TWA");
                            MainActivity.connectedGlasses.txt(new Point(160, 180), Rotation.TOP_LR, (byte) 3, (byte) 0x0F, MainActivity.ILSobject.getString("FwdAwaMeas"));
                            MainActivity.connectedGlasses.txt(new Point(300, 205), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, "AWA");
                            MainActivity.connectedGlasses.txt(new Point(300, 180), Rotation.TOP_LR, (byte) 3, (byte) 0x0F, MainActivity.ILSobject.getString("TopAwaMeas"));

                            MainActivity.connectedGlasses.battery(r ->MainActivity.connectedGlasses.txt(new Point(220, 240), Rotation.TOP_LR, (byte) 0, (byte) 0x0F, (r + "%")));

                        }
                    } catch (JSONException e) {
                        System.out.println(e.toString());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

    }
}
