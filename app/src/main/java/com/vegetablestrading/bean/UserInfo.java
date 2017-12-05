package com.vegetablestrading.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Author:wang_sir
 * Time:2017/11/29 13:09
 * Description:This is UserInfo  用户的信息
 */
@Entity
public class UserInfo {
    @Id
    private Long id;
    @NotNull
    private String userId;//用户Id
    private String userName;//用户名
    private String userPhone;//用户手机号
    private String petType;//会员类型
    private String petStatus;//会员状态
    private String petSum;//会费
    private String deposit;//押金
    private String expirationTime;//会员过期时间
    private String residualIntegral;//剩余积分
    private String residualPickAmount;//剩余采摘次数
    private String boxNo;//中转箱编号
    private String registDate;//注册日期
    private String userEmail;//用户邮箱
    private String userAddr;//用户地址
    private String refundAccountName;//退款账户名称（微信，支付宝）
    private String refundAccount;//退款账户
    @Generated(hash = 1656927996)
    public UserInfo(Long id, @NotNull String userId, String userName,
            String userPhone, String petType, String petStatus, String petSum,
            String deposit, String expirationTime, String residualIntegral,
            String residualPickAmount, String boxNo, String registDate,
            String userEmail, String userAddr, String refundAccountName,
            String refundAccount) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.petType = petType;
        this.petStatus = petStatus;
        this.petSum = petSum;
        this.deposit = deposit;
        this.expirationTime = expirationTime;
        this.residualIntegral = residualIntegral;
        this.residualPickAmount = residualPickAmount;
        this.boxNo = boxNo;
        this.registDate = registDate;
        this.userEmail = userEmail;
        this.userAddr = userAddr;
        this.refundAccountName = refundAccountName;
        this.refundAccount = refundAccount;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPhone() {
        return this.userPhone;
    }
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public String getPetType() {
        return this.petType;
    }
    public void setPetType(String petType) {
        this.petType = petType;
    }
    public String getPetStatus() {
        return this.petStatus;
    }
    public void setPetStatus(String petStatus) {
        this.petStatus = petStatus;
    }
    public String getPetSum() {
        return this.petSum;
    }
    public void setPetSum(String petSum) {
        this.petSum = petSum;
    }
    public String getDeposit() {
        return this.deposit;
    }
    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }
    public String getExpirationTime() {
        return this.expirationTime;
    }
    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }
    public String getResidualIntegral() {
        return this.residualIntegral;
    }
    public void setResidualIntegral(String residualIntegral) {
        this.residualIntegral = residualIntegral;
    }
    public String getResidualPickAmount() {
        return this.residualPickAmount;
    }
    public void setResidualPickAmount(String residualPickAmount) {
        this.residualPickAmount = residualPickAmount;
    }
    public String getBoxNo() {
        return this.boxNo;
    }
    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }
    public String getRegistDate() {
        return this.registDate;
    }
    public void setRegistDate(String registDate) {
        this.registDate = registDate;
    }
    public String getUserEmail() {
        return this.userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserAddr() {
        return this.userAddr;
    }
    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }
    public String getRefundAccountName() {
        return this.refundAccountName;
    }
    public void setRefundAccountName(String refundAccountName) {
        this.refundAccountName = refundAccountName;
    }
    public String getRefundAccount() {
        return this.refundAccount;
    }
    public void setRefundAccount(String refundAccount) {
        this.refundAccount = refundAccount;
    }


}
