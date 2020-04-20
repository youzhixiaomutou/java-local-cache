package me.linxiaowei.cache.core;

import com.github.benmanes.caffeine.cache.stats.CacheStats;

/**
 * 通过装饰器模式
 */
public class Cache {

    private com.google.common.cache.Cache cache;
    private CacheStats cacheStats;

    public Cache(com.google.common.cache.Cache cache, CacheStats cacheStats) {
        this.cache = cache;
        this.cacheStats = cacheStats;
    }

    public com.google.common.cache.Cache getCache() {
        return cache;
    }

    public void setCache(com.google.common.cache.Cache cache) {
        this.cache = cache;
    }

    public CacheStats getCacheStats() {
        return cacheStats;
    }

    public void setCacheStats(CacheStats cacheStats) {
        this.cacheStats = cacheStats;
    }

}
