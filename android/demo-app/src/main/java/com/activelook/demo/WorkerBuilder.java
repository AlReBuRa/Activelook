package com.activelook.demo;

import com.activelook.activelooksdk.Glasses;
import org.json.JSONObject;

public class WorkerBuilder {
    public Worker createWorker(final Glasses g, JSONObject _ils, String _IlsData) {
        return new Worker(g, _ils, _IlsData);
    }
}
