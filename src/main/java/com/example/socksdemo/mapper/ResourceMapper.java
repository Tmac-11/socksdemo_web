package com.example.socksdemo.mapper;

import com.example.socksdemo.model.ResourceVo;
import com.example.socksdemo.model.UserInfo;

import java.util.List;

public interface ResourceMapper {


    public List<ResourceVo> ResourceIpCount(String resourceIp);
    public List<ResourceVo> ResourceCount(String resourceIp);
    public  Integer insertResource(ResourceVo resourceVo);
    public  void updResource(UserInfo userInfo);
    public void delResource(Integer resource_id);
    public ResourceVo ResourceByid(Integer resource_id);
    public String [] ResourceEffective();
    public List<UserInfo> getUserList(String resourceIp);

}
