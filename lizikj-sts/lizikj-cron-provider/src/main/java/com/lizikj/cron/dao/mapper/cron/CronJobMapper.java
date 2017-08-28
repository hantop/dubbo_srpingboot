package com.lizikj.cron.dao.mapper.cron;

import com.lizikj.cron.dao.model.cron.CronJob;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public interface CronJobMapper {

    List<CronJob> findAll(@Param("status") Byte status);

    /**
     * 查询计划任务
     *
     * @param state 任务状态
     * @return List<CronInfo>
     */
    List<CronJob> findCronInfoList(@Param("status") Integer status);

    /**
     * 根据ID查询计划任务
     *
     * @param id 计划任务ID
     * @return CronInfo
     */
    public CronJob findById(@Param("id") Integer id);

    public List<CronJob> findByIds(@Param("ids") String ids);

    /**
     * 添加计划任务
     */
    int insertCronInfo(CronJob cronInfo);

    /**
     * 修改计划任务
     */
    int updateCronInfo(CronJob cronInfo);

    /**
     * 删除计划任务
     */
    int deleteCronInfo(@Param("id") Integer id);
}