package com.example.socksdemo.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateFormatUtill {


    public static List<String> monthDate(String start,String end){
     // 开始时间// 结束时间
        List<String> list = new ArrayList<String>();

        try {
            Date startDate = new SimpleDateFormat("yyyy-MM").parse(start);
            Date endDate = new SimpleDateFormat("yyyy-MM").parse(end);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            // 获取开始年份和开始月份
            int startYear = calendar.get(Calendar.YEAR);
            int startMonth = calendar.get(Calendar.MONTH);
            // 获取结束年份和结束月份
            calendar.setTime(endDate);
            int endYear = calendar.get(Calendar.YEAR);
            int endMonth = calendar.get(Calendar.MONTH);
            //

            for (int i = startYear; i <= endYear; i++) {
                String date = "";
                if (startYear == endYear) {
                    for (int j = startMonth; j <= endMonth; j++) {
                        if (j < 9) {
                            date = i + "-0" + (j + 1);
                        } else {
                            date = i + "-" + (j + 1);
                        }
                        list.add(date);
                    }

                } else {
                    if (i == startYear) {
                        for (int j = startMonth; j < 12; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            list.add(date);
                        }
                    } else if (i == endYear) {
                        for (int j = 0; j <= endMonth; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            list.add(date);
                        }
                    } else {
                        for (int j = 0; j < 12; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            list.add(date);
                        }
                    }

                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
     return list;

    }


    /**
     * 获取两个日期字符串之间的日期集合
     * @param start:String
     * @param end:String
     * @return list:yyyy-MM-dd
     */
    public static List<String> getBetweenDate(String start,String end){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(start);
            Date endDate = sdf.parse(end);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (endDate.getTime()>=startDate.getTime()){
                // 把日期添加到集合
                list.add(sdf.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                //把日期减一天
                calendar.add(Calendar.DATE, 1);
                // 获取增加后的日期
                startDate=calendar.getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        List<String> list=getBetweenDate("2020-01-01","2020-02-06");
        System.out.println(list.toString());
    }

}
