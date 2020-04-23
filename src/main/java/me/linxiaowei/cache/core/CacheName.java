package me.linxiaowei.cache.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存名注解
 *
 * @author Lin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CacheName {

    /**
     * @return 缓存名的值
     */
    String value();

}
