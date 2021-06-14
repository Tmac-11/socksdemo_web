package com.example.socksdemo.mapper;

import com.example.socksdemo.model.FileDataVo;
import com.example.socksdemo.model.FlowDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SrcFlowDataMapper {

    public void insertFlowData(List<FileDataVo> list);
    public String [] flowDataByDate(String dateParm);
    public List<String> flowDataByIp(@Param("dateParm") String dateParm,@Param("ipaddr") String ipaddr ,@Param("delList")List<FlowDataVo> delList);
    public List<FileDataVo> flowDatalist(@Param("dateParm") String dateParm,@Param("ipaddr") String ipaddr,@Param("port")String port);
    public Integer dataCount(@Param("startdate") String startdate,@Param("enddate") String enddate);
    public Integer deldataCount(@Param("startdate") String startdate,@Param("enddate") String enddate);
    public void insertFlowDataTmp(String dateParm);
    public void countFlowProcedure(String dateParm);
    public void delFlowDataTmp();
}
