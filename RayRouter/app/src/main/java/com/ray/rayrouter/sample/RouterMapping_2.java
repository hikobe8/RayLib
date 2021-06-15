package com.ray.rayrouter.sample;

import java.util.HashMap;
import java.util.Map;

public class RouterMapping_2 {

    public static Map<String, String> get() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("router://home","com.ray.rayrouter.MainActivity");
        mapping.put("router://test","com.ray.rayrouter.TestActivity");
        return mapping;
    }

}