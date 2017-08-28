package com.lizikj.cron.mq.enums;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public enum TagsEnum {
    LOAN_SUCCESS("满标"), REPAY_SUCCESS("还款");

    private String desc;

    private TagsEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
