package com.lizikj.cron.service;

import java.util.List;

import com.lizikj.cron.dto.CronClientDto;
import com.lizikj.cron.enums.cron.CronClientStatus;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public interface ICronClientService {

	List<CronClientDto> findAll(CronClientStatus cronClientStatus);

}
