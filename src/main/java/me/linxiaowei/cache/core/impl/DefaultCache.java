package me.linxiaowei.cache.core.impl;

import me.linxiaowei.cache.core.CacheBuilder;
import me.linxiaowei.cache.core.annotation.CacheName;

@CacheName("default.cache")
public class DefaultCache extends CacheBuilder {

    @Override
    protected boolean enAbleStats() {
        return false;
    }

}
