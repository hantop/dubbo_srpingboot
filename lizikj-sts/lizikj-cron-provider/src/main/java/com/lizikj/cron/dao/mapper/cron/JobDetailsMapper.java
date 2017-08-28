package com.lizikj.cron.dao.mapper.cron;

import com.lizikj.cron.dao.model.cron.JobDetails;
import com.lizikj.cron.dao.model.cron.JobDetailsKey;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public interface JobDetailsMapper {
    int deleteByPrimaryKey(JobDetailsKey key);

    int insert(JobDetails record);

    int insertSelective(JobDetails record);

    JobDetails selectByPrimaryKey(JobDetailsKey key);

    int updateByPrimaryKeySelective(JobDetails record);

    int updateByPrimaryKeyWithBLOBs(JobDetails record);

    int updateByPrimaryKey(JobDetails record);
}