package me.linxiaowei.cache.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.Cache;
import com.sun.org.apache.bcel.internal.generic.FDIV;

import java.util.concurrent.TimeUnit;

public class CaffieineTest {

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        // 手动加载
        Cache<String, Object> cache = (Cache<String, Object>) Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS)
                .maximumSize(3).build();
        String key = "hello";
        String key2 = "func";


    }

}
