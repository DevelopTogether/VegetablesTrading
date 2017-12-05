package com.vegetablestrading.bean;

/**
 * Author:wang_sir
 * Time:2017/12/4 15:47
 * Description:This is LogisticsInfo  物流信息实体类
 */
public class LogisticsInfo {

    private String time;//物流更新时间
    private String status;//物流状态
    private String description;//物流信息描述


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
