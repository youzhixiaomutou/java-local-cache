package me.linxiaowei.cache.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class GuavaTest {

    public static void main(String[] args) {
        Cache<Object, Object> cache = CacheBuilder.newBuilder().build();
    }

}
