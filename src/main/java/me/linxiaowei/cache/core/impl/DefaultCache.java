package me.linxiaowei.cache.core.impl;

import me.linxiaowei.cache.core.CacheBuilder;
import me.linxiaowei.cache.core.CacheName;

/**
 * 默认缓存类的实现，其余实现可以参考这个类
 */
@CacheName("default.cache")
public class DefaultCache extends CacheBuilder {

    @Override
    protected long size() {
        return 100L;
    }

    @Override
    protected long weightSize() {
        return 0L;
    }

    @Override
    protected int weigh(Object key,Object value) {
        return 0;
    }

    @Override
    protected long expireAfterAccess() {
        return 0L;
    }

    @Override
    protected long expireAfterWrite() {
        return 0L;
    }

    @Override
    protected boolean enableWeakReference() {
        return false;
    }

    @Override
    protected boolean enableSoftReference() {
        return false;
    }

    @Override
    protected long refreshAfterWrite() {
        return 1000L * 10;
    }

    @Override
    protected boolean enableStats() {
        return true;
    }

    @Override
    protected Object load(Object key) {
        return key + ": I am value.";
    }

    @Override
    protected void onRemoval(Object key, Object value, String cause) {
        System.out.println("key=" + key + ", value=" + value + ",cause=" + cause);
    }

}
