package com.example.socksdemo.service.impl;

import com.example.socksdemo.mapper.DeviceMapper;
import com.example.socksdemo.mapper.ResourceMapper;
import com.example.socksdemo.model.DeviceVo;
import com.example.socksdemo.model.ResourceVo;
import com.example.socksdemo.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService{

    @Autowired
    DeviceMapper deviceMapper;
    @Autowired
    ResourceMapper resourceMapper;

    @Value("${ipCount}")
    private int ipCount;

    @Override
    public List<DeviceVo> DeviceInfo() {
        List<DeviceVo> deviceVoList=  deviceMapper.deviceAll();
        for(int i=0;i<deviceVoList.size();i++){
            if(deviceVoList.get(i).getDevice_state().equals("1")){
                List<ResourceVo> resourceList = resourceMapper.ResourceIpCount(deviceVoList.get(i).getDevice_ip());
                deviceVoList.get(i).setDevice_count(resourceList.size());
            }else{
                deviceVoList.get(i).setDevice_count(0);
            }

        }
        return deviceVoList;
    }

    @Override
    public void updInfo(DeviceVo deviceVo) {
        deviceMapper.updDevice(deviceVo);
    }

    @Override
    public void delInfo(Integer device_id) {
        deviceMapper.delDevice(device_id);
    }

    @Override
    public void addDevice(DeviceVo device) {
            deviceMapper.addDevice(device);
    }


    @Override
    public List<DeviceVo> Devicelist() {
        List<DeviceVo> deviceVoList= deviceMapper.devicelist();
        for(int i=0;i<deviceVoList.size();i++){
            List<ResourceVo> resourceList = resourceMapper.ResourceIpCount(deviceVoList.get(i).getDevice_ip());
            if(resourceList.size()>ipCount){
                deviceVoList.remove(deviceVoList.get(i));
            }else{
                continue;
            }
        }

       return   deviceVoList;
    }

    @Override
    public Integer DeviceCount(String ip) {
        List<ResourceVo> resourceList = resourceMapper.ResourceIpCount(ip);
        return resourceList.size();
    }
}
