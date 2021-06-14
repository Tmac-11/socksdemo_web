package com.example.socksdemo.controller;


import com.example.socksdemo.model.DeviceVo;
import com.example.socksdemo.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/deviceController")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @RequestMapping(value = "/edit")
    public  String  edit(@ModelAttribute DeviceVo device,Model model){
       //System.out.println(device.getDevice_state());
       model.addAttribute("device",device);
        return "deviceEdit";

    }

    @RequestMapping(value = "/updateDevice")
    public String updateDevice(@ModelAttribute DeviceVo device,Model model){
        deviceService.updInfo(device);
        return "redirect:/indexController/index";
    }

    @RequestMapping(value = "/delete")
    public String delete( Integer device_id ,Model model){

        deviceService.delInfo(device_id);
        return "redirect:/indexController/index";
    }

    @RequestMapping(value = "/addDeviceHtml")
    public String addHtml(){

        return  "addDevice";
    }


    @RequestMapping(value = "/addDevice")
    public String addDevice(@ModelAttribute DeviceVo device){
        deviceService.addDevice(device);
        return "redirect:/indexController/index";
    }


    @RequestMapping(value = "/effectiveDevice")
    @ResponseBody
    public List<DeviceVo> effectiveDevice(){
        return  deviceService.Devicelist();
    }

    @RequestMapping(value = "/deviceCount")
    @ResponseBody
    public Integer deviceCount(String ipStr){
       // System.out.println(ipStr);
        String str[]=ipStr.split("/");
        Integer count=deviceService.DeviceCount(str[0]);
        //System.out.println("count:"+count);
        return count;
    }

}
