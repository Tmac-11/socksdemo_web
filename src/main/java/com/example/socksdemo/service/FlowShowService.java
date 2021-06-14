package com.example.socksdemo.service;

import com.example.socksdemo.model.DeviceFlowShowVo;
import com.example.socksdemo.model.FlowShowVo;
import com.example.socksdemo.model.UserFlowShowVo;

import java.util.List;

public interface FlowShowService {

    public FlowShowVo deviceMonthData(String startmonth, String endmonth,String [] ipArr);
    public FlowShowVo deviceDateData(String startmonth, String endmonth,String [] ipArr);
    public FlowShowVo userMonthData(String startmonth, String endmonth, String queryUser, String queryUserShow);
    public FlowShowVo userDateData(String startmonth,String endmonth,String queryUser,String queryUserShow);
    public FlowShowVo userHourData(String startmonth,String endmonth,String queryUser,String queryUserShow);

}
