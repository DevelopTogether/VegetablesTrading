package com.vegetablestrading.bean;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:wang_sir
 * Time:2017/12/4 11:22
 * Description:This is B_Converter
 */
public class Logistics_converter implements PropertyConverter<List<LogisticsInfo>, String> {
    @Override
    public List<LogisticsInfo> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null|| TextUtils.isEmpty(databaseValue)) {
            return null;
        }
//        List<String> list_str = Arrays.asList(databaseValue.split("},"));
       String[] list_str = databaseValue.split("\\},");
        List<LogisticsInfo> list_transport = new ArrayList<>();
        for (String s : list_str) {
            s+="}";
            list_transport.add(new Gson().fromJson(s, LogisticsInfo.class));
        }
        return list_transport;
    }

    @Override
    public String convertToDatabaseValue(List<LogisticsInfo> arrays) {
        if (arrays == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (LogisticsInfo array : arrays) {
                String str = new Gson().toJson(array);
                sb.append(str);
                sb.append(",");
            }
            return sb.toString();

        }
    }
}
