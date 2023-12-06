package com.activelook.demo;

import com.activelook.activelooksdk.Glasses;

public class WorkerBuilder {
    public Worker createWorker(final Glasses g, String[] _ils) {
        return new Worker(g, _ils);
    }
}
