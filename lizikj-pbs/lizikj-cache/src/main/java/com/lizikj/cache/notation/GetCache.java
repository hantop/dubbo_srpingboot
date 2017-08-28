package com.lizikj.cache.notation;


import com.lizikj.cache.aspect.CacheAOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Michael.Huang on 2017/4/1.
 * 获取数据时的缓存注解。
 * 缓存中有值时，则取缓存中的值；
 * 缓存中无值时，则从数据库中取值并放入到缓存中。
 *
 * @see CacheAOP
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface GetCache {

    /**
     * 缓存中key的前缀
     */
    String prefix();

    /**
     * 过期时间，不设置则不过期
     */
    long expire() default -1;

    /**
     * 指定key的字段
     */
    String[] key() default {};

    /**
     * 缓存生效的条件
     */
    String[] cons() default "";

    /**
     * 是否删除
     */
    boolean delete() default false;
}
