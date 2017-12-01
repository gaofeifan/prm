package com.pj.cache;

import java.util.HashMap;
import java.util.Map;

public class PartnerDetailsCache {

    private static Map<String , Object> cache = new HashMap<>();

    public static Map<String , Object> getChche(){
        return cache;
    }

    public static Object getValueByKey(String key){
        return cache.get(key);
    }

    public static void put(String key,Object value){
        cache.put(key,value);
    }
}
