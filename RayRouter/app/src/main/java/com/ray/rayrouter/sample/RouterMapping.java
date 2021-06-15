package com.ray.rayrouter.sample;

import java.util.HashMap;
import java.util.Map;

public class RouterMapping {

    public static Map<String, String> get() {
        Map<String, String> mapping = new HashMap<>();
        mapping.putAll(RouterMapping_1.get());
        mapping.putAll(RouterMapping_2.get());
        return mapping;
    }

}