package com.lizikj.resource.provider.service.Impl;


import com.lizikj.qiniu.tools.QiniuTools;
import com.lizikj.resource.provider.service.IQiniuService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class QiniuService implements IQiniuService {

    private static final Log log= LogFactory.getLog(QiniuService.class);
    /**
     * 获取上传凭证token
     * @return
     */
    public String uploadVoucher(){
        return QiniuTools.gainUploadVoucher();
    }
    /**
     * 覆盖上传文件的凭证token:替换已上传文件
     * @param key
     * @return
     */
    public String uploadVoucher(String key){
        return QiniuTools.gainUploadVoucher(key);
    }

    @Override
    public String buildUrLPath(String key) {
        return QiniuTools.getDomain()+key;
    }
}
