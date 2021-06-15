package com.ray.rayrouter.sample;

import java.util.HashMap;
import java.util.Map;

public class RouterMapping_1 {

    public static Map<String, String> get() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("router://reading","com.ray.biz_reading.ReadingActivity");
        return mapping;
    }

}