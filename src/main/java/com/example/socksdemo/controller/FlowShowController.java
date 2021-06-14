package com.example.socksdemo.controller;

import com.alibaba.fastjson.JSON;
import com.example.socksdemo.model.*;
import com.example.socksdemo.service.DeviceService;
import com.example.socksdemo.service.FlowShowService;
import com.example.socksdemo.service.ResourceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/flowShowController")
public class FlowShowController {
    private static Log log= LogFactory.getLog(FlowShowController.class);

    @Autowired
    FlowShowService flowShowService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    ResourceService resourceService;

    @RequestMapping(value = "/userFlow")
    public String  userFlow(Model model){
        model.addAttribute("DeviceList",deviceService.DeviceInfo());
        return "userFlow";
    }

    @RequestMapping(value = "/deviceFlow")
    public String deviceFlow(Model model){
        model.addAttribute("DeviceList",deviceService.DeviceInfo());
        return "deviceFlow";
    }


    @RequestMapping(value = "/deviceListFlow", method= RequestMethod.POST)
    @ResponseBody
    public String  deviceListFlow(String startmonth,String endmonth,String deviceName,String type){
        //System.out.println(deviceName);
        FlowShowVo flowShowVo =null;
        String [] ipArr=deviceName.split(",");
        if("month".equals(type)){
            flowShowVo= flowShowService.deviceMonthData(startmonth,endmonth,ipArr);
        }else if("date".equals(type)){
            flowShowVo= flowShowService.deviceDateData(startmonth,endmonth,ipArr);
        }
        String jsondata= JSON.toJSONString(flowShowVo);
        System.out.println(jsondata);
        return jsondata;

    }

    @RequestMapping(value = "/userListFlow", method= RequestMethod.POST)
    @ResponseBody
    public String  userListFlow(String start,String end,String queryUser,String queryUserShow,String type){

//        System.out.println(start+","+end);
//        System.out.println(queryUser);
//        System.out.println(queryUserShow);
//        System.out.println(type);
        FlowShowVo flowShowVo =null;
        if("month".equals(type)){
            flowShowVo= flowShowService.userMonthData(start,end,queryUser,queryUserShow);
        }else if("date".equals(type)){
            flowShowVo=flowShowService.userDateData(start,end,queryUser,queryUserShow);
        }else if("hour".equals(type)){
            flowShowVo=flowShowService.userHourData(start,end,queryUser,queryUserShow);
        }
        String jsondata= JSON.toJSONString(flowShowVo);
        System.out.println(jsondata);
        return jsondata;

    }

    @RequestMapping(value = "/userList", method= RequestMethod.POST)
    @ResponseBody
    public String  userList(String deviceName){
        System.out.println(deviceName);
        List<UserInfo> list=resourceService.getUserList(deviceName);
        String jsondata= JSON.toJSONString(list);
        return jsondata;

    }




    @RequestMapping(value = "/test")
    public String  test(){

        return "test";
    }
}
