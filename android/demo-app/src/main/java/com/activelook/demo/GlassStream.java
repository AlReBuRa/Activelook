package com.activelook.demo;

import android.graphics.Point;
import android.os.Build;
import com.activelook.activelooksdk.types.Rotation;
import org.json.JSONException;

public class GlassStream implements Runnable{

    private boolean FirstTime = true;
    private boolean FirstTimeUnconnected = true;

    public GlassStream() {
    }

    @Override
    public void run() {
        System.out.printf("Start thread to display variables to connected glass (" + Thread.currentThread().getName() + ")");
        boolean running = true;

        while (running) {
            if(MainActivity.connectedGlasses != null) {

                if (MainActivity.IlsObject != null || MainActivity.IlsObject.length() != 0) {
                    try {


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                            if(MainActivity.IsMulticastConnected) {
                                if(FirstTime){
                                    FirstTime = false;
                                    MainActivity.connectedGlasses.clear();
                                    //MainActivity.connectedGlasses.battery(r -> snack(String.format("Battery level: %d", r)));
                                }
/*
                            MainActivity.connectedGlasses.txt(new Point(300, 205), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, MainActivity.UserlistOfVariables.get(0));
                            MainActivity.connectedGlasses.txt(new Point(300, 180), Rotation.TOP_LR, (byte) 3, (byte) 0x0F, MainActivity.IlsObject.getString(MainActivity.UserlistOfVariables.get(0)));
                            MainActivity.connectedGlasses.txt(new Point(150, 205), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, MainActivity.UserlistOfVariables.get(1));
                            MainActivity.connectedGlasses.txt(new Point(160, 180), Rotation.TOP_LR, (byte) 3, (byte) 0x0F, MainActivity.IlsObject.getString(MainActivity.UserlistOfVariables.get(1)));
                            MainActivity.connectedGlasses.txt(new Point(300, 100), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, MainActivity.UserlistOfVariables.get(2));
                            MainActivity.connectedGlasses.txt(new Point(300, 70), Rotation.TOP_LR, (byte) 3, (byte) 0x0F, MainActivity.IlsObject.getString(MainActivity.UserlistOfVariables.get(2)));
                            MainActivity.connectedGlasses.txt(new Point(150, 100), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, MainActivity.UserlistOfVariables.get(3));
                            MainActivity.connectedGlasses.txt(new Point(150, 70), Rotation.TOP_LR, (byte) 3, (byte) 0x0F, MainActivity.IlsObject.getString(MainActivity.UserlistOfVariables.get(3)));
*/

                                MainActivity.connectedGlasses.txt(new Point(300, 205), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, "BSPtg");
                                MainActivity.connectedGlasses.txt(new Point(300, 180), Rotation.TOP_LR, (byte) 3, (byte) 0x0F, MainActivity.IlsObject.getString("BspTarget"));
                                MainActivity.connectedGlasses.txt(new Point(150, 205), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, "CWA");
                                MainActivity.connectedGlasses.txt(new Point(160, 180), Rotation.TOP_LR, (byte) 3, (byte) 0x0F, MainActivity.IlsObject.getString("CWA"));
                                MainActivity.connectedGlasses.txt(new Point(300, 100), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, "Heel");
                                MainActivity.connectedGlasses.txt(new Point(300, 70),  Rotation.TOP_LR, (byte) 3, (byte) 0x0F, MainActivity.IlsObject.getString("Roll"));
                                MainActivity.connectedGlasses.txt(new Point(150, 100), Rotation.TOP_LR, (byte) 1, (byte) 0x0F, "BSP");
                                MainActivity.connectedGlasses.txt(new Point(150, 70),  Rotation.TOP_LR, (byte) 3, (byte) 0x0F, MainActivity.IlsObject.getString("Sog_hydrins"));

                                MainActivity.connectedGlasses.battery(r -> MainActivity.connectedGlasses.txt(new Point(220, 240), Rotation.TOP_LR, (byte) 0, (byte) 0x0F, (r + "%")));
                                FirstTimeUnconnected = true;
                            }
                            else{
                                if(FirstTimeUnconnected) {
                                    MainActivity.connectedGlasses.clear();
                                    FirstTimeUnconnected = false;
                                }
                                //MainActivity.connectedGlasses.cfgSet("please_connect");
                                MainActivity.connectedGlasses.battery(r -> MainActivity.connectedGlasses.txt(new Point(220, 240), Rotation.TOP_LR, (byte) 0, (byte) 0x0F, (r + "%")));
                                MainActivity.connectedGlasses.txt(new Point(300, 150), Rotation.TOP_LR, (byte) 2, (byte) 0x0F, "Phone is not");
                                MainActivity.connectedGlasses.txt(new Point(300, 100), Rotation.TOP_LR, (byte) 2, (byte) 0x0F, "connected to");
                                MainActivity.connectedGlasses.txt(new Point(300,  50), Rotation.TOP_LR, (byte) 2, (byte) 0x0F, "the boat");
                                FirstTime = true;
                            }


                            Thread.sleep(200);

                        }
                    } catch (JSONException e) {
                        System.out.println(e);
                    }
                    catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            }
        }

    }
}
