package com.lizikj.cache;

import com.lizikj.cache.Bootstrap;
import com.lizikj.cache.Cache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Bootstrap.class)
public class CacheTest {

    @Autowired
    private Cache cache;

    @Test
    public void test() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("hey", "there");
        map.put("what", "'up");
        map.put("hello", "world!");
        cache.multiSet(map);
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            cache.set(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }
        HashSet<String> strings = new HashSet<>();
        strings.add("hey");
        strings.add("what");
        strings.add("hello");
        Map<String, Object> stringObjectMap = cache.multiGet(strings);
        System.out.println(stringObjectMap);
        cache.set("hello", "world");
        System.out.println(cache.hasKey("hello"));
        cache.set("what", "the fuck");
        System.out.println(cache.get("what"));
        System.out.println(cache.getAndSet("what", "the fuck"));
        System.out.println(cache.get("what"));
        cache.setIfAbsent("what", "the hell");
        System.out.println(cache.get("what"));
        Set keys = cache.keys("*");
        System.out.println(keys);
    }

}
