package com.example.socksdemo.controller;

import com.example.socksdemo.model.ResourceVo;
import com.example.socksdemo.model.UserInfo;
import com.example.socksdemo.service.ResourceService;
import com.example.socksdemo.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/resourceController")
public class ResourceController {

    @Autowired
    ResourceService resourceService;

    @RequestMapping(value="/resourceEdit")
    public String editHtml(@ModelAttribute UserInfo userinfo, Model model){
        model.addAttribute("user",userinfo);
        return "resourceEdit";
    }

    @RequestMapping(value = "/antoResource")
    @ResponseBody
    public UserInfo antoResource(){

        Map<String,String> map=resourceService.RandomIp();
        String password= PasswordUtil.RandomPassWord();
        UserInfo userInfo=new UserInfo();
        userInfo.setUser_password(password);
        userInfo.setResource_ip(map.get("ip"));
        userInfo.setResource_port(map.get("port"));
        userInfo.setResource_domain_name(map.get("domainName"));
        return userInfo;
    }


    @RequestMapping(value = "/apecifiedResource")
    @ResponseBody
    public UserInfo  apecifiedResource(String deviceName){

        Map<String,String> map=resourceService.apecifiedIp(deviceName);
        String password= PasswordUtil.RandomPassWord();
        UserInfo userInfo=new UserInfo();
        userInfo.setUser_password(password);
        userInfo.setResource_ip(map.get("ip"));
        userInfo.setResource_port(map.get("port"));
        userInfo.setResource_domain_name(map.get("domainName"));
        return userInfo;
    }

}
