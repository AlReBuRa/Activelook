package com.activelook.demo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.util.ArrayList;

public class ConnectActivity  extends AppCompatActivity {
    public ConnectActivity() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_scrolling);

        final TextView Variable1TextView = (TextView) findViewById(R.id.textView7);

        Thread thread = new Thread(new Runnable() {

            public void run() {


                while (true) {
                    try {
                        String Variable = MainActivity.IlsObject.getString(MainActivity.UserlistOfVariables.get(0));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Variable1TextView.setText(Variable);
                            }
                        });
                        Thread.sleep(200);

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }
}