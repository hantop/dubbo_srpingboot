package com.lizikj.resource.provider.service.Impl;


import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.lizikj.resource.api.dto.TMediaDto;
import com.lizikj.resource.api.enums.ResourceErrorEnum;
import com.lizikj.resource.api.exception.ResourceException;
import com.lizikj.resource.provider.dao.mapper.ITMediaMapper;
import com.lizikj.resource.provider.dao.model.Media;
import com.lizikj.resource.provider.service.IMediaService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MediaService implements IMediaService{

    private static final Log log= LogFactory.getLog(MediaService.class);

    @Autowired
    private ITMediaMapper mediaDao;

    @Override
    public void save(Media tMediaDto) {
        if(null== tMediaDto)
            throw new ResourceException(ResourceErrorEnum.PARAMETERS_ERROR);
        try {
            String jsonStr= JSON.json(tMediaDto);
            log.info("开始保存附件数据:"+jsonStr);
            mediaDao.insert(tMediaDto);
            log.info("附件数据保存成功:"+jsonStr);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }

    @Override
    public Media findById(Long id) {
        if(null==id)
            throw new ResourceException(ResourceErrorEnum.PARAMETERS_ERROR);
        return mediaDao.findOne(Long.valueOf(id));
    }

    @Override
    public List<Media> findByIds(List<Long> ids) {
        return null;
    }
}
