package com.activelook.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.activelook.activelooksdk.DiscoveredGlasses;
import com.activelook.activelooksdk.Sdk;
import com.activelook.activelooksdk.types.GlassesUpdate;

import java.util.ArrayList;

public class ScanningActivity extends AppCompatActivity {

    private Sdk alsdk;
    private ArrayAdapter<DiscoveredGlasses> scanResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity);

        /*
         * Initialize scan result list
         */
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.scanResults = new ArrayAdapter<DiscoveredGlasses>(this, R.layout.listitem_device) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View glassesView = convertView;
                if (convertView == null) {
                    final DiscoveredGlasses glasses = this.getItem(position);
                    glassesView = inflater.inflate(R.layout.listitem_device, null);
                    TextView name = glassesView.findViewById(R.id.device_name);
                    TextView address = glassesView.findViewById(R.id.device_address);
                    name.setText(glasses.getName());
                    String addressText = String.format(parent.getResources().getString(R.string.listitem_address),
                            glasses.getManufacturer(),
                            glasses.getAddress());
                    address.setText(addressText);
                }
                return glassesView;
            }
        };
        if (savedInstanceState != null) {
            savedInstanceState.setClassLoader(getClass().getClassLoader());
            ArrayList<DiscoveredGlasses> dGlasses = savedInstanceState.getParcelableArrayList("discoveredGlasses");
            if (dGlasses != null) {
                this.scanResults.addAll(dGlasses);
            }
        }
        final ListView list = this.findViewById(R.id.scan_results);
        list.setAdapter(this.scanResults);
        list.setOnItemClickListener((adapterView, view, i, l) -> {
            DiscoveredGlasses device = ScanningActivity.this.scanResults.getItem(i);
            ScanningActivity.this.scanResults.clear();
            ScanningActivity.this.alsdk.stopScan();
            Toast.makeText(this, "Connecting to...", Toast.LENGTH_SHORT).show();
            device.connect(glasses -> {
                if (glasses.isFirmwareAtLeast("4.0")) {
                    if (glasses.compareFirmwareVersion("4.0") > 0) {
                        /*runOnUiThread(() ->
                            Toast.makeText(ScanningActivity.this, "Your glasses have a more recent " +
                                    "firmware. Check the store for an update of this application",
                                    Toast.LENGTH_LONG).show());
                         */
                    }
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("connectedGlasses", glasses);
                    ScanningActivity.this.setResult(Activity.RESULT_FIRST_USER, returnIntent);
                    ((DemoApp) this.getApplication()).onConnected();
                    ScanningActivity.this.finish();
                } else {
                    runOnUiThread(() ->
                        Toast.makeText(ScanningActivity.this, "Update your glasses at least with firmware 4.0" +
                            " to use this application", Toast.LENGTH_LONG).show());
                }
            },
            discoveredGlasses -> runOnUiThread(() -> {
                Toast.makeText(ScanningActivity.this, "Connection failure, waiting for reconnection", Toast.LENGTH_LONG).show();
            }),
            glasses -> {
                ((DemoApp) this.getApplication()).onDisconnected();
            });
        });
        /*
         * Scan / Stop scan button
         */
        Button fab = this.findViewById(R.id.button_stop_scan);
        fab.setOnClickListener(view -> {
            ScanningActivity.this.scanResults.clear();
            ScanningActivity.this.alsdk.stopScan();
            ScanningActivity.this.setResult(Activity.RESULT_CANCELED);
            ScanningActivity.this.finish();
        });
        this.alsdk = ((DemoApp) this.getApplication()).getActiveLookSdk(
                this::onUpdateStart,
                this::onUpdateAvailableCallback,
                this::onUpdateProgress,
                this::onUpdateSuccess,
                this::onUpdateError
        );
        this.alsdk.startScan(activeLookDiscoveredGlassesI -> runOnUiThread(() -> {
            ScanningActivity.this.scanResults.add(activeLookDiscoveredGlassesI);
        }));
    }

    private void onUpdateStart(final GlassesUpdate glassesUpdate) {
        this.logText(glassesUpdate);
    }
    private void onUpdateAvailableCallback(final android.util.Pair<GlassesUpdate, Runnable> glassesUpdateAndRunnable) {
        this.logText(glassesUpdateAndRunnable.first);
        Log.d("GLASSES_UPDATE", String.format("onUpdateAvailableCallback   : %s", glassesUpdateAndRunnable.first));
        glassesUpdateAndRunnable.second.run();
    }
    private void onUpdateProgress(final GlassesUpdate glassesUpdate) {
        this.logText(glassesUpdate);
    }
    private void onUpdateSuccess(final GlassesUpdate glassesUpdate) {
        this.logText(glassesUpdate);
    }
    private void onUpdateError(final GlassesUpdate glassesUpdate) {
        //Toast.makeText(this, "Error while trying to connect", Toast.LENGTH_SHORT).show();
        //this.logText(glassesUpdate);
        this.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        ArrayList<DiscoveredGlasses> underlying = new ArrayList<>();
        for (int i = 0; i < this.scanResults.getCount(); i++) {
            underlying.add(this.scanResults.getItem(i));
        }
        savedInstanceState.putParcelableArrayList("discoveredGlasses", underlying);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void logText(Object data) {
        this.runOnUiThread(() -> {
            final TextView textView = this.findViewById(R.id.textView);
            final String msg = data == null ? "" : data.toString();
            textView.setText(msg);
            if (data != null) {
                Log.d("ScanningActivity", data.toString());
            } else {
                textView.setText("");
            }
        });
    }
}
