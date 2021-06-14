package com.example.socksdemo.model;

import java.math.BigDecimal;

public class DeviceFlowVo {


    private String dataMonth;
    private BigDecimal flowCount;
    private String flowJson;


    public String getDataMonth() {
        return dataMonth;
    }

    public void setDataMonth(String dataMonth) {
        this.dataMonth = dataMonth;
    }

    public BigDecimal getFlowCount() {
        return flowCount;
    }

    public void setFlowCount(BigDecimal flowCount) {
        this.flowCount = flowCount;
    }

    public String getFlowJson() {
        return flowJson;
    }

    public void setFlowJson(String flowJson) {
        this.flowJson = flowJson;
    }
}
