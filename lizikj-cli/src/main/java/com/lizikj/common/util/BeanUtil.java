package com.lizikj.common.util;

import org.apache.commons.lang.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public class BeanUtil {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    public static List<String> getPropertyNames(Object obj) {
        return getPropertyNames(obj, false, false, null);
    }

    /**
     * 获得对象所有的property names,包括对象的field和getXxx方法所代表的property
     * 如果是由getXxx方法所产生的property,
     * 则忽略annotation包括ignoreClass的
     *
     * @param obj
     * @param fieldOnly             是否只取field
     * @param ignoreNull            是否忽略为null的
     * @param ignoreAnnotationClass
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List<String> getPropertyNames(Object obj, boolean fieldOnly, boolean ignoreNull, Class ignoreAnnotationClass) {
        final BeanWrapper src = new BeanWrapperImpl(obj);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        List<String> names = new ArrayList<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            String name = pd.getName();
            boolean ignore = false;

            Field field = FieldUtils.getField(obj.getClass(), name, true);
            if (field == null) {
                if (fieldOnly) {
                    logger.info("property " + name + " isn't a field, will be ignored!");
                    ignore = true;
                } else {
                    Method method = pd.getReadMethod();
                    if (method == null) {
                        method = pd.getWriteMethod();
                    }
                    if (method == null) {
                        logger.info("property " + name + "'s has no set or get method, will be ignored!");
                        ignore = true;
                    } else {
                        if (ignoreAnnotationClass != null) {
                            if (method.getAnnotation(ignoreAnnotationClass) != null) {
                                logger.info("property(method)" + name + " has annotation " + ignoreAnnotationClass + ", will be ignored!");
                                ignore = true;
                            }
                        }
                    }
                }
            } else {
                if (ignoreNull && src.getPropertyValue(name) == null) {
                    logger.info("property(field) " + name + "'s value is null, ignore!");
                    ignore = true;
                }
            }
            if (!ignore) {
                names.add(pd.getName());
            }
        }
        return names;
    }

    public static void copyPropertiesInclude(Object src, Object target, List<String> properties) {
        final BeanWrapper srcWrapper = new BeanWrapperImpl(src);
        final BeanWrapper targetWrapper = new BeanWrapperImpl(target);
        for (String property : properties) {
            targetWrapper.setPropertyValue(property, srcWrapper.getPropertyValue(property));
        }
    }

    /**
     * 获取为null的property
     *
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static Set<String> getNotNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> notEmptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue != null) notEmptyNames.add(pd.getName());
        }
        return notEmptyNames;
    }

    /**
     * 获取对象的properties之外的其他属性
     *
     * @param source
     * @param excludeProperties
     * @return
     */
    public static String[] getOtherPropertyNames(Object source, Set<String> excludeProperties) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> names = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            if (!excludeProperties.contains(pd.getName())) {
                names.add(pd.getName());
            }
        }
        return names.toArray(new String[names.size()]);
    }

    /**
     * 复制src的非null字段到target
     *
     * @param src
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * 复制src的字段到target
     *
     * @param src
     * @param target
     */
    public static void copyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target);
    }

    /**
     * 复制src的properties字段到target
     *
     * @param src
     * @param target
     * @param properties
     */
    public static void copyPropertiesInclude(Object src, Object target, Set<String> properties) {
        BeanUtils.copyProperties(src, target, getOtherPropertyNames(src, properties));
    }

    /**
     * 复制src的properties字段到target
     *
     * @param src
     * @param target
     * @param properties
     */
    public static void copyPropertiesExclude(Object src, Object target, String... properties) {
        BeanUtils.copyProperties(src, target, properties);
    }

    public static void main(String[] args) {
        System.out.println(FieldUtils.getField(Object.class, ""));
    }
}
