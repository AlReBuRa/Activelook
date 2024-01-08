package com.activelook.demo;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IlsActivity  extends AppCompatActivity {

    private JSONObject ils;
    private ArrayAdapter<String> dataAdapter;
    private List<String> listdata_org;
    private Spinner Variable1Spinner;
    private Spinner Variable2Spinner;
    private Spinner Variable3Spinner;
    private Spinner Variable4Spinner;

    public IlsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ils_data);

        configureBackButton();
        final TextView Variable1TextView = (TextView) findViewById(R.id.textView1);
        final TextView Variable2TextView = (TextView) findViewById(R.id.textView2);
        final TextView Variable3TextView = (TextView) findViewById(R.id.textView3);
        final TextView Variable4TextView = (TextView) findViewById(R.id.textView4);
        Variable1Spinner = (Spinner) findViewById(R.id.spinner1);
        Variable2Spinner = (Spinner) findViewById(R.id.spinner2);
        Variable3Spinner = (Spinner) findViewById(R.id.spinner3);
        Variable4Spinner = (Spinner) findViewById(R.id.spinner4);
        final Handler handler = new Handler();

        Variable1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.UserlistOfVariables.set(0, parent.getItemAtPosition(position).toString());
                System.out.println("OnItemSelectedListener : " + parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {    }
        });

        Variable2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.UserlistOfVariables.set(1, parent.getItemAtPosition(position).toString());
                System.out.println("OnItemSelectedListener : " + parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {    }
        });

        Variable3Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.UserlistOfVariables.set(2, parent.getItemAtPosition(position).toString());
                System.out.println("OnItemSelectedListener : " + parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {    }
        });

        Variable4Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.UserlistOfVariables.set(3, parent.getItemAtPosition(position).toString());
                System.out.println("OnItemSelectedListener : " + parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {    }
        });

        Thread thread = new Thread(new Runnable() {

            public void run() {

                listdata_org = new ArrayList<>();

                while (true) {
                    try {
                        //System.out.println("**TopAwsMeas: " + MainActivity.IlsObject.getString("TopAwsMeas"));
                        //System.out.println("TEST !!");
                        if( MainActivity.IlsObject.length() != 0) {


                            if (!MainActivity.IlsObject.isNull(MainActivity.UserlistOfVariables.get(0))) {

                                String Variable = MainActivity.IlsObject.getString(MainActivity.UserlistOfVariables.get(0));

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Variable1TextView.setText(Variable);
                                    }
                                });
                            }


                            if (!MainActivity.IlsObject.isNull(MainActivity.UserlistOfVariables.get(1))) {

                                String Variable = MainActivity.IlsObject.getString(MainActivity.UserlistOfVariables.get(1));

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Variable2TextView.setText(Variable);
                                    }
                                });
                            }

                            if (!MainActivity.IlsObject.isNull(MainActivity.UserlistOfVariables.get(2))) {

                                String Variable = MainActivity.IlsObject.getString(MainActivity.UserlistOfVariables.get(2));

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Variable3TextView.setText(Variable);
                                    }
                                });
                            }if (!MainActivity.IlsObject.isNull(MainActivity.UserlistOfVariables.get(3))) {

                                String Variable = MainActivity.IlsObject.getString(MainActivity.UserlistOfVariables.get(3));

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Variable4TextView.setText(Variable);
                                    }
                                });
                            }

                        }
                        Thread.sleep(200);

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                } // end of run


            }

        });
        thread.start();


        Thread KeyThread = new Thread(new Runnable() {

            public void run() {
                while (true) {
                    int index = 0;
                    Iterator<String> keys = MainActivity.IlsObject.keys();
                    ArrayList<String> listdata = new ArrayList<>();

                    while(keys.hasNext()) {
                        String key = keys.next();
                        listdata.add(key);
                    }

                    if(!listdata_org.equals(listdata)){



                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(IlsActivity.this, android.R.layout.simple_spinner_item, listdata);
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Variable1Spinner.setAdapter(spinnerArrayAdapter);
                                Variable2Spinner.setAdapter(spinnerArrayAdapter);
                                Variable3Spinner.setAdapter(spinnerArrayAdapter);
                                Variable4Spinner.setAdapter(spinnerArrayAdapter);
                            }
                        });

                        if(listdata_org.size() == 0){
                            //String name = ;
                            //index = listdata.indexOf(MainActivity.UserlistOfVariables.get(0));
                            int finalIndex1 = listdata.indexOf(MainActivity.UserlistOfVariables.get(0));
                            int finalIndex2 = listdata.indexOf(MainActivity.UserlistOfVariables.get(1));
                            int finalIndex3 = listdata.indexOf(MainActivity.UserlistOfVariables.get(2));
                            int finalIndex4 = listdata.indexOf(MainActivity.UserlistOfVariables.get(3));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Variable1Spinner.setSelection(finalIndex1);
                                    Variable2Spinner.setSelection(finalIndex2);
                                    Variable3Spinner.setSelection(finalIndex3);
                                    Variable4Spinner.setSelection(finalIndex4);
                                }
                            });
                        }

                        listdata_org = listdata;

                    }



                    //String varname = Variable1Spinner.getSelectedItem().toString();
                    //System.out.println("Var name selected: " + varname);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        KeyThread.start();
    }


    private void configureBackButton(){
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}