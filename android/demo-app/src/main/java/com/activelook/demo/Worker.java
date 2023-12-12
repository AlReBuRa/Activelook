package com.activelook.demo;

import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import com.activelook.activelooksdk.Glasses;
import com.activelook.activelooksdk.types.Rotation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import kotlin.random.Random;

public class Worker  extends Thread{
    private JSONObject ils;
    private volatile String IlsData;
    private final Handler h = new Handler();
    private Glasses connectedGlasses;
    public Worker(final Glasses g, JSONObject _ils, String _IlsData){
        this.connectedGlasses = g;
        this.IlsData = _IlsData;
    }
    @Override
    public void run(){
        final long msStep = 200;
        long ms = 10;

        if(this.IlsData != null || this.IlsData != "") {
            try {
                JSONObject ils = new JSONObject(this.IlsData);
                System.out.println("*TopAwsMeas: " + ils.getString("TopAwsMeas"));
            } catch (JSONException e) {
                System.out.println(e.toString());
            }
        }

        /*String data = "[{\"userName\": \"sandeep\",\"age\":30},{\"userName\": \"vivan\",\"age\":5}]  ";
        JSONArray jsonArr = null;
        try {
            jsonArr = new JSONArray(data);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < jsonArr.length(); i++)
        {
            JSONObject jsonObj = null;
            try {
                jsonObj = jsonArr.getJSONObject(i);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            System.out.println(jsonObj);
        }
         */

        /*try {
            String vari = this.ils.getString("TopAwsMeas");
            System.out.println("*TopAwsMeas: " + vari);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }*/
        float Var1_random = 12.0F;
        float Var2_random = 36.0F;
        float Var3_random = -145.0F;
        float Var4_random = 52.0F;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(1);
        h.postDelayed(() -> connectedGlasses.clear(), ms+=msStep);
        while (true) {

            Var1_random += 0.03;
            if(Var1_random > 16.5F) {
                Var1_random = 12.0F;
            }
            Var2_random += 0.03;
            if(Var2_random > 43.5F) {
                Var2_random = 36.0F;
            }

            Var3_random += 0.03;
            if(Var3_random > -148.3F) {
                Var3_random = -141.0F;
            }

            Var4_random += 0.02;
            if(Var4_random > 29.5F) {
                Var4_random = 26.0F;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                String finalFloat_random = df.format(Var1_random);
                connectedGlasses.txt(new Point(300, 100), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, "TWS2");
                connectedGlasses.txt(new Point(300, 70), Rotation.TOP_LR, (byte) 3, (byte) 0x0F, finalFloat_random);
                finalFloat_random = df.format(Var2_random);
                connectedGlasses.txt(new Point(150, 100), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, "BSP");
                connectedGlasses.txt(new Point(150, 70), Rotation.TOP_LR, (byte) 3, (byte) 0x0F, finalFloat_random);
                finalFloat_random = df.format(Var3_random);
                connectedGlasses.txt(new Point(150, 205), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, "TWA");
                connectedGlasses.txt(new Point(160, 180), Rotation.TOP_LR, (byte) 3, (byte) 0x0F, finalFloat_random);
                finalFloat_random = df.format(Var4_random);
                connectedGlasses.txt(new Point(300, 205), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, "AWA");
                connectedGlasses.txt(new Point(300, 180), Rotation.TOP_LR, (byte) 3, (byte) 0x0F, finalFloat_random);

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


            /*try {
                Var1 = ils.getString("TopAwsMeas");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //h.postDelayed(() -> connectedGlasses.clear(), ms+=msStep);
            String finalVar = Var1;
            h.postDelayed(() -> connectedGlasses.txt(new Point(200, 200), Rotation.TOP_LR, (byte) 0x00, (byte) 0x0F, "val: " + finalVar), ms+=0);
            System.out.println("val: " + Var1);
            try {
                Thread.sleep( TimeUnit.SECONDS.toMillis( 3 ) );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }*/

            //Var1_random = rand.nextFloat();
            /*DateTimeFormatter dtf;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            } else {
                dtf = null;
            }
            LocalDateTime now;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                now = LocalDateTime.now();
            } else {
                now = null;
            }
*/



            /*
            h.postDelayed(() -> connectedGlasses.clear(), ms += msStep);
            //h.postDelayed(() -> g.battery(r -> snack(String.format("Battery level: %d", r))), ms+=msStep);
            //h.postDelayed(() -> g.vers(r -> snack(String.format("Version: %s [serial=%d]", r.getVersion(), r.getSerial()))), ms+=msStep);
            //h.postDelayed(() -> g.clear(), ms+=msStep);
            h.postDelayed(() -> connectedGlasses.shift((short) 0, (short) 0), ms += msStep);
            h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "1"), ms += msStep);
            h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "22"), ms += msStep);
            h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "333"), ms += msStep);
            h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "4444"), ms += msStep);
            h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "55555"), ms += msStep);
            h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "666666"), ms += msStep);
            h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "7777777"), ms += msStep);
            h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "88888888"), ms += msStep);
            h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "999999999"), ms += msStep);
            h.postDelayed(() -> connectedGlasses.txt(new Point(10, 100), Rotation.TOP_RL, (byte) 0x00, (byte) 0x0F, "0000000000"), ms += msStep);
            h.postDelayed(() -> connectedGlasses.clear(), ms += msStep);
            h.postDelayed(() -> connectedGlasses.txt(new Point(200, 200), Rotation.TOP_LR, (byte) 0x00, (byte) 0x0F, "TEST DONE"), ms += msStep);
            */
        }
    }

}
