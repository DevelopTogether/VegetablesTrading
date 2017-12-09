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
    private String userType;//会员类型 1==N蓝卡，2==P银卡，3==VIP金卡
    private String userStatus;//会员状态
    private String dues;//会费
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
    @Generated(hash = 514566146)
    public UserInfo(Long id, @NotNull String userId, String userName,
            String userPhone, String userType, String userStatus, String dues,
            String deposit, String expirationTime, String residualIntegral,
            String residualPickAmount, String boxNo, String registDate,
            String userEmail, String userAddr, String refundAccountName,
            String refundAccount) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userType = userType;
        this.userStatus = userStatus;
        this.dues = dues;
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
    public String getUserType() {
        return this.userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getUserStatus() {
        return this.userStatus;
    }
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
    public String getDues() {
        return this.dues;
    }
    public void setDues(String dues) {
        this.dues = dues;
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
