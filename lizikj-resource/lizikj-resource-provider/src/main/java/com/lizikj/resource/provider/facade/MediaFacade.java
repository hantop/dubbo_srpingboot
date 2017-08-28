package com.lizikj.resource.provider.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.lizikj.resource.api.enums.ResourceErrorEnum;
import com.lizikj.resource.api.exception.ResourceException;
import com.lizikj.resource.api.facade.IMediaFacade;
import com.lizikj.resource.provider.dao.model.Media;
import com.lizikj.resource.provider.service.IQiniuService;
import com.lizikj.resource.provider.service.Impl.MediaService;
import com.qiniu.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(protocol = {"dubbo"},cluster = "failfast")
public class MediaFacade implements IMediaFacade {

    @Autowired
    private IQiniuService qiniuService;

    @Autowired
    private MediaService mediaService;

    @Override
    public String gainUploadVoucher(String key) {
        return qiniuService.uploadVoucher(key);
    }

    @Override
    public Map<Long,String> getRemoteUrls(List<Long> mediaIds) {
        if(CollectionUtils.isEmpty(mediaIds))
            throw new ResourceException(ResourceErrorEnum.PARAMETERS_ERROR);
        List<Media> mediaList=mediaService.findByIds(mediaIds);
        if(CollectionUtils.isNotEmpty(mediaList)){
            Map<Long,String> urls=new HashMap<>();
            for(Media media:mediaList){
                String key=media.getQiniuKey();
                if(!StringUtils.isNullOrEmpty(key)){
                    urls.put(media.getId(),qiniuService.buildUrLPath(key));
                }
            }
            if(urls.size()!=0)
                return urls;
        }
        return null;
    }
}
