package com.lizikj.cron.service.impl;

import com.lizikj.common.util.ObjectConvertUtil;
import com.lizikj.cron.dao.mapper.cron.CronClientMapper;
import com.lizikj.cron.dao.model.cron.CronClient;
import com.lizikj.cron.dto.CronClientDto;
import com.lizikj.cron.enums.cron.CronClientStatus;
import com.lizikj.cron.service.ICronClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
@Service
public class CronClientServiceImpl implements ICronClientService {

    @Autowired
    private CronClientMapper cronClientMapper;

    @Override
    public List<CronClientDto> findAll(CronClientStatus cronClientStatus) {
        List<CronClient> cronClientList = cronClientMapper.findAll(cronClientStatus.getStatus());
        return ObjectConvertUtil.copyListProperties(cronClientList, CronClientDto.class);
    }
}
