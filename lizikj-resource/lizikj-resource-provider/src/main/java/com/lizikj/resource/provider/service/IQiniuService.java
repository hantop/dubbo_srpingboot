package com.lizikj.resource.provider.service;


public interface IQiniuService {

    /**
     * 获取上传凭证
     * @return 凭证token
     */
    String uploadVoucher();

    /**
     * 获取覆盖上传凭证
     * @param key  需要覆盖的文件键值
     * @return 覆盖上传token
     */
    String uploadVoucher(String key);

    /**
     * 根据骑牛的key构建完整路径地址
     * @param key
     * @return 地址
     */
    String buildUrLPath(String key);
}
