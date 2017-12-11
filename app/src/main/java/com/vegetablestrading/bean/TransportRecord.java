package com.vegetablestrading.bean;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;

/**
 * Created by ${王sir} on 2017/11/20.
 * application 配送记录实体类
 */
@Entity
public class TransportRecord {
    @Id
    private Long id;
    @NotNull
    private String transportRecordId;//配送记录ID
    private String logisticsNo;//物流单号
    private String logisticsName;//物流名称
    @Convert(columnType = String.class, converter = Logistics_converter.class)
    private List<LogisticsInfo> LogisticsInfos;
    @Convert(columnType = String.class, converter = TransportVegetable_converter.class)
    private List<TransportVegetableInfo> transportVegetableInfos;
    private String transportPeople;//配送人
    private String transportPeopleMobile;//配送人电话
    private String transportTime;//配送时间
    private String transportInfo;//配送详情
    private String userName;//会员名
    private String mobile;//会员电话
    private String address;//收货地址
    private String residualIntegral;//剩余积分
    private String relayBoxNo;//中转箱编号
    private String operatingPeople;//操作人
    private String operateTime;//操作时间
    private String noteInfo;//noteInfo
    @Generated(hash = 1604843095)
    public TransportRecord(Long id, @NotNull String transportRecordId, String logisticsNo,
            String logisticsName, List<LogisticsInfo> LogisticsInfos,
            List<TransportVegetableInfo> transportVegetableInfos, String transportPeople,
            String transportPeopleMobile, String transportTime, String transportInfo,
            String userName, String mobile, String address, String residualIntegral,
            String relayBoxNo, String operatingPeople, String operateTime, String noteInfo) {
        this.id = id;
        this.transportRecordId = transportRecordId;
        this.logisticsNo = logisticsNo;
        this.logisticsName = logisticsName;
        this.LogisticsInfos = LogisticsInfos;
        this.transportVegetableInfos = transportVegetableInfos;
        this.transportPeople = transportPeople;
        this.transportPeopleMobile = transportPeopleMobile;
        this.transportTime = transportTime;
        this.transportInfo = transportInfo;
        this.userName = userName;
        this.mobile = mobile;
        this.address = address;
        this.residualIntegral = residualIntegral;
        this.relayBoxNo = relayBoxNo;
        this.operatingPeople = operatingPeople;
        this.operateTime = operateTime;
        this.noteInfo = noteInfo;
    }
    @Generated(hash = 80826641)
    public TransportRecord() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTransportRecordId() {
        return this.transportRecordId;
    }
    public void setTransportRecordId(String transportRecordId) {
        this.transportRecordId = transportRecordId;
    }
    public String getLogisticsNo() {
        return this.logisticsNo;
    }
    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }
    public String getLogisticsName() {
        return this.logisticsName;
    }
    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }
    public List<LogisticsInfo> getLogisticsInfos() {
        return this.LogisticsInfos;
    }
    public void setLogisticsInfos(List<LogisticsInfo> LogisticsInfos) {
        this.LogisticsInfos = LogisticsInfos;
    }
    public List<TransportVegetableInfo> getTransportVegetableInfos() {
        return this.transportVegetableInfos;
    }
    public void setTransportVegetableInfos(
            List<TransportVegetableInfo> transportVegetableInfos) {
        this.transportVegetableInfos = transportVegetableInfos;
    }
    public String getTransportPeople() {
        return this.transportPeople;
    }
    public void setTransportPeople(String transportPeople) {
        this.transportPeople = transportPeople;
    }
    public String getTransportPeopleMobile() {
        return this.transportPeopleMobile;
    }
    public void setTransportPeopleMobile(String transportPeopleMobile) {
        this.transportPeopleMobile = transportPeopleMobile;
    }
    public String getTransportTime() {
        return this.transportTime;
    }
    public void setTransportTime(String transportTime) {
        this.transportTime = transportTime;
    }
    public String getTransportInfo() {
        return this.transportInfo;
    }
    public void setTransportInfo(String transportInfo) {
        this.transportInfo = transportInfo;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getMobile() {
        return this.mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getResidualIntegral() {
        return this.residualIntegral;
    }
    public void setResidualIntegral(String residualIntegral) {
        this.residualIntegral = residualIntegral;
    }
    public String getRelayBoxNo() {
        return this.relayBoxNo;
    }
    public void setRelayBoxNo(String relayBoxNo) {
        this.relayBoxNo = relayBoxNo;
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
