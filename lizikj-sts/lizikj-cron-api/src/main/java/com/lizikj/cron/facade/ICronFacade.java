package com.lizikj.cron.facade;

import com.github.pagehelper.PageInfo;
import com.lizikj.cron.dto.CronClientDto;
import com.lizikj.cron.dto.CronJobDto;
import com.lizikj.cron.dto.ExecHistoryDto;
import com.lizikj.cron.enums.cron.CronClientStatus;

import java.util.List;

/**
 * 计划任务管理接口
 * Created by Michael.Huang on 2016/8/3.
 */
public interface ICronFacade {

    List<CronJobDto> findCronJobs();

    Integer insertExecHistory(ExecHistoryDto execHistoryDto);

    void updateExecHistory(ExecHistoryDto execHistoryDto);

    List<CronClientDto> findAllCronClients(CronClientStatus status);

    /**
     * 查询计划任务
     *
     * @param state 计划任务状态
     * @return List<CronInfo>
     */
    public PageInfo<CronJobDto> findCronInfoList(int iDisplayStart, int iDisplayLength);

    /**
     * 根据ID查询计划任务
     *
     * @param id 计划任务ID
     * @return CronInfo
     */
    public CronJobDto findById(Integer id);

    public List<CronJobDto> findByIds(String ids);

    /**
     * 添加计划任务
     */
    public int insertCronInfo(CronJobDto cronJobDto);

    /**
     * 修改计划任务
     */
    public int updateCronInfo(CronJobDto cronJobDto);

    /**
     * 删除计划任务
     *
     * @param id 任务id
     * @return int
     */
    public int deleteCronInfo(Integer id);

    /**
     * 查询计划任务日志
     */
    public PageInfo<ExecHistoryDto> findCronLogList(int iDisplayStart, int iDisplayLength, ExecHistoryDto execHistoryDto);

    /**
     * 删除日志
     *
     * @param executeDate 执行日期
     * @return int
     */
    public int deleteCronLog(String executeDate);
}
