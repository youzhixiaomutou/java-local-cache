package me.linxiaowei.cache;

import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ExecutionException {
       Cache cache =  CacheManager.getCache("default.cache");
        System.out.println(((LoadingCache)cache.getCache()).get("1"));
    }

}
