package com.lizikj.cron.dao.mapper.cron;

import com.lizikj.cron.dao.model.cron.CronClient;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public interface CronClientMapper {

    int insert(CronClient record);

    List<CronClient> findAll(@Param("status") Byte status);
}