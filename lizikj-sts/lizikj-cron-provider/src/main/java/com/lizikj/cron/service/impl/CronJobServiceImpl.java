package com.lizikj.cron.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lizikj.common.util.ObjectConvertUtil;
import com.lizikj.cron.dao.mapper.cron.CronJobMapper;
import com.lizikj.cron.dao.model.cron.CronJob;
import com.lizikj.cron.dto.CronJobDto;
import com.lizikj.cron.enums.cron.CronJobStatus;
import com.lizikj.cron.service.ICronJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
@Service
public class CronJobServiceImpl implements ICronJobService {

    @Autowired
    private CronJobMapper cronJobMapper;

    @Override
    public List<CronJobDto> findJobs() {
        List<CronJob> cronJobList = cronJobMapper.findAll(CronJobStatus.Enable.getStatus());
        return ObjectConvertUtil.copyListProperties(cronJobList, CronJobDto.class);
    }

    /**
     * 查询计划任务
     *
     * @param state 任务状态
     * @return List<CronInfo>
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public PageInfo<CronJobDto> findCronInfoList(int iDisplayStart, int iDisplayLength, Integer status) {
        PageHelper.startPage(Integer.parseInt(String.valueOf(iDisplayStart / iDisplayLength + 1)), iDisplayLength);
        List<CronJob> cronJoblist = cronJobMapper.findCronInfoList(status);
        PageInfo page = new PageInfo(cronJoblist);

        List<CronJobDto> cronJobDtoList = ObjectConvertUtil.copyListProperties(page.getList(), CronJobDto.class);
        page.setList(cronJobDtoList);
        return page;
    }

    /**
     * 根据ID查询计划任务
     *
     * @param id 计划任务ID
     * @return CronInfo
     */
    public CronJobDto findById(Integer id) {
        CronJob cronJob = cronJobMapper.findById(id);
        return ObjectConvertUtil.copyProperties(CronJobDto.class, cronJob);
    }

    public List<CronJobDto> findByIds(String ids) {
        List<CronJob> cronJobList = cronJobMapper.findByIds(ids);
        return ObjectConvertUtil.copyListProperties(cronJobList, CronJobDto.class);
    }

    /**
     * 添加计划任务
     */
    public int insertCronInfo(CronJobDto cronJobDto) {
        CronJob cronJob = ObjectConvertUtil.copyProperties(CronJob.class, cronJobDto);
        return cronJobMapper.insertCronInfo(cronJob);
    }

    /**
     * 修改计划任务
     */
    public int updateCronInfo(CronJobDto cronJobDto) {
        CronJob cronJob = ObjectConvertUtil.copyProperties(CronJob.class, cronJobDto);
        return cronJobMapper.updateCronInfo(cronJob);
    }

    /**
     * 删除计划任务
     *
     * @param id 任务id
     * @return int
     */
    public int deleteCronInfo(Integer id) {
        return cronJobMapper.deleteCronInfo(id);
    }
}
