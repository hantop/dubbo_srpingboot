package com.lizikj.cron.admin.controller;

import com.github.pagehelper.PageInfo;
import com.lizikj.common.util.DateUtils;
import com.lizikj.cron.admin.model.DatatablesViewPage;
import com.lizikj.cron.dto.ExecHistoryDto;
import com.lizikj.cron.facade.ICronFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
@Controller
@RequestMapping("/cronLog")
public class CronLogController {
    @Autowired
    private ICronFacade dubboCronFacade;

    @RequestMapping("/list")
    String list() {
        return "cron/cron_log";
    }

    @RequestMapping("/logPage")
    String logPage(String jobName, ModelMap map) {
        map.addAttribute("jobName", jobName);
        return "cron/cron_log";
    }

    @ResponseBody
    @RequestMapping("/getData")
    Object getData(int sEcho, int iDisplayStart, int iDisplayLength, ExecHistoryDto execHistoryDto) {
        PageInfo<ExecHistoryDto> page = dubboCronFacade.findCronLogList(iDisplayStart, iDisplayLength, execHistoryDto);

        DatatablesViewPage<ExecHistoryDto> view = new DatatablesViewPage<ExecHistoryDto>();
        view.setAaData(page.getList(), new DatatablesViewPage.AaDataInterface<ExecHistoryDto>() {
            @Override
            public List<String> wrap(ExecHistoryDto history) {
                return Arrays.asList(
                        String.valueOf(history.getId()),
                        history.getJobName(),
                        DateUtils.format(history.getStartedAt(), DateUtils.FULL_BAR_PATTERN),
                        DateUtils.format(history.getFinishedAt(), DateUtils.FULL_BAR_PATTERN),
                        String.valueOf(history.getDuration()),
                        String.valueOf(history.getStatus()),
                        history.getIpAddr(),
                        history.getThreadName()
                );
            }
        });
        view.setiTotalDisplayRecords((int) page.getTotal());
        view.setiTotalRecords((int) page.getTotal());
        view.setsEcho(sEcho);
        return view;
    }
}
