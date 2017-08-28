package com.lizikj.cron.test;

import org.junit.Test;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

import com.lizikj.cron.service.IQuartzService;

public class JobTest extends BaseTest{
	
	@Autowired
	private IQuartzService quartzService;
	
	//@Test
	public void ProjectTenderFlowJobTest() throws SchedulerException,InterruptedException{
		quartzService.executeJob("ProjectTenderFlowJob", "ProjectTenderFlowJobGroup");
		
		Thread.sleep(120*1000);  
	}
	
	@Test
	public void AccountOpenCheckJobTest() throws SchedulerException,InterruptedException{
		quartzService.executeJob("AccountOpenCheckJob", "AccountOpenCheckJobGroup");
		
		Thread.sleep(120*1000);  
	}
	
}
