package com.lizikj.cron.admin.controller;

import com.github.pagehelper.PageInfo;
import com.lizikj.cron.admin.common.ActionRespData;
import com.lizikj.cron.admin.common.ValidateUtil;
import com.lizikj.cron.admin.model.DatatablesViewPage;
import com.lizikj.cron.admin.model.DatatablesViewPage.AaDataInterface;
import com.lizikj.cron.admin.service.ISchedulerConnectorService;
import com.lizikj.cron.dto.CronJobDto;
import com.lizikj.cron.enums.cron.CronJobStatus;
import com.lizikj.cron.facade.ICronFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
@Controller
@RequestMapping("/cronInfo")
public class CronInfoController {
    private final static Logger logger = LoggerFactory.getLogger(CronInfoController.class);

    @Autowired
    private ICronFacade dubboCronFacade;
    @Autowired
    private ISchedulerConnectorService schedulerConnectorService;

    @RequestMapping("/list")
    public String list() {
        return "cron/cron_list";
    }

    @RequestMapping("/editPage")
    public String editPage(Integer id, ModelMap map) {
        map.addAttribute("cronInfo", dubboCronFacade.findById(id));
        return "cron/cron_edit";
    }

    @RequestMapping("/addPage")
    public String addPage() {
        return "cron/cron_add";
    }

    @ResponseBody
    @RequestMapping("/edit")
    public Object edit(CronJobDto cronJobDto) {
        try {
            boolean flag = ValidateUtil.validateQuartzExpression(cronJobDto.getTriggerCron());
            if (!flag) {
                return ActionRespData.genError("触发时间表达式不正确！");
            }
            if (cronJobDto.getId() == null) {
                dubboCronFacade.insertCronInfo(cronJobDto);
                if (CronJobStatus.Enable.getStatus() == cronJobDto.getStatus()) {
                    addJob(cronJobDto);
                }
            } else {
                dubboCronFacade.updateCronInfo(cronJobDto);
                schedulerConnectorService.deleteJob(cronJobDto.getJobName(), cronJobDto.getJobGroup());
                if (CronJobStatus.Enable.getStatus() == cronJobDto.getStatus()) {
                    addJob(cronJobDto);
                }
            }
            return ActionRespData.genSus();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ActionRespData.genError();
        }
    }

    private void addJob(CronJobDto cronJob) throws Exception {
        Map<String, Object> jobMap = new HashMap<String, Object>();
        jobMap.put("name", cronJob.getJobName());
        jobMap.put("group", cronJob.getJobGroup());
        jobMap.put("description", cronJob.getJobDescription());
        jobMap.put("jobClass", cronJob.getJobClassName());
        jobMap.put("durability", true);
        jobMap.put("jobDetailClass", "org.quartz.impl.JobDetailImpl");

        Map<String, Integer> jobDataMap = new HashMap<String, Integer>();
        jobDataMap.put("jobId", cronJob.getId());
        jobMap.put("jobDataMap", jobDataMap);
        schedulerConnectorService.addJob(jobMap, false);

        Map<String, Object> triggerMap = new HashMap<String, Object>();
        triggerMap.put("name", cronJob.getTriggerName());
        triggerMap.put("group", cronJob.getTriggerGroup());
        triggerMap.put("description", "");
        triggerMap.put("cronExpression", cronJob.getTriggerCron());
        triggerMap.put("triggerClass", "org.quartz.impl.triggers.CronTriggerImpl");
        triggerMap.put("jobName", cronJob.getJobName());
        triggerMap.put("jobGroup", cronJob.getJobGroup());
        schedulerConnectorService.scheduleJob(cronJob.getJobName(), cronJob.getJobGroup(), triggerMap);
    }

    @ResponseBody
    @RequestMapping("/del")
    public Object del(Integer id) {
        try {
            CronJobDto cronJob = dubboCronFacade.findById(id);
            dubboCronFacade.deleteCronInfo(id);
            schedulerConnectorService.deleteJob(cronJob.getJobName(), cronJob.getJobGroup());
            return ActionRespData.genSus();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ActionRespData.genError();
        }
    }

    @ResponseBody
    @RequestMapping("/execute")
    public Object execute(Integer id) {
        try {
            CronJobDto cronJob = dubboCronFacade.findById(id);
            Map<String, String> jobDataMap = new HashMap<String, String>();
            jobDataMap.put("jobId", String.valueOf(cronJob.getId()));
            schedulerConnectorService.triggerJob(cronJob.getJobName(), cronJob.getJobGroup(), jobDataMap);
            return ActionRespData.genSus();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ActionRespData.genError();
        }
    }

    @ResponseBody
    @RequestMapping("/pauseJob")
    public Object pauseJob(String ids) {
        try {
            List<CronJobDto> cronJobList = dubboCronFacade.findByIds(ids);
            for (CronJobDto dto : cronJobList) {
                schedulerConnectorService.pauseTrigger(dto.getTriggerName(), dto.getTriggerGroup());
                schedulerConnectorService.pauseJob(dto.getJobName(), dto.getJobGroup());
            }
            return ActionRespData.genSus();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ActionRespData.genError();
        }
    }

    @ResponseBody
    @RequestMapping("/resumeJob")
    public Object resumeJob(String ids) {
        try {
            List<CronJobDto> cronJobList = dubboCronFacade.findByIds(ids);
            for (CronJobDto dto : cronJobList) {
                schedulerConnectorService.resumeJob(dto.getJobName(), dto.getJobGroup());
                schedulerConnectorService.resumeTrigger(dto.getTriggerName(), dto.getTriggerGroup());
            }
            return ActionRespData.genSus();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ActionRespData.genError();
        }
    }

    @ResponseBody
    @RequestMapping("/getData")
    Object getData(int sEcho, int iDisplayStart, int iDisplayLength) {
        PageInfo<CronJobDto> page = dubboCronFacade.findCronInfoList(iDisplayStart, iDisplayLength);

        DatatablesViewPage<CronJobDto> view = new DatatablesViewPage<CronJobDto>();
        view.setAaData(page.getList(), new AaDataInterface<CronJobDto>() {
            @Override
            public List<String> wrap(CronJobDto job) {
                return Arrays.asList(
                        "",
                        String.valueOf(job.getId()),
                        job.getJobName(),
                        job.getJobClassName(),
                        job.getTriggerName(),
                        job.getJobDescription(),
                        job.getTriggerCron(),
                        String.valueOf(job.getStatus()),
                        job.getTriggerState()
                );
            }
        });
        view.setiTotalDisplayRecords((int) page.getTotal());
        view.setiTotalRecords((int) page.getTotal());
        view.setsEcho(sEcho);
        return view;
    }

}
