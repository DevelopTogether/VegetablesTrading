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
    private String petName;//会员名
    private String applyType;//申请类型
    private String applyInfo;//申请备注
    private String apply_startTime;//申请开始时间
    private String apply_endTime;//申请结束时间
    private String apply_Time;//申请时间
    private String operatingPeople;//审核人
    private String operateTime;//审核时间
    private String operateStatus;//审核状态
    private String operateNote;//审核备注
    @Generated(hash = 1354302475)
    public MyApply(Long id, @NotNull String petName, String applyType,
            String applyInfo, String apply_startTime, String apply_endTime,
            String apply_Time, String operatingPeople, String operateTime,
            String operateStatus, String operateNote) {
        this.id = id;
        this.petName = petName;
        this.applyType = applyType;
        this.applyInfo = applyInfo;
        this.apply_startTime = apply_startTime;
        this.apply_endTime = apply_endTime;
        this.apply_Time = apply_Time;
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
    public String getPetName() {
        return this.petName;
    }
    public void setPetName(String petName) {
        this.petName = petName;
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
    public String getApply_startTime() {
        return this.apply_startTime;
    }
    public void setApply_startTime(String apply_startTime) {
        this.apply_startTime = apply_startTime;
    }
    public String getApply_endTime() {
        return this.apply_endTime;
    }
    public void setApply_endTime(String apply_endTime) {
        this.apply_endTime = apply_endTime;
    }
    public String getApply_Time() {
        return this.apply_Time;
    }
    public void setApply_Time(String apply_Time) {
        this.apply_Time = apply_Time;
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
