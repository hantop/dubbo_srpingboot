package com.lizikj.cron.service;

import com.github.pagehelper.PageInfo;
import com.lizikj.cron.dto.ExecHistoryDto;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public interface IExecHistoryService {

    Integer insertSelective(ExecHistoryDto execHistoryDto);

    void updateByPrimaryKeySelective(ExecHistoryDto execHistoryDto);

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
