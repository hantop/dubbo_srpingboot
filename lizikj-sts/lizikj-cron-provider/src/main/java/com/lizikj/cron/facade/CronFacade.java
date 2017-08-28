package com.lizikj.cron.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.lizikj.cron.dto.CronClientDto;
import com.lizikj.cron.dto.CronJobDto;
import com.lizikj.cron.dto.ExecHistoryDto;
import com.lizikj.cron.enums.cron.CronClientStatus;
import com.lizikj.cron.service.ICronClientService;
import com.lizikj.cron.service.ICronJobService;
import com.lizikj.cron.service.IExecHistoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
@Service(protocol = {"dubbo"}, cluster = "failfast")
public class CronFacade implements ICronFacade {
    @Autowired
    private ICronJobService cronJobService;
    @Autowired
    private ICronClientService cronClientService;
    @Autowired
    private IExecHistoryService execHistoryService;

    @Override
    public List<CronJobDto> findCronJobs() {
        return cronJobService.findJobs();
    }

    @Override
    public Integer insertExecHistory(ExecHistoryDto execHistoryDto) {
        return execHistoryService.insertSelective(execHistoryDto);
    }

    @Override
    public void updateExecHistory(ExecHistoryDto execHistoryDto) {
        execHistoryService.updateByPrimaryKeySelective(execHistoryDto);
    }

    @Override
    public List<CronClientDto> findAllCronClients(CronClientStatus status) {
        return cronClientService.findAll(status);
    }

    /**
     * 查询计划任务
     *
     * @param state 任务状态
     * @return List<CronInfo>
     */
    public PageInfo<CronJobDto> findCronInfoList(int iDisplayStart, int iDisplayLength) {
        return cronJobService.findCronInfoList(iDisplayStart, iDisplayLength, null);
    }

    /**
     * 根据ID查询计划任务
     *
     * @param id 计划任务ID
     * @return CronInfo
     */
    public CronJobDto findById(Integer id) {
        return cronJobService.findById(id);
    }

    public List<CronJobDto> findByIds(String ids) {
        return cronJobService.findByIds(ids);
    }

    /**
     * 添加计划任务
     */
    public int insertCronInfo(CronJobDto cronJobDto) {
        return cronJobService.insertCronInfo(cronJobDto);
    }

    /**
     * 修改计划任务
     */
    public int updateCronInfo(CronJobDto cronJobDto) {
        return cronJobService.updateCronInfo(cronJobDto);
    }

    /**
     * 删除计划任务
     *
     * @param id 任务id
     * @return int
     */
    public int deleteCronInfo(Integer id) {
        return cronJobService.deleteCronInfo(id);
    }

    /**
     * 查询计划任务日志
     */
    public PageInfo<ExecHistoryDto> findCronLogList(int iDisplayStart, int iDisplayLength, ExecHistoryDto execHistoryDto) {
        return execHistoryService.findCronLogList(iDisplayStart, iDisplayLength, execHistoryDto);
    }

    /**
     * 删除日志
     *
     * @param executeDate 执行日期
     * @return int
     */
    public int deleteCronLog(String executeDate) {
        return execHistoryService.deleteCronLog(executeDate);
    }

}
