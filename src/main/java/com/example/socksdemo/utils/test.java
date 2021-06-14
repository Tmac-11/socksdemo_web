package com.example.socksdemo.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class test {



    public void test(){
        System.out.println("1..");
        System.out.println("2..");
        System.out.println("3..");
    }

    public static void main(String[] args) {
        test test=new test();
        test.test();
//        String y1 = "2020-01";// 开始时间
//        String y2 = "2020-03";// 结束时间
//
//        try {
//            Date startDate = new SimpleDateFormat("yyyy-MM").parse(y1);
//            Date endDate = new SimpleDateFormat("yyyy-MM").parse(y2);
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(startDate);
//            // 获取开始年份和开始月份
//            int startYear = calendar.get(Calendar.YEAR);
//            int startMonth = calendar.get(Calendar.MONTH);
//            // 获取结束年份和结束月份
//            calendar.setTime(endDate);
//            int endYear = calendar.get(Calendar.YEAR);
//            int endMonth = calendar.get(Calendar.MONTH);
//            //
//            List<String> list = new ArrayList<String>();
//            for (int i = startYear; i <= endYear; i++) {
//                String date = "";
//                if (startYear == endYear) {
//                    for (int j = startMonth; j <= endMonth; j++) {
//                        if (j < 9) {
//                            date = i + "-0" + (j + 1);
//                        } else {
//                            date = i + "-" + (j + 1);
//                        }
//                        list.add(date);
//                    }
//
//                } else {
//                    if (i == startYear) {
//                        for (int j = startMonth; j < 12; j++) {
//                            if (j < 9) {
//                                date = i + "-0" + (j + 1);
//                            } else {
//                                date = i + "-" + (j + 1);
//                            }
//                            list.add(date);
//                        }
//                    } else if (i == endYear) {
//                        for (int j = 0; j <= endMonth; j++) {
//                            if (j < 9) {
//                                date = i + "-0" + (j + 1);
//                            } else {
//                                date = i + "-" + (j + 1);
//                            }
//                            list.add(date);
//                        }
//                    } else {
//                        for (int j = 0; j < 12; j++) {
//                            if (j < 9) {
//                                date = i + "-0" + (j + 1);
//                            } else {
//                                date = i + "-" + (j + 1);
//                            }
//                            list.add(date);
//                        }
//                    }
//
//                }
//
//            }
//
//            // 所有的月份已经准备好
//            //System.out.println(list);
//            for(int i = 0;i < list.size();i++){
//                System.out.println(list.get(i));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//

    }



}
