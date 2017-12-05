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
    private String petName;//会员名
    private String pick_Time;//采摘时间
    private String pickPeopleNumber;//采摘人数
    private String residualPickAmount;//剩余采摘次数
    private String operatingPeople;//操作人
    private String operateTime;//操作时间
    private String operateNote;//操作备注时间
    @Generated(hash = 67802872)
    public MyPickInfo(Long id, @NotNull String petName, String pick_Time,
            String pickPeopleNumber, String residualPickAmount,
            String operatingPeople, String operateTime, String operateNote) {
        this.id = id;
        this.petName = petName;
        this.pick_Time = pick_Time;
        this.pickPeopleNumber = pickPeopleNumber;
        this.residualPickAmount = residualPickAmount;
        this.operatingPeople = operatingPeople;
        this.operateTime = operateTime;
        this.operateNote = operateNote;
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
    public String getPetName() {
        return this.petName;
    }
    public void setPetName(String petName) {
        this.petName = petName;
    }
    public String getPick_Time() {
        return this.pick_Time;
    }
    public void setPick_Time(String pick_Time) {
        this.pick_Time = pick_Time;
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
    public String getOperateNote() {
        return this.operateNote;
    }
    public void setOperateNote(String operateNote) {
        this.operateNote = operateNote;
    }

}
