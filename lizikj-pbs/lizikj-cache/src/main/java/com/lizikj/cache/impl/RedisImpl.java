package com.lizikj.cache.impl;

import com.lizikj.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
@Component("cache")
public class RedisImpl<K, V> implements Cache<RedisTemplate<K, V>> {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void append(String key, String value) {
        redisTemplate.opsForValue().append(key, value);
    }

    @Override
    public void increment(String key, long delta) {
        redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public void increment(String key, double delta) {
        redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public boolean setIfAbsent(String key, Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public String getAsStr(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    @Override
    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public void expire(String key, long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeout, timeUnit);
    }

    @Override
    public Object getAndSet(String key, Object value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    @Override
    public Set<String> keys(String pattern) {
        Set<String> keys = new HashSet<>();
        RedisClusterConnection clusterConnection = null;
        try {
            clusterConnection = redisTemplate.getConnectionFactory().getClusterConnection();
            Iterable<RedisClusterNode> redisClusterNodes = clusterConnection.clusterGetNodes();
            Set<byte[]> keysInByte = new HashSet<>();
            for (RedisClusterNode redisClusterNode : redisClusterNodes) {
                Set<byte[]> keysInNode;
                try {
                    System.out.println(redisClusterNode);
                    System.out.println("===" + clusterConnection.ping(redisClusterNode) + "===");
                    keysInNode = clusterConnection.keys(redisClusterNode, pattern.getBytes());
                    if (isEmpty((keysInNode))) {
                        continue;
                    }
                    keysInByte.addAll(keysInNode);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            for (byte[] bytes : keysInByte) {
                keys.add(new String(bytes));
            }
            return keys;
        } catch (Exception e) {
            throw e;
        } finally {
            if (clusterConnection != null) {
                clusterConnection.close();
            }
        }
    }

    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void multiSet(Map<String, Object> paramMap) {
        if (isEmpty(paramMap)) {
            return;
        }
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            redisTemplate.opsForValue().set(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Map<String, Object> multiGet(Set<String> keys) {
        Map<String, Object> resultMap = new HashMap<>();
        if (isEmpty(keys)) {
            return Collections.EMPTY_MAP;
        }
        for (String key : keys) {
            resultMap.put(key, redisTemplate.opsForValue().get(key));
        }
        return resultMap;
    }

    @Override
    public Long sadd(String key, Object... value) {
        return redisTemplate.boundSetOps(key).add(value);
    }

    @Override
    public Set<Object> members(String key) {
        return redisTemplate.boundSetOps(key).members();
    }

    @Override
    public RedisTemplate<K, V> getCore() {
        return redisTemplate;
    }
}
