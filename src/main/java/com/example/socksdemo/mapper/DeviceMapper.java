package com.example.socksdemo.mapper;

import com.example.socksdemo.model.DeviceVo;

import java.util.List;

public interface DeviceMapper {

    /**
     * 查询设备信息
     */
    public List<DeviceVo> devicelist();

    public void updDevice(DeviceVo deviceVo);

    public void delDevice(Integer device_id);

    public void addDevice(DeviceVo deviceVo);

    public List<DeviceVo>  deviceAll();
}
