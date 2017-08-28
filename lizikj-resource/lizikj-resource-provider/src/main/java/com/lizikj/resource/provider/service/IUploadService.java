package com.lizikj.resource.provider.service;



public interface IUploadService {

    /**
     * 获取上传凭证
     * @param fileName  可为空
     * @return 上传凭证
     */
    String uploadVoucher(String fileName);

    /**
     * 构建覆盖的上传凭证
     * @param fileName
     * @return
     */
    String buildUploadVoucher(String fileName);

    /**
     * 构建上传凭证
     * @return
     */
    String buildUploadVoucher();

}
