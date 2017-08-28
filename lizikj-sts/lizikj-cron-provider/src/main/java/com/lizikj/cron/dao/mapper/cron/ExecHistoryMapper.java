package com.lizikj.cron.dao.mapper.cron;

import com.lizikj.cron.dao.model.cron.ExecHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public interface ExecHistoryMapper {

    /**
     * 插入日志
     */
    int insertSelective(ExecHistory record);

    /**
     * 修改日志
     */
    int updateByPrimaryKeySelective(ExecHistory record);

    /**
     * 查询计划任务日志
     */
    List<ExecHistory> findCronLogList(ExecHistory execHistory);

    /**
     * 删除日志
     *
     * @param executeDate 执行日期
     * @return int
     */
    int deleteCronLog(@Param("createDate") String createDate);
}