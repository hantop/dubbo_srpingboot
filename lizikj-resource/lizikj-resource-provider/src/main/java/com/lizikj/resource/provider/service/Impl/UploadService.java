package com.lizikj.resource.provider.service.Impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.lizikj.resource.provider.service.IQiniuService;
import com.lizikj.resource.provider.service.IUploadService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadService implements IUploadService {

    private static final Log log= LogFactory.getLog(UploadService.class);

    @Autowired
    private IQiniuService qiniuService;

    @Override
    public String uploadVoucher(String fileName) {
        if(StringUtils.isNotEmpty(fileName))
            return this.buildUploadVoucher(fileName);
        return this.buildUploadVoucher();
    }

    @Override
    public String buildUploadVoucher(String fileName) {
        return qiniuService.uploadVoucher(fileName);
    }

    @Override
    public String buildUploadVoucher() {
        return qiniuService.uploadVoucher();
    }
}
