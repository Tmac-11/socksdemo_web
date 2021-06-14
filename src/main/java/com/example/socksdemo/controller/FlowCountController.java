package com.example.socksdemo.controller;


import com.example.socksdemo.model.DeviceVo;
import com.example.socksdemo.service.FlowDataService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/flowController")
public class FlowCountController {

    private static Log log= LogFactory.getLog(FlowCountController.class);
    @Autowired
    FlowDataService flowDataService;

    @Value("${textfile}")
    private  String fileLocation;


    @RequestMapping(value = "/uploadData", method= RequestMethod.POST)
    @ResponseBody
    public  String  upload(HttpServletRequest request){
        try {
        MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest)request;
        MultipartFile file= multipartHttpServletRequest.getFile("file");

        if(file.isEmpty()){

            return "file is null";
        }

            String path=fileLocation;
           //File newfile= new File(path+"userFlow_"+ LocalDate.now()+".txt") ;
           File newfile= new File(path+file.getOriginalFilename()) ;
           file.transferTo(newfile);

        }catch (IOException e){
            log.info("上传文件错误："+e.getMessage());
            return "upload file fail："+ e.getMessage();
        }

        return "upload file success";

    }

    @RequestMapping(value = "/manualData", method= RequestMethod.POST)
    public String  insert(String fileDate){

     for(int i=0;i<24;i++){
         try{
             String msg=flowDataService.dataProcess(fileDate.trim()+"_"+i);
             log.info(fileDate + "每小时解析数据信息：" + msg);
         }catch (Exception e){
             log.error(fileDate + " 每小时解析数据错误：" + e.getMessage());
         }

     }

        return "redirect:/flowController/flowHml";
    }


    @RequestMapping(value = "/countFlow", method= RequestMethod.POST)
    @ResponseBody
    public String  countFlow(String countDate){
        String msg=null;
        try{
            msg=flowDataService.dataByDate(countDate.trim());
            log.info(countDate+" 计算用户流量信息："+msg);
        }catch (Exception e){
            msg=countDate+" 计算用户流量错误："+e.getMessage();
            log.error(countDate+" 计算用户流量错误："+e.getMessage());
        }

        //String return_mag="{\"msg\":"+msg+"}";
         return msg;

    }


    @RequestMapping("/flowHml")
    public String flowHml(){

        return "flowCount";
    }

    @RequestMapping(value = "/queryData", method= RequestMethod.POST)
    @ResponseBody
    public Integer  queryData(String startdate,String enddate,String select_table){
      Integer num=  flowDataService.queryTable(startdate.trim(), enddate.trim(),select_table);
        return num;

    }


    @RequestMapping(value = "/delData", method= RequestMethod.POST)
    @ResponseBody
    public Integer  delData(String startdate,String enddate,String select_table){
        Integer num=  flowDataService.delTable(startdate.trim(), enddate.trim(),select_table);
        return num;

    }


}
