package com.vegetablestrading.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ${王sir} on 2017/11/13.
 * application  配送蔬菜实体类
 */
@Entity
public class TransportVegetableInfo {
    public static final int TRANSPORT_MODE = 1;//配送实体类
    public static  final int SHOP_MODE = 2;//商城实体类

    @Id
    private Long id;
    @NotNull
    private int Type;//实体类的类型
    private String vegetableIconPath;//蔬菜图片
    private String vegetableName;//蔬菜名称
    private String weight;//配送重量
    private String vegetablePrice;//蔬菜单价
    private String vegetableInfo;//蔬菜介绍
    private String transportStartTime;//配送开始时间
    private String transportEndTime;//配送结束时间
    private String classify;//蔬菜所属类别
    private String webURL;//蔬菜对应二级界面（详情）
    private String vegetableId;//蔬菜对应ID
    @Generated(hash = 1243977677)
    public TransportVegetableInfo(Long id, int Type, String vegetableIconPath,
            String vegetableName, String weight, String vegetablePrice,
            String vegetableInfo, String transportStartTime,
            String transportEndTime, String classify, String webURL,
            String vegetableId) {
        this.id = id;
        this.Type = Type;
        this.vegetableIconPath = vegetableIconPath;
        this.vegetableName = vegetableName;
        this.weight = weight;
        this.vegetablePrice = vegetablePrice;
        this.vegetableInfo = vegetableInfo;
        this.transportStartTime = transportStartTime;
        this.transportEndTime = transportEndTime;
        this.classify = classify;
        this.webURL = webURL;
        this.vegetableId = vegetableId;
    }
    @Generated(hash = 1757651186)
    public TransportVegetableInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getType() {
        return this.Type;
    }
    public void setType(int Type) {
        this.Type = Type;
    }
    public String getVegetableIconPath() {
        return this.vegetableIconPath;
    }
    public void setVegetableIconPath(String vegetableIconPath) {
        this.vegetableIconPath = vegetableIconPath;
    }
    public String getVegetableName() {
        return this.vegetableName;
    }
    public void setVegetableName(String vegetableName) {
        this.vegetableName = vegetableName;
    }
    public String getWeight() {
        return this.weight;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
    public String getVegetablePrice() {
        return this.vegetablePrice;
    }
    public void setVegetablePrice(String vegetablePrice) {
        this.vegetablePrice = vegetablePrice;
    }
    public String getVegetableInfo() {
        return this.vegetableInfo;
    }
    public void setVegetableInfo(String vegetableInfo) {
        this.vegetableInfo = vegetableInfo;
    }
    public String getTransportStartTime() {
        return this.transportStartTime;
    }
    public void setTransportStartTime(String transportStartTime) {
        this.transportStartTime = transportStartTime;
    }
    public String getTransportEndTime() {
        return this.transportEndTime;
    }
    public void setTransportEndTime(String transportEndTime) {
        this.transportEndTime = transportEndTime;
    }
    public String getClassify() {
        return this.classify;
    }
    public void setClassify(String classify) {
        this.classify = classify;
    }
    public String getWebURL() {
        return this.webURL;
    }
    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }
    public String getVegetableId() {
        return this.vegetableId;
    }
    public void setVegetableId(String vegetableId) {
        this.vegetableId = vegetableId;
    }
}
