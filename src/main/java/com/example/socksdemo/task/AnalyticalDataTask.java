package com.example.socksdemo.task;

import com.example.socksdemo.service.FlowDataService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

//@Component
public class AnalyticalDataTask {

    private static Log log= LogFactory.getLog(AnalyticalDataTask.class);

   // private static  Integer count=0;
   // @Autowired
    FlowDataService flowDataService;

//    /**
//     * 解析每小时数据
//     */
//    @Scheduled(cron ="${task.processingData}")
//    public void processingData(){
//      String now_date= DateFromatt();
//
//
//      //String date="2020_1_29_";
//      // String now_date=date+count.toString();
//      try{
//          String msg=  flowDataService.dataProcess(now_date);
//          log.info(now_date+ "每小时解析数据信息："+msg);
//      }catch (Exception e){
//          log.error(now_date+" 每小时解析数据错误："+e.getMessage());
//      }
//
////             count++;
////      if(count>=24){
////          count=0;
////      }
//    }
    /**
     * 解析前一天每小时数据
     */
    //@Scheduled(cron ="${task.processingData}")
    public void processingData(){
        String now_date=DateFile();
        for(int i=0;i<24;i++) {
            try {
                String msg = flowDataService.dataProcess(now_date+i);
                log.info(now_date + "每小时解析数据信息：" + msg);
            } catch (Exception e) {
                log.error(now_date + " 每小时解析数据错误：" + e.getMessage());
            }
        }

    }
    /**
     *　计算前一天用户浏量
     *
     */
   // @Scheduled(cron ="${task.Countflowdata}")
    public void Countflowdata(){
        LocalDate localDate=LocalDate.now().plusDays(-1);
        try{
            String msg=flowDataService.dataByDate(localDate.toString());
            log.info(localDate.toString()+" 计算用户流量信息："+msg);
        }catch (Exception e){
            log.error(localDate.toString()+" 计算用户流量错误："+e.getMessage());
        }

    }


    public String DateFromatt(){
        LocalDateTime localDateTime= LocalDateTime.now().plusHours(-1);
     String now_date;
     String year=Integer.toString(localDateTime.getYear()) ;
     String month=Integer.toString( localDateTime.getMonthValue());
     String day=Integer.toString(localDateTime.getDayOfMonth());
     String hour=Integer.toString(localDateTime.getHour());
      now_date=year+"_"+month+"_"+day+"_"+hour;

        return now_date;
    }

    public String DateFile(){
        LocalDate localDate=LocalDate.now().plusDays(-1);
        String now_date;
        String year=Integer.toString(localDate.getYear()) ;
        String month=Integer.toString( localDate.getMonthValue());
        String day=Integer.toString(localDate.getDayOfMonth());
        now_date=year+"_"+month+"_"+day+"_";

        return now_date;
    }

}
