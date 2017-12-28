package com.vegetablestrading.bean;

import android.support.annotation.NonNull;

/**
 * Author:wang_sir
 * Time:2017/12/26 13:19
 * Description:This is AddressInfo 地址信息的实体类
 */
public class AddressInfo implements Comparable<AddressInfo> {

    private String accepter;//收货人
    private String accepterPhone;//收货人电话
    private String addressId;//地址ID
    private String address;//地址信息
    private String isDefault;//是否是默认地址  1=> 是默认地址 2=> 不是默认地址
    private String layoutTag ;

    public String getAccepter() {
        return accepter;
    }

    public void setAccepter(String accepter) {
        this.accepter = accepter;
    }

    public String getAccepterPhone() {
        return accepterPhone;
    }

    public void setAccepterPhone(String accepterPhone) {
        this.accepterPhone = accepterPhone;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getLayoutTag() {
        return layoutTag;
    }

    public void setLayoutTag(String layoutTag) {
        this.layoutTag = layoutTag;
    }

    @Override
    public int compareTo(@NonNull AddressInfo addressInfo) {

        int a = Integer.parseInt(this.getIsDefault()) - Integer.parseInt(addressInfo.getIsDefault());
        return a;

    }
}
