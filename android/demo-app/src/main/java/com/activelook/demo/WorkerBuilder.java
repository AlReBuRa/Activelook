package com.activelook.demo;

import com.activelook.activelooksdk.Glasses;
import org.json.JSONObject;

public class WorkerBuilder {
    public Worker createWorker(final Glasses g, JSONObject _ils) {
        return new Worker(g, _ils);
    }
}
