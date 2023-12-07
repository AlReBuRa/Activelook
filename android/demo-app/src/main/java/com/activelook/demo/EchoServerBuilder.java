package com.activelook.demo;

import org.json.JSONObject;

public class EchoServerBuilder {
    public EchoServer createEchoServer(JSONObject _ils) {
        return new EchoServer(_ils);
    }
}