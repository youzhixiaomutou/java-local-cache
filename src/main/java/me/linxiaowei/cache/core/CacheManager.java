package me.linxiaowei.cache.core;

import me.linxiaowei.cache.core.annotation.CacheName;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局的缓存控制器
 */
public class CacheManager {

    private static final Map<String, Cache> CACHE_MAP = new ConcurrentHashMap<>(16);

    /**
     * @param name 缓存名
     * @return 获取对应的缓存
     */
    public static Cache getCache(String name) throws IllegalAccessException, InstantiationException {
        Cache cache = null;
        if (CACHE_MAP.get(name) != null) {
            cache = CACHE_MAP.get(name);
        } else {
            Set<Class<?>> ann = new Reflections("me.linxiaowei.cache.core.impl").getTypesAnnotatedWith(CacheName.class);
            for (Class clazz : ann) {
                for (Annotation annotation : clazz.getAnnotations()) {
                    if (annotation instanceof CacheName) {
                        String value = ((CacheName) annotation).value();
                        if (value.equals(name)) {
                            cache = ((CacheBuilder) clazz.newInstance()).build(name);
                            CACHE_MAP.put(name, cache);
                        }
                    }
                }
            }
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
