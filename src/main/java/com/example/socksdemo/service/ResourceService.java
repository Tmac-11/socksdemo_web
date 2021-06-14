package com.example.socksdemo.service;

import com.example.socksdemo.model.BaseResult;
import com.example.socksdemo.model.ResUpdParam;
import com.example.socksdemo.model.ResourceVo;
import com.example.socksdemo.model.UserInfo;

import java.util.List;
import java.util.Map;

public interface ResourceService {

    public Integer insertResource(ResourceVo resourceVo);
    public  Map<String,String> RandomIp();
    public  Map<String,String> apecifiedIp(String ipName);
    public BaseResult resourceUpd(ResUpdParam resUpdParam);
    public List<UserInfo> getUserList(String ip);
}
