package com.lizikj.resource.api.dto;


import java.util.Date;

public class TMediaDto {
    //主键ID
    private Long id;
    //附件类型 或者文件后缀
    private String type;
    //文件名
    private String name;
    //本地路径
    private String path;
    //创建时间
    private Date createTime;
    //七牛存储的键：根据该值以及域名，可生成完整路径地址
    private String qiniuKey;
    //上传用户
    private String account;
    //来源  POS app 后台
    private String source;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQiniuKey() {
        return qiniuKey;
    }

    public void setQiniuKey(String qiniuKey) {
        this.qiniuKey = qiniuKey;
    }
}
