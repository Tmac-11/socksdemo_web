package com.example.socksdemo.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.socksdemo.model.FileDataVo;
import com.example.socksdemo.model.FlowDataVo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *随机生成密码
 */
public class PasswordUtil {

    public static  String RandomPassWord(){
        String password = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for(int i = 0; i < 8; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                password +=(char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                int r=random.nextInt(10);
                if(r==0){
                    r=random.nextInt(9)+1;
                }
                password += String.valueOf(r);
            }
        }
        return password.toUpperCase();

    }

    public static Map<String,Object> AnalyticalFile(){
        Map<String,Object> map=new HashMap<String,Object>();
        List<FileDataVo> list=new ArrayList<FileDataVo>();
        FileDataVo fileDataVo=null;
        File file=new File("D:\\LDMFILE\\"+"userFlow_"+ LocalDate.now()+".txt");
        if(!file.exists()){
           String msg="file not exist!";
           map.put("status",msg);
            return map;
        }
        StringBuilder result=new StringBuilder();
        try{
            BufferedReader br=new BufferedReader(new FileReader(file));
            String s=null;
            while ((s=br.readLine())!=null){
                result.append(System.lineSeparator()+s+",");
            }
            br.close();
        }catch (IOException e){
            e.getMessage();
            map.put("status","fial");
            return map;
        }
       String fileData= result.toString();
       String dataArr []= fileData.split("------------------------------");

        String ipaddr=null;
        String port=null;
        String traffic=null;
       for(int i=0;i<dataArr.length;i++){
           String dataString=dataArr[i];
          // System.out.println(dataString);
           Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}\\s*[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}");
           Matcher match = pattern.matcher(dataString);
           if(match.find()){
               String data=dataString.substring(match.start(),match.end());
              // System.out.println("日期："+data);
               String datalistString=dataString.substring(match.end());
               String datalist [] =datalistString.split(",");
               for(int j=0;j<datalist.length;j++ ){
                   String line=datalist[j];
//                   Pattern linePattern = Pattern.compile("ipaddr:.*(\\s*)");
//                   Matcher lineMatch = pattern.matcher(line);
                   if(line.contains("ipaddr")){
                      String field []=line.split("\t");
                      for (int k=0;k<field.length;k++){
                          if(field[k].contains(":")){
                              int index=field[k].lastIndexOf(":");
                              String field_x=field[k].substring(index+1);
                              if(field[k].contains("ipaddr")){
                                  ipaddr=field_x;
                              }else if(field[k].contains("port")){
                                  port=field_x;
                              }else if(field[k].contains("traffic")){
                                  traffic=field_x;
                              }
                          }else{
                              continue;
                          }
                      }
                       fileDataVo=new FileDataVo();
                       fileDataVo.setIpaddr(ipaddr);
                       fileDataVo.setPort(port);
                       fileDataVo.setTraffic(traffic);
                       fileDataVo.setTime(data);
                       list.add(fileDataVo);


                   }else{
                       continue;
                   }

               }

           }else{
               continue;
           }

       }

        map.put("status","success");
        map.put("list",list);
        return map;
    }


    public static   String test(String ip []){
        String msg="";
        for(int i=0;i<ip.length;i++){

            String str=ip[i];
            if(!str.equals("0")){
               msg= "no exist";
               continue;
            }
            msg="success";

        }


        return msg;
    }

    public static void main(String[] args) {

//        String queryUser="38.143.19.31/39517,38.143.19.31/42510,220.130.175.203/60236,";
//            String [] userString =queryUser.split(",");
//            List<FlowDataVo> list=new ArrayList<FlowDataVo>();
//            FlowDataVo flowDataVo=null;
//            for(int i=0;i<userString.length;i++){
//                String user= userString[i];
//                String [] info=user.split("/");
//                flowDataVo=new FlowDataVo();
//                flowDataVo.setUser_ip(info[0]);
//                flowDataVo.setUser_port(info[1]);
//                list.add(flowDataVo);
//
//            }

        String json="{01:276,03:276,08:388,09:208,12:1044,13:1468,16:6983451,21:789,23:1945}";


       JSONObject jsonObject= JSONObject.parseObject(json);
//        JSONObject.parse(json);
//        JSONObject.parseObject()

        BigDecimal flowStr=(BigDecimal)jsonObject.get(1);

        System.out.println(flowStr);

    }
}