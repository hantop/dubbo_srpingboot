package com.lizikj.cron.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lizikj.cron.dto.CronJobDto;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public interface ICronJobService {

	List<CronJobDto> findJobs();
    
    /**
	 * 查询计划任务
	 * @param state 计划任务状态
	 * @return List<CronInfo>
	 * */
	public PageInfo<CronJobDto> findCronInfoList(int iDisplayStart,int iDisplayLength,Integer status);
	
	/**
	 * 根据ID查询计划任务
	 * @param id 计划任务ID
	 * @return CronInfo
	 * */
	public CronJobDto findById(Integer id);
	
	public List<CronJobDto> findByIds(String ids);
	
	/**
	 * 添加计划任务
	 * */
	public int insertCronInfo(CronJobDto cronJobDto);
	
	/**
	 * 修改计划任务
	 * */
	public int updateCronInfo(CronJobDto cronJobDto);
	
	/**
	 * 删除计划任务
	 * @param id 任务id
	 * @return int
	 * */
	public int deleteCronInfo(Integer id);

}
