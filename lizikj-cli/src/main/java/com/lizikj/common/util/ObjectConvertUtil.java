package com.lizikj.common.util;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public class ObjectConvertUtil {
    private final static Logger logger = LoggerFactory.getLogger(ObjectConvertUtil.class);

    public static <T, E> T copyProperties(Class<T> destClassType, E orig) throws RuntimeException {
        if (orig == null) {
            logger.error("转换对象为空");
            return null;
        }
        try {
            T dest = destClassType.newInstance();
            BeanUtils.copyProperties(dest, orig);
            return dest;
        } catch (Exception e) {
            logger.error("转换对象出错:", e);
            throw new RuntimeException("对象复制异常");
        }
    }

    public static <F, T> List<T> copyListProperties(List<F> origList, final Class<T> destClassType) {
        if (origList == null) {
            logger.error("转换对象为空");
            return null;
        }
        List<T> destList = Lists.transform(origList, new Function<F, T>() {
            @Override
            public T apply(F orig) {
                return ObjectConvertUtil.copyProperties(destClassType, orig);
            }
        });
        return Lists.newArrayList(destList);
    }

}
