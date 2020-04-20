package me.linxiaowei.cache.core;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * 模板方法模式
 */
public abstract class CacheBuilder {

    public Cache build(String name) {
        Cache cache = null;
        try {
            LoadingCache<String, String> lc = com.google.common.cache.CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(10, TimeUnit.MINUTES).recordStats()
                    .build(new CacheLoader<String, String>() {
                        @Override
                        public String load(String key) throws Exception {
                            return key + ": this is value";
                        }
                    });
            cache = new Cache(lc, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cache;
    }

    /**
     * dfdfdf
     *
     * @return true
     */
    protected abstract boolean enAbleStats();


}
