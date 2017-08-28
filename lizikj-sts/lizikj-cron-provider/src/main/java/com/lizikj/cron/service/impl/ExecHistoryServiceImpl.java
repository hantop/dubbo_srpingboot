package com.lizikj.cron.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lizikj.common.util.ObjectConvertUtil;
import com.lizikj.cron.dao.mapper.cron.ExecHistoryMapper;
import com.lizikj.cron.dao.model.cron.ExecHistory;
import com.lizikj.cron.dto.ExecHistoryDto;
import com.lizikj.cron.service.IExecHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
@Service
public class ExecHistoryServiceImpl implements IExecHistoryService {

    @Autowired
    private ExecHistoryMapper execHistoryMapper;

    @Override
    public Integer insertSelective(ExecHistoryDto execHistoryDto) {
        ExecHistory execHistory = ObjectConvertUtil.copyProperties(ExecHistory.class, execHistoryDto);
        execHistoryMapper.insertSelective(execHistory);
        return execHistory.getId();
    }

    @Override
    public void updateByPrimaryKeySelective(ExecHistoryDto execHistoryDto) {
        ExecHistory execHistory = ObjectConvertUtil.copyProperties(ExecHistory.class, execHistoryDto);
        execHistoryMapper.updateByPrimaryKeySelective(execHistory);
    }

    /**
     * 查询计划任务日志
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public PageInfo<ExecHistoryDto> findCronLogList(int iDisplayStart, int iDisplayLength, ExecHistoryDto execHistoryDto) {
        PageHelper.startPage(Integer.parseInt(String.valueOf(iDisplayStart / iDisplayLength + 1)), iDisplayLength);
        ExecHistory execHistory = ObjectConvertUtil.copyProperties(ExecHistory.class, execHistoryDto);
        List<ExecHistory> execHistoryList = execHistoryMapper.findCronLogList(execHistory);
        PageInfo page = new PageInfo(execHistoryList);

        List<ExecHistoryDto> execHistoryDtoList = ObjectConvertUtil.copyListProperties(page.getList(), ExecHistoryDto.class);
        page.setList(execHistoryDtoList);
        return page;
    }

    /**
     * 删除日志
     *
     * @param executeDate 执行日期
     * @return int
     */
    public int deleteCronLog(String executeDate) {
        return execHistoryMapper.deleteCronLog(executeDate);
    }

}
