package com.example.socksdemo.mapper;

import com.example.socksdemo.model.DeviceFlowVo;
import com.example.socksdemo.model.FlowDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFlowMapper {

    public void insertData(List<FlowDataVo> list);
    public void insertOtherData(List<FlowDataVo> list);
    public Integer userFlowDataCount(@Param("startdate") String startdate, @Param("enddate") String enddate);
    public Integer otherFlowDataCount(@Param("startdate") String startdate, @Param("enddate") String enddate);
    public Integer deluserFlowDataCount(@Param("startdate") String startdate, @Param("enddate") String enddate);
    public Integer delotherFlowDataCount(@Param("startdate") String startdate, @Param("enddate") String enddate);
    public List<DeviceFlowVo> deviceMonthData(@Param("startMonth") String startMonth, @Param("endMonth") String endMonth, @Param("ip") String ip);
    public List<DeviceFlowVo> deviceDateData(@Param("startMonth") String startMonth, @Param("endMonth") String endMonth, @Param("ip") String ip);
    public List<DeviceFlowVo> userMonthData(@Param("startMonth") String startMonth, @Param("endMonth") String endMonth, @Param("ip") String ip,@Param("port") String port);
    public List<DeviceFlowVo> userDateData(@Param("startMonth") String startMonth, @Param("endMonth") String endMonth, @Param("ip") String ip,@Param("port") String port);
    public DeviceFlowVo userHourData(@Param("startMonth") String startMonth, @Param("endMonth") String endMonth, @Param("ip") String ip,@Param("port") String port);
    public void insertUserFlow(String dateParm);
    public void insertOtherFlow(String dateParm);


}
