package me.linxiaowei.cache.core;

import me.linxiaowei.cache.util.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局的缓存控制器
 */
public class CacheManager {

    /**
     * 全局缓存 map
     */
    private static final Map<String, Cache> CACHE_MAP = new ConcurrentHashMap<>(16);
    /**
     * 缓存实现存放的包名
     */
    private static final String CACHE_IMPL_PACKAGE_NAME = "me.linxiaowei.cache.core.impl";

    /**
     * @param name 缓存名
     * @return 获取对应的缓存
     */
    public static Cache getCache(String name) {
        Cache cache = null;
        if (CACHE_MAP.get(name) != null) {
            // 如果已经构建过缓存则返回
            cache = CACHE_MAP.get(name);
        } else {
            // 否则构建一次后存入全局缓存 map
            boolean findCacheImpl = false;
            try {
                Set<Class<?>> classSet = ReflectionUtil.getAnnotationClasses(CACHE_IMPL_PACKAGE_NAME, CacheName.class);
                for (Class clazz : classSet) {
                    for (Annotation annotation : clazz.getAnnotations()) {
                        if (annotation instanceof CacheName) {
                            String value = ((CacheName) annotation).value();
                            if (value.equals(name)) {
                                findCacheImpl = true;
                                cache = ((CacheBuilder) clazz.newInstance()).build(name);
                                CACHE_MAP.put(name, cache);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // log findCacheImpl
        }
        return cache;
    }

    /**
     * @return 获取所有缓存的 names
     */
    public static Set<String> names() {
        return CACHE_MAP.keySet();
    }

}
