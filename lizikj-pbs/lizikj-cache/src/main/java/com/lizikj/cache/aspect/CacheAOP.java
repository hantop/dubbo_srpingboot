package com.lizikj.cache.aspect;

import com.alibaba.fastjson.JSONObject;
import com.lizikj.cache.Cache;
import com.lizikj.cache.constant.ComparisonSymbol;
import com.lizikj.cache.constant.ComparisonType;
import com.lizikj.cache.notation.ExpireCache;
import com.lizikj.cache.notation.GetCache;
import com.lizikj.cache.parser.KeyParser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang.ArrayUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isBlank;


/**
 * Created by Michael.Huang on 2017/4/1.
 * 缓存切面的逻辑实现
 */
@Aspect
@Component
public class CacheAOP {

    private static Logger logger = LoggerFactory.getLogger(CacheAOP.class);

    private static String SEPARATOR = "_";

    @Autowired
    private Cache cache;

    @Autowired
    private KeyParser keyParser;

    @Pointcut("@annotation(com.lizikj.cache.notation.GetCache)")
    public void getCache() {
    }

    @Pointcut("@annotation(com.lizikj.cache.notation.ExpireCache)")
    public void expireCache() {
    }

    @Around("getCache()")
    public Object aroundGet(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();

        boolean delete = method.getAnnotation(GetCache.class).delete();
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            throw new RuntimeException("parameterless method is not allowed!");
        }
        if (!meetCons(method.getAnnotation(GetCache.class).cons(), args, ms)) {
            return joinPoint.proceed();
        }
        String[] keys = method.getAnnotation(GetCache.class).key();
        String key = getKey(ms, args, keys);
        String prefix = method.getAnnotation(GetCache.class).prefix();
        String cacheKey = prefix + key;

        Object object = cache.get(cacheKey);
        if (object != null) {
            if (delete) {
                cache.delete(cacheKey);
            }
            return object;
        }

        Object proceed = joinPoint.proceed();
        if (proceed == null) {
            return null;
        }

        long expire = method.getAnnotation(GetCache.class).expire();
        if (expire == -1) {
            cache.set(cacheKey, proceed);
        } else {
            cache.set(cacheKey, proceed, expire, TimeUnit.SECONDS);
        }
        logger.info("『{}〓{}』 has been saved to cache and will be expired in {} sec", cacheKey, JSONObject.toJSONString(proceed), expire);
        if (delete) {
            cache.delete(cacheKey);
        }
        return proceed;
    }

    /**
     * 是否满足条件
     *
     * @param cons
     * @param args
     * @return
     */
    private boolean meetCons(String[] cons, Object[] args, MethodSignature methodSignature) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        if (isEmpty(cons)) {
            return true;
        }
        for (String con : cons) {
            if (!isComparing(con)) {
                logger.warn("No comparison detected in [{}], comparison aborted.", con);
                continue;
            }
            int comparisonType = getComparisonType(con);
            if (comparisonType == ComparisonType.UNRECOGNIZED) {
                logger.warn("Unrecognized comparison type [{}] for con [{}], comparison aborted.", comparisonType, con);
                continue;
            }
            boolean result = doCompare(con, comparisonType, methodSignature, args);
            if (!result) {
                return false;
            }
        }
        return true;
    }

    /**
     * perform comparing according to the comparison type and return the result.
     *
     * @param con
     * @param comparisonType
     * @return
     */
    private boolean doCompare(String con, int comparisonType, MethodSignature methodSignature, Object[] args) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        String symbol = getSymbolByComparisonType(comparisonType);
        String[] split = con.split(symbol);
        if (isEmpty(split) || split.length < 2) {
            logger.warn("A comparison requires 2 competitors. Comparison of [{}] aborted.", con);
            return true;
        }
        Object competitorA = keyParser.parse(split[0], args, methodSignature);
        Object competitorB = keyParser.parse(split[1], args, methodSignature);

        if (competitorA == null || competitorB == null) {
            logger.warn("Failed to parse [{}], comparison aborted.", con);
            return true;
        }

        int result = competitorA.toString().compareTo(competitorB.toString());

        if (result == -1) {
            if (comparisonType == ComparisonType.LESS || comparisonType == ComparisonType.LESSEQUALS) {
                return true;
            } else {
                return false;
            }
        } else if (result == 1) {
            if (comparisonType == ComparisonType.LARGER || comparisonType == ComparisonType.LARGEREQUALS) {
                return true;
            } else {
                return false;
            }
        } else {
            if (comparisonType == ComparisonType.EQUALS || comparisonType == ComparisonType.LARGEREQUALS || comparisonType == ComparisonType.LESSEQUALS) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * obtain the comparison symbol.
     *
     * @param comparisonType
     * @return
     */
    private String getSymbolByComparisonType(int comparisonType) {
        if (comparisonType == ComparisonType.EQUALS) {
            return ComparisonSymbol.EQUALS;
        }
        if (comparisonType == ComparisonType.LESS) {
            return ComparisonSymbol.LESS;
        }
        if (comparisonType == ComparisonType.LESSEQUALS) {
            return ComparisonSymbol.LESSEQUALS;
        }
        if (comparisonType == ComparisonType.LARGER) {
            return ComparisonSymbol.LARGER;
        }
        if (comparisonType == ComparisonType.LARGEREQUALS) {
            return ComparisonSymbol.LARGEREQUALS;
        }
        throw new RuntimeException("unrecognized comparison type.");
    }

    /**
     * determine the type of comparing.
     *
     * @param con
     * @return
     */
    private int getComparisonType(String con) {
        if (con.contains(ComparisonSymbol.EQUALS)) {
            return ComparisonType.EQUALS;
        }
        if (con.contains(ComparisonSymbol.LARGEREQUALS)) {
            return ComparisonType.LARGEREQUALS;
        }
        if (con.contains(ComparisonSymbol.LESSEQUALS)) {
            return ComparisonType.LESSEQUALS;
        }
        if (con.contains(ComparisonSymbol.LARGER)) {
            return ComparisonType.LARGER;
        }
        if (con.contains(ComparisonSymbol.LESS)) {
            return ComparisonType.LESS;
        }
        return ComparisonType.UNRECOGNIZED;
    }

    /**
     * determine whether it's comparing.
     *
     * @param con
     * @return
     */
    private boolean isComparing(String con) {
        return con.contains(ComparisonSymbol.EQUALS)
                || con.contains(ComparisonSymbol.LARGER)
                || con.contains(ComparisonSymbol.LESS);
    }

    @After("expireCache()")
    public void afterExpire(JoinPoint joinPoint) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        String prefix = method.getAnnotation(ExpireCache.class).prefix();
        String[] keys = method.getAnnotation(ExpireCache.class).key();
        Object[] args = joinPoint.getArgs();

        String key = getKey(ms, args, keys);
        String cacheKey = prefix + key;

        cache.delete(cacheKey);
        logger.info("『{}』 has been deleted from cache.", cacheKey);
    }

    /**
     * 获取key
     *
     * @param methodSignature
     * @param args
     * @param keys
     * @return
     */
    private String getKey(MethodSignature methodSignature, Object[] args, String[] keys) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        if (isEmpty(keys)) {
            logger.info("no key has been specified, all the args will be used as keys");
            return arrayToString(args);
        }
        if (isAllVariable(keys) && isEmpty(args)) {
            throw new RuntimeException("parameterless method is not allowed in all-variable scenario.");
        }
        StringBuilder keyBuilder = new StringBuilder();
        for (String key : keys) {
            String parsedKey = keyParser.parse(key, args, methodSignature);
            if (isBlank(parsedKey)) {
                continue;
            }
            keyBuilder.append(parsedKey).append(SEPARATOR);
        }
        if (isBlank(keyBuilder.toString())) {
            throw new RuntimeException("no key has been specified.");
        }

        return keyBuilder.substring(0, keyBuilder.length() - 1);
    }

    private boolean isAllVariable(String[] keys) {
        for (String key : keys) {
            if (!keyParser.isVariable(key)) {
                return false;
            }
        }
        return true;
    }

    private String arrayToString(Object[] args) {
        if (args == null || args.length == 0) {
            return "";
        }
        StringBuilder array = new StringBuilder();
        for (Object arg : args) {
            array.append(arg.toString()).append("_");
        }
        return array.substring(0, array.length() - 1);
    }
}
