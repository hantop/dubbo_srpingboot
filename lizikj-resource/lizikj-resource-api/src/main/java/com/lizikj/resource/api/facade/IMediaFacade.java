package com.lizikj.resource.api.facade;


import java.util.List;
import java.util.Map;

public interface IMediaFacade {

    /**
     * 获取七牛上传凭证
     * @param key
     * @return
     */
    String gainUploadVoucher(String key);

    /**
     * 获取附件远程七牛云存储地址
     * @param mediaIds 附件Id集合
     * @return  id 转换后的映射地址
     */
    Map<Long,String> getRemoteUrls(List<Long> mediaIds);
}
