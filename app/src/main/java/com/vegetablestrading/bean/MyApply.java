package com.vegetablestrading.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ${王sir} on 2017/11/21.
 * application 我的申请实体类
 */
@Entity
public class MyApply {
    @Id
    private Long id;
    @NotNull
    private String userName;//会员名
    private String applyType;//申请类型
    private String applyInfo;//申请备注
    private String applyStartTime;//申请开始时间
    private String applyEndTime;//申请结束时间
    private String applyTime;//申请时间
    private String operatingPeople;//审核人
    private String operateTime;//审核时间
    private String operateStatus;//审核状态
    private String operateNote;//审核备注
    @Generated(hash = 813569057)
    public MyApply(Long id, @NotNull String userName, String applyType,
            String applyInfo, String applyStartTime, String applyEndTime,
            String applyTime, String operatingPeople, String operateTime,
            String operateStatus, String operateNote) {
        this.id = id;
        this.userName = userName;
        this.applyType = applyType;
        this.applyInfo = applyInfo;
        this.applyStartTime = applyStartTime;
        this.applyEndTime = applyEndTime;
        this.applyTime = applyTime;
        this.operatingPeople = operatingPeople;
        this.operateTime = operateTime;
        this.operateStatus = operateStatus;
        this.operateNote = operateNote;
    }
    @Generated(hash = 834780815)
    public MyApply() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getApplyType() {
        return this.applyType;
    }
    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }
    public String getApplyInfo() {
        return this.applyInfo;
    }
    public void setApplyInfo(String applyInfo) {
        this.applyInfo = applyInfo;
    }
    public String getApplyStartTime() {
        return this.applyStartTime;
    }
    public void setApplyStartTime(String applyStartTime) {
        this.applyStartTime = applyStartTime;
    }
    public String getApplyEndTime() {
        return this.applyEndTime;
    }
    public void setApplyEndTime(String applyEndTime) {
        this.applyEndTime = applyEndTime;
    }
    public String getApplyTime() {
        return this.applyTime;
    }
    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }
    public String getOperatingPeople() {
        return this.operatingPeople;
    }
    public void setOperatingPeople(String operatingPeople) {
        this.operatingPeople = operatingPeople;
    }
    public String getOperateTime() {
        return this.operateTime;
    }
    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }
    public String getOperateStatus() {
        return this.operateStatus;
    }
    public void setOperateStatus(String operateStatus) {
        this.operateStatus = operateStatus;
    }
    public String getOperateNote() {
        return this.operateNote;
    }
    public void setOperateNote(String operateNote) {
        this.operateNote = operateNote;
    }

}
