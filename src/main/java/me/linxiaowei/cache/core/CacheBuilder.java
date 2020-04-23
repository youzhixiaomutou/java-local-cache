package me.linxiaowei.cache.core;

import com.github.benmanes.caffeine.cache.*;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * 通过模板方法模式来配置缓存的特性，所有缓存的实现默认要继承该类，可参考下面的 DefaultCache 是怎么使用的
 *
 * @see me.linxiaowei.cache.core.impl.DefaultCache
 */
public abstract class CacheBuilder {

    public Cache build(String name) {
        Cache cache = null;
        Caffeine caffeine = Caffeine.newBuilder();
        // 设置缓存容量大小
        if (size() > 0) {
            caffeine.maximumSize(size());
        }
        // 设置缓存权重大小
        if (weightSize() > 0) {
            caffeine.maximumWeight(weightSize());
            // 设置缓存权重计算方法
            caffeine.weigher(new Weigher() {
                @Override
                public @NonNegative int weigh(@NonNull Object key, @NonNull Object value) {
                    return CacheBuilder.this.weigh(key, value);
                }
            });
        }
        // 自定义失效策略暂时不使用，以后有使用场景再补
        // 设置最后一次被访问（读或者写）后的失效时间，这里单位写死为 ms 毫秒了
        if (expireAfterAccess() > 0) {
            caffeine.expireAfterAccess(expireAfterAccess(), TimeUnit.MILLISECONDS);
        }
        // 最后一次被创建或修改后的失效时间，这里单位写死为 ms 毫秒了
        if (expireAfterWrite() > 0) {
            caffeine.expireAfterWrite(expireAfterWrite(), TimeUnit.MILLISECONDS);
        }
        // 是否开启软引用功能（开启后，每次垃圾回收时，如果缓存值没有被强引用，则缓存会失效）
        if (enableWeakReference()) {
            caffeine.weakKeys().weakValues();
        }
        // 是否开启弱引用功能（开启后，每次垃圾回收时缓存都会失效）
        if (enableSoftReference()) {
            caffeine.softValues();
        }
        // 设置写入后的缓存刷新时间，这里时间单位写死为 ms 毫秒了
        if (refreshAfterWrite() > 0) {
            caffeine.refreshAfterWrite(refreshAfterWrite(), TimeUnit.MILLISECONDS);
        }
        // 是否开启缓存统计功能
        if (enableStats()) {
            caffeine.recordStats();
        }
        // 缓存删除时的回调方法
        caffeine.removalListener((Object key, @Nullable Object value, @NonNull RemovalCause cause) ->
                onRemoval(key, value, cause.toString()));
        // Caffeine 支持手动加载、同步自动加载、异步加载，这里直接制定同步自动加载（这是更加保证一致性官方推荐的做法）
        LoadingCache<Object, Object> loadingCache = caffeine.build(new CacheLoader() {
            @Nullable
            @Override
            public Object load(@NonNull Object key) throws Exception {
                return CacheBuilder.this.load(key);
            }
        });
        cache = new Cache(loadingCache);
        return cache;
    }


    /**
     * 基于容量大小的缓存，会默认使用 Caffeine 实现的 W-TinyLFU 算法进行缓存淘汰（目前最快的算法，不用去改了）
     *
     * @return 缓存容量大小（基于容量或权重只能选择 1 种，否则抛异常）
     */
    protected abstract long size();

    /**
     * @return 缓存权重大小（基于容量或权重只能选择 1 种，否则抛异常）
     */
    protected abstract long weightSize();

    /**
     * @param key   缓存 key
     * @param value 缓存 value
     * @return 缓存权重计算方法，如果使用基于权重，则必须预先实现该方法
     */
    protected abstract int weigh(Object key, Object value);

    /**
     * @return 最后一次被访问（读或者写）后的失效时间，这里单位写死为 ms 毫秒了
     */
    protected abstract long expireAfterAccess();

    /**
     * @return 最后一次被创建或修改后的失效时间，这里单位写死为 ms 毫秒了
     */
    protected abstract long expireAfterWrite();

    /**
     * 是否开启弱引用功能（开启后，每次垃圾回收时缓存都会失效）
     *
     * @return true 是；false 否
     */
    protected abstract boolean enableWeakReference();

    /**
     * 是否开启软引用功能（开启后，每次垃圾回收时，如果缓存值没有被强引用，则缓存会失效）
     *
     * @return true 是；false 否
     */
    protected abstract boolean enableSoftReference();

    /**
     * @return 写入后的缓存刷新时间，这里时间单位写死为 ms 毫秒了
     */
    protected abstract long refreshAfterWrite();

    /**
     * 是否开启缓存统计功能
     *
     * @return true 是；false 否
     */
    protected abstract boolean enableStats();

    /**
     * 缓存自动同步加载的方法
     *
     * @param key 缓存 key
     */
    protected abstract Object load(Object key);

    /**
     * 缓存删除时的回调方法
     *
     * @param key   缓存 key
     * @param value 缓存 value
     * @param cause 缓存删除的原因
     */
    protected abstract void onRemoval(Object key, Object value, String cause);

}
