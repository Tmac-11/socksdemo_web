package com.example.socksdemo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.example.socksdemo.mapper.ResourceMapper;
import com.example.socksdemo.mapper.UserFlowMapper;
import com.example.socksdemo.model.*;
import com.example.socksdemo.service.FlowDataService;
import com.example.socksdemo.service.FlowShowService;
import com.example.socksdemo.utils.DateFormatUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FlowShowServiceImpl implements FlowShowService{

    @Autowired
    UserFlowMapper userFlowMapper;
    @Autowired
    ResourceMapper resourceMapper;
    @Override
    public FlowShowVo deviceMonthData(String startmonth, String endmonth,String [] ipArr) {
        List<String>  monthList= DateFormatUtill.monthDate(startmonth,endmonth);
        String [] xAxisArr=new String[monthList.size()];
        List<DeviceFlowShowVo> devicelist=new ArrayList<DeviceFlowShowVo>();
        DeviceFlowShowVo deviceFlowShowVo=null;
       // String ip [] =resourceMapper.ResourceEffective();
        String ip [] =ipArr;
        String jsonFlow="";


        for(int i=0;i<ip.length;i++){
        String [] flowArr=null;
         String json="{";
         List<DeviceFlowVo>  list=  userFlowMapper.deviceMonthData(startmonth,endmonth,ip[i]);
         if(list.size()!=0){
             for(DeviceFlowVo deviceFlowVo :list){
                 BigDecimal flow=deviceFlowVo.getFlowCount();
                 BigDecimal bigDecimal=new BigDecimal(1024);
                 // 1byte=1/1024/1024 MB
                 flow=flow.divide(bigDecimal).divide(bigDecimal);
                 //保留2位小数
                 flow=flow.setScale(2,BigDecimal.ROUND_UP);
                 json=json+"\""+deviceFlowVo.getDataMonth()+"\""+":"+flow+",";
             }
             json=json.substring(0,json.length()-1)+"}";
             System.out.println("ip"+ip[i]+" json"+json);
             JSONObject jsonObject= JSONObject.parseObject(json);
             for(int j=0;j<monthList.size();j++){

                 String count= String.valueOf(jsonObject.get(monthList.get(j)));
                 if(count.equals("null")){
                     count="0";
                 }
                 jsonFlow=jsonFlow+count+",";
                 xAxisArr[j]=monthList.get(j);
             }
             jsonFlow=jsonFlow.substring(0,jsonFlow.length()-1);
             flowArr=jsonFlow.split(",");

        }else{
             flowArr=new String[monthList.size()];
            for(int k=0;k<monthList.size();k++){
                xAxisArr[k]=monthList.get(k);
                flowArr[k]="0";
            }
         }

            deviceFlowShowVo=new DeviceFlowShowVo();
            deviceFlowShowVo.setIpName(ip[i]);
            deviceFlowShowVo.setJsonArr(flowArr);
            devicelist.add(deviceFlowShowVo);
        }

        FlowShowVo flowShowVo=new FlowShowVo();
        flowShowVo.setxAxisArr(xAxisArr);
        flowShowVo.setObjectList(devicelist);

        return flowShowVo;
    }

    @Override
    public FlowShowVo deviceDateData(String startmonth, String endmonth, String[] ipArr) {
        List<String> dateList=DateFormatUtill.getBetweenDate(startmonth,endmonth);
        String [] xAxisArr=new String[dateList.size()];
        List<DeviceFlowShowVo> devicelist=new ArrayList<DeviceFlowShowVo>();
        DeviceFlowShowVo deviceFlowShowVo=null;
        // String ip [] =resourceMapper.ResourceEffective();
        String ip [] =ipArr;
        String jsonFlow="";


        for(int i=0;i<ip.length;i++){
            String [] flowArr=null;
            String json="{";
            List<DeviceFlowVo>  list=  userFlowMapper.deviceDateData(startmonth,endmonth,ip[i]);
            if(list.size()!=0){
                for(DeviceFlowVo deviceFlowVo :list){
                    BigDecimal flow=deviceFlowVo.getFlowCount();
                    BigDecimal bigDecimal=new BigDecimal(1024);
                    // 1byte=1/1024/1024 MB
                    flow=flow.divide(bigDecimal).divide(bigDecimal);
                    //保留2位小数
                    flow=flow.setScale(2,BigDecimal.ROUND_UP);
                    json=json+"\""+deviceFlowVo.getDataMonth()+"\""+":"+flow+",";
                }
                json=json.substring(0,json.length()-1)+"}";
                System.out.println("ip"+ip[i]+" json"+json);
                JSONObject jsonObject= JSONObject.parseObject(json);
                for(int j=0;j<dateList.size();j++){

                    String count= String.valueOf(jsonObject.get(dateList.get(j)));
                    if(count.equals("null")){
                        count="0";
                    }
                    jsonFlow=jsonFlow+count+",";
                    xAxisArr[j]=dateList.get(j);
                }
                jsonFlow=jsonFlow.substring(0,jsonFlow.length()-1);
                flowArr=jsonFlow.split(",");

            }else{
                flowArr=new String[dateList.size()];
                for(int k=0;k<dateList.size();k++){
                    xAxisArr[k]=dateList.get(k);
                    flowArr[k]="0";
                }
            }

            deviceFlowShowVo=new DeviceFlowShowVo();
            deviceFlowShowVo.setIpName(ip[i]);
            deviceFlowShowVo.setJsonArr(flowArr);
            devicelist.add(deviceFlowShowVo);

        }

        FlowShowVo flowShowVo=new FlowShowVo();
        flowShowVo.setxAxisArr(xAxisArr);
        flowShowVo.setObjectList(devicelist);
        return flowShowVo;
    }

    @Override
    public FlowShowVo userMonthData(String startmonth, String endmonth, String queryUser,String queryUserShow) {
        List<FlowDataVo> queryList= getQueryUser(queryUser,queryUserShow);
        List<String> monthList=DateFormatUtill.monthDate(startmonth,endmonth);
        String [] flowArr=null;
        String [] xAxisArr=new String[monthList.size()];
        List<UserFlowShowVo> userList=new ArrayList<UserFlowShowVo>();
        UserFlowShowVo userFlowShowVo=null;
        String user_name=null;

        for(int i=0;i<queryList.size();i++){
            String jsonFlow="";
            String json="{";
            FlowDataVo flowDataVo=queryList.get(i);
            user_name=flowDataVo.getUser_name();
            List<DeviceFlowVo>  list=userFlowMapper.userMonthData(startmonth,endmonth,flowDataVo.getUser_ip(),flowDataVo.getUser_port());
            if(list.size()!=0){
                for(DeviceFlowVo deviceFlowVo :list){
                    BigDecimal flow=deviceFlowVo.getFlowCount();
                    BigDecimal bigDecimal=new BigDecimal(1024);
                    // 1byte=1/1024/1024 MB
                    flow=flow.divide(bigDecimal).divide(bigDecimal);
                    //保留2位小数
                    flow=flow.setScale(2,BigDecimal.ROUND_UP);
                    json=json+"\""+deviceFlowVo.getDataMonth()+"\""+":"+flow+",";
                }

                json=json.substring(0,json.length()-1)+"}";
                System.out.println("ip:"+flowDataVo.getUser_ip()+" port:"+flowDataVo.getUser_port()+" json:"+json);
                JSONObject jsonObject= JSONObject.parseObject(json);
                for(int j=0;j<monthList.size();j++){

                    String count= String.valueOf(jsonObject.get(monthList.get(j)));
                    if(count.equals("null")){
                        count="0";
                    }
                    jsonFlow=jsonFlow+count+",";
                    xAxisArr[j]=monthList.get(j);
                }
                jsonFlow=jsonFlow.substring(0,jsonFlow.length()-1);
                flowArr=jsonFlow.split(",");
            }else{
                flowArr=new String[monthList.size()];
                for(int k=0;k<monthList.size();k++){
                    flowArr[k]="0";
                    xAxisArr[k]=monthList.get(k);
                }

            }

            userFlowShowVo=new UserFlowShowVo();
            userFlowShowVo.setUserName(user_name);
            userFlowShowVo.setJsonArr(flowArr);
            userList.add(userFlowShowVo);
        }

        FlowShowVo flowShowVo=new FlowShowVo();
        flowShowVo.setObjectList(userList);
        flowShowVo.setxAxisArr(xAxisArr);

        return flowShowVo;
    }

    @Override
    public FlowShowVo userDateData(String startmonth, String endmonth, String queryUser,String queryUserShow) {
        List<String> dateList=DateFormatUtill.getBetweenDate(startmonth,endmonth);
        List<FlowDataVo> queryList= getQueryUser(queryUser,queryUserShow);

        String [] flowArr=null;
        String [] xAxisArr=new String[dateList.size()];
        List<UserFlowShowVo> userList=new ArrayList<UserFlowShowVo>();
        UserFlowShowVo userFlowShowVo=null;
        String user_name=null;

        for(int i=0;i<queryList.size();i++){
            {
                String jsonFlow="";
                String json="{";
                FlowDataVo flowDataVo=queryList.get(i);
                user_name=flowDataVo.getUser_name();
                List<DeviceFlowVo>  list=userFlowMapper.userDateData(startmonth,endmonth,flowDataVo.getUser_ip(),flowDataVo.getUser_port());
                if(list.size()!=0){
                    for(DeviceFlowVo deviceFlowVo :list){
                        BigDecimal flow=deviceFlowVo.getFlowCount();
                        BigDecimal bigDecimal=new BigDecimal(1024);
                        // 1byte=1/1024/1024 MB
                        flow=flow.divide(bigDecimal).divide(bigDecimal);
                        //保留2位小数
                        flow=flow.setScale(2,BigDecimal.ROUND_UP);
                        json=json+"\""+deviceFlowVo.getDataMonth()+"\""+":"+flow+",";
                    }

                    json=json.substring(0,json.length()-1)+"}";
                    System.out.println("ip:"+flowDataVo.getUser_ip()+" port:"+flowDataVo.getUser_port()+" json:"+json);
                    JSONObject jsonObject= JSONObject.parseObject(json);
                    for(int j=0;j<dateList.size();j++){

                        String count= String.valueOf(jsonObject.get(dateList.get(j)));
                        if(count.equals("null")){
                            count="0";
                        }
                        jsonFlow=jsonFlow+count+",";
                        xAxisArr[j]=dateList.get(j);
                    }
                    jsonFlow=jsonFlow.substring(0,jsonFlow.length()-1);
                    flowArr=jsonFlow.split(",");
                }else{
                    flowArr=new String[dateList.size()];
                    for(int k=0;k<dateList.size();k++){
                        flowArr[k]="0";
                        xAxisArr[k]=dateList.get(k);
                    }

                }

                userFlowShowVo=new UserFlowShowVo();
                userFlowShowVo.setUserName(user_name);
                userFlowShowVo.setJsonArr(flowArr);
                userList.add(userFlowShowVo);
            }
        }

        FlowShowVo flowShowVo=new FlowShowVo();
        flowShowVo.setObjectList(userList);
        flowShowVo.setxAxisArr(xAxisArr);

        return flowShowVo;
    }


    @Override
    public FlowShowVo userHourData(String startmonth, String endmonth, String queryUser, String queryUserShow) {
        List<FlowDataVo> queryList=getQueryUser(queryUser,queryUserShow);
        String [] xAxisArr=new String[24];
        String [] flowArr=null;
        List<UserFlowShowVo> userList=new ArrayList<UserFlowShowVo>();
        UserFlowShowVo userFlowShowVo=null;
        String user_name=null;
        for(int i=0;i<queryList.size();i++){
            flowArr=new String[24];
            FlowDataVo flowDataVo=queryList.get(i);
            user_name=flowDataVo.getUser_name();
            DeviceFlowVo deviceFlowVo =userFlowMapper.userHourData(startmonth,endmonth,flowDataVo.getUser_ip(),flowDataVo.getUser_port());

            if(deviceFlowVo!=null){
            JSONObject jsonObject= JSONObject.parseObject("{"+deviceFlowVo.getFlowJson()+"}");
                for(int j=0;j<24;j++){
                   Integer num=j;
                    BigDecimal flowStr=(BigDecimal)jsonObject.get(num);
                    //String flowStr=(String)jsonObject.get(num);
                    String str="";
                    if(flowStr==null){
                        str="0";
                    }else{
                            //BigDecimal flow=new BigDecimal(Float.valueOf(flowStr));
                            BigDecimal bigDecimal=new BigDecimal(1024);
                            // 1byte=1/1024/1024 MB
                            flowStr=flowStr.divide(bigDecimal).divide(bigDecimal);
                            //保留2位小数
                            flowStr=flowStr.setScale(2,BigDecimal.ROUND_UP);
                            str=flowStr.toString();

                    }
                    flowArr[j]=str;
                    xAxisArr[j]=String.valueOf(j);


                }

            }else{
                for(int k=0;k<24;k++){
                    flowArr[k]="0";
                    xAxisArr[k]=String.valueOf(k);
                }


            }

            userFlowShowVo=new UserFlowShowVo();
            userFlowShowVo.setUserName(user_name);
            userFlowShowVo.setJsonArr(flowArr);
            userList.add(userFlowShowVo);
        }

        FlowShowVo flowShowVo=new FlowShowVo();
        flowShowVo.setObjectList(userList);
        flowShowVo.setxAxisArr(xAxisArr);
        return flowShowVo;
    }

    private List<FlowDataVo> getQueryUser(String queryUser, String queryUserShow){
        //38.143.19.31/39517,38.143.19.31/42510,220.130.175.203/60236,
        String [] userString =queryUser.split(",");
        String [] userShowString =queryUserShow.split("；");
        List<FlowDataVo> list=new ArrayList<FlowDataVo>();
        FlowDataVo flowDataVo=null;
        for(int i=0;i<userString.length;i++){
           String user= userString[i];
           String [] info=user.split("/");
            String userShow= userShowString[i];
            String [] infoShow=userShow.split("/");
            flowDataVo=new FlowDataVo();
            flowDataVo.setUser_ip(info[0]);
            flowDataVo.setUser_port(info[1]);
            flowDataVo.setUser_name(infoShow[1]);
            list.add(flowDataVo);

        }

        return  list;
    }

    private String jsonFromate(String flowjson){


        return  null;
    }
}
