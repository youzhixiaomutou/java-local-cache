package me.linxiaowei.cache.core;

import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;

import java.util.function.Function;

/**
 * 通过装饰器模式来对 Caffeine 的 Cache 相关对象做一层封装
 */
public class Cache<T> {

    private LoadingCache loadingCache;

    public Cache(LoadingCache loadingCache) {
        this.loadingCache = loadingCache;
    }

    public LoadingCache getCache() {
        return loadingCache;
    }

    /**
     * @param key 缓存 key
     * @return 根据 key 获取 缓存值
     */
    public T get(String key) {
        return (T) loadingCache.get(key);
    }

    /**
     * @param key      缓存 key
     * @param function 缓存值获取方法
     * @return 根据 key 和 缓存值获取方法 获取 缓存值
     */
    public T get(String key, Function function) {
        return (T) loadingCache.get(key, function);
    }

    /**
     * @return 获取统计信息
     */
    public CacheStats getStats() {
        return loadingCache.stats();
    }

    /**
     * @param key   缓存 key
     * @param value 缓存 value
     */
    public void put(String key, T value) {
        loadingCache.put(key, value);
    }

}
