package com.lizikj.common.dto;

import com.github.pagehelper.PageInfo;
import com.lizikj.common.enums.ConvertErrorEnum;
import com.lizikj.common.exception.ConvertException;
import com.lizikj.common.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public abstract class BaseDTO implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(BaseDTO.class);

    private static final long serialVersionUID = 1L;

    /**
     * 将DTO List转换成Model List
     *
     * @param dtoList
     * @param clazz
     * @return
     */
    public static <T extends BaseModel, E extends BaseDTO> List<T> convertListModel(List<E> dtoList, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        for (E e : dtoList) {
            list.add(e.<T>convertToModel(clazz));
        }
        return list;
    }

    /**
     * 将DTO PageInfo转换成Model PageInfo
     *
     * @param pageInfo
     * @param clazz
     * @return
     */
    public static <T extends BaseModel, E extends BaseDTO> PageInfo<T> convertToPageInfoModel(PageInfo<E> pageInfo, Class<T> clazz) {
        List<T> list = BaseDTO.convertListModel(pageInfo.getList(), clazz);
        PageInfo<T> dtoPageInfo = new PageInfo<>(list);
        BeanUtil.copyPropertiesExclude(pageInfo, dtoPageInfo, "list");
        return dtoPageInfo;
    }

    public static final BigDecimal optional(BigDecimal d) {
        return d == null ? BigDecimal.ZERO : d;
    }

    public <T extends BaseModel> T convertToModel(Class<T> clazz) {
        T t;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("", e);
            throw new ConvertException(ConvertErrorEnum.DTO_CONVERT_TO_MODEL_ERROR, e.getMessage());
        }
        return convertToModel(t);
    }

    /**
     * 将DTO对象转换成Model
     *
     * @param t
     * @return
     */
    public <T extends BaseModel> T convertToModel(T t) {
        BeanUtil.copyProperties(this, t);
        return t;
    }


}
