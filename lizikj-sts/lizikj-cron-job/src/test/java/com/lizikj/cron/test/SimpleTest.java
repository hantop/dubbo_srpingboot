package com.lizikj.cron.test;

import java.util.Date;

import org.junit.Test;
import org.quartz.CronExpression;

public class SimpleTest {
	@Test
	public void check() throws Exception {
        CronExpression cronExpression = new CronExpression("0 0/10 * * * ?");
        Date date = new Date();
        date = cronExpression.getNextValidTimeAfter(date);
        
        System.out.println(date);
	}
}
