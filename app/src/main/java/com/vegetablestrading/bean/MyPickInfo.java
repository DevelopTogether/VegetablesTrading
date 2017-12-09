package com.vegetablestrading.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ${王sir} on 2017/11/23.
 * application 采摘记录实体
 */
@Entity
public class MyPickInfo {
    @Id
    private Long id;
    @NotNull
    private String userName;//会员名
    private String pickTime;//采摘时间
    private String pickPeopleNumber;//采摘人数
    private String residualPickAmount;//剩余采摘次数
    private String operatingPeople;//操作人
    private String operateTime;//操作时间
    private String noteInfo;//操作备注时间
    @Generated(hash = 1153771238)
    public MyPickInfo(Long id, @NotNull String userName, String pickTime,
            String pickPeopleNumber, String residualPickAmount,
            String operatingPeople, String operateTime, String noteInfo) {
        this.id = id;
        this.userName = userName;
        this.pickTime = pickTime;
        this.pickPeopleNumber = pickPeopleNumber;
        this.residualPickAmount = residualPickAmount;
        this.operatingPeople = operatingPeople;
        this.operateTime = operateTime;
        this.noteInfo = noteInfo;
    }
    @Generated(hash = 1742949351)
    public MyPickInfo() {
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
    public String getPickTime() {
        return this.pickTime;
    }
    public void setPickTime(String pickTime) {
        this.pickTime = pickTime;
    }
    public String getPickPeopleNumber() {
        return this.pickPeopleNumber;
    }
    public void setPickPeopleNumber(String pickPeopleNumber) {
        this.pickPeopleNumber = pickPeopleNumber;
    }
    public String getResidualPickAmount() {
        return this.residualPickAmount;
    }
    public void setResidualPickAmount(String residualPickAmount) {
        this.residualPickAmount = residualPickAmount;
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
    public String getNoteInfo() {
        return this.noteInfo;
    }
    public void setNoteInfo(String noteInfo) {
        this.noteInfo = noteInfo;
    }

}
