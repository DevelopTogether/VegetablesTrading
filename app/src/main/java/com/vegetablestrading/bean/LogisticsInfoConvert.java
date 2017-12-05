package com.vegetablestrading.bean;

import com.google.gson.Gson;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author:wang_sir
 * Time:2017/12/4 17:15
 * Description:This is LogisticsInfoConvert
 */
public class LogisticsInfoConvert implements PropertyConverter<List<LogisticsInfo>, String> {



        @Override
        public List<LogisticsInfo> convertToEntityProperty(String databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            List<String> list_str = Arrays.asList(databaseValue.split(","));
            List<LogisticsInfo> list_transport = new ArrayList<>();
            for (String s : list_str) {
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

