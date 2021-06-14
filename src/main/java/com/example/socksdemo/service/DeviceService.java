package com.example.socksdemo.service;

import com.example.socksdemo.model.DeviceVo;

import java.util.List;

public interface DeviceService {


    public List<DeviceVo> DeviceInfo();
    public void updInfo(DeviceVo deviceVo);
    public void delInfo(Integer device_id);
    public void addDevice(DeviceVo device);
    public List<DeviceVo> Devicelist();
    public Integer DeviceCount(String ip);
}
