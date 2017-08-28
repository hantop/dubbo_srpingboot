package com.lizikj.resource.provider.service.Impl;


import com.alibaba.dubbo.common.utils.StringUtils;
import com.lizikj.resource.api.dto.TMediaDto;
import com.lizikj.resource.api.enums.ResourceErrorEnum;
import com.lizikj.resource.api.exception.ResourceException;
import com.lizikj.resource.provider.service.IDownloadService;
import com.lizikj.resource.provider.service.IMediaService;
import com.lizikj.resource.provider.service.IQiniuService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DownloadService implements IDownloadService {

    private static final Log log = LogFactory.getLog(DownloadService.class);
    @Autowired
    private IQiniuService qiniuService;
    @Autowired
    private IMediaService mediaService;

    public String buildUrlPath(String mediaId) {
        if(StringUtils.isEmpty(mediaId))
            throw new ResourceException(ResourceErrorEnum.PARAMETERS_ERROR);
        TMediaDto media=mediaService.findById(Long.valueOf(mediaId));
        if(null!=media){
            String key=media.getQiniuKey();
            if(StringUtils.isNotEmpty(key))
                return qiniuService.buildUrLPath(key);
        }
        log.warn("不存在ID为"+mediaId+"的附件!");
        return null;
    }
}
