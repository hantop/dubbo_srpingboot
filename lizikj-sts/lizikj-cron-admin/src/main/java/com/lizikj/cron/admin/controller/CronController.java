package com.lizikj.cron.admin.controller;

import com.lizikj.cron.admin.enums.TriggerState;
import com.lizikj.cron.admin.service.ISchedulerConnectorService;
import com.lizikj.cron.dto.CronJobDto;
import com.lizikj.cron.facade.ICronFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
@Controller
@RequestMapping("/cron")
public class CronController {
    @Autowired
    private ICronFacade dubboCronFacade;
    @Autowired
    private ISchedulerConnectorService schedulerConnectorService;

    @RequestMapping("/")
    public String index(Model model) {
        List<CronJobDto> cronJobs = dubboCronFacade.findCronJobs();

        model.addAttribute("cronJobs", cronJobs);
        return "temp/index";
    }

    @RequestMapping("/resumeTrigger")
    public String resumeTrigger(String triggerName, String triggerGroupName) {
        schedulerConnectorService.resumeTrigger(triggerName, triggerGroupName);
        return "redirect:/cron";
    }

    @RequestMapping("/pauseTrigger")
    public String pauseTrigger(String triggerName, String triggerGroupName) {
        schedulerConnectorService.pauseTrigger(triggerName, triggerGroupName);
        return "redirect:/cron";
    }

    @RequestMapping("/resumeJob")
    public String resumeJob(String jobName, String jobGroupName) {
        schedulerConnectorService.resumeJob(jobName, jobGroupName);
        return "redirect:/cron";
    }

    @RequestMapping("/pauseJob")
    public String pauseJob(String jobName, String jobGroupName) {
        schedulerConnectorService.pauseJob(jobName, jobGroupName);
        return "redirect:/cron";
    }

    @ResponseBody
    @RequestMapping("/getTriggerState")
    public String getTriggerState(String triggerName, String triggerGroupName) {
        TriggerState triggerState = schedulerConnectorService.getTriggerState(triggerName, triggerGroupName);
        return triggerState.value();
    }


}
