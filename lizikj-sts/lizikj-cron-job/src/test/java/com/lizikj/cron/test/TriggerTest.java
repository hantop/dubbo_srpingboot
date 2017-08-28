package com.lizikj.cron.test;

import com.lizikj.cron.Bootstrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lizikj.cron.service.IQuartzService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Bootstrap.class)
@TestPropertySource(locations = "classpath:config/application-dev.properties")
public class TriggerTest {

	@Autowired
	private IQuartzService quartzService;

	@Test
	public void UpdateTriggerTest() {
		try {
			quartzService.runAllJob();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
