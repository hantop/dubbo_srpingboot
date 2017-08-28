package com.lizikj.resource.provider.service;


public interface IDownloadService {

    /**
     * 根据附件ID获取完整url地址
     * @param attachmentId
     * @return
     */
    String buildUrlPath(String attachmentId);
}
