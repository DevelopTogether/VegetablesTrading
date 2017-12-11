package com.vegetablestrading.bean;

/**
 * Author:wang_sir
 * Time:2017/12/4 15:47
 * Description:This is LogisticsInfo  物流信息实体类
 */
public class LogisticsInfo {

    private String datetime;//物流更新时间
    private String zone;
    private String remark;//物流信息描述

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
