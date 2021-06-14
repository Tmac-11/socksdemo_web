package com.example.socksdemo.model;

public class DeviceFlowShowVo {

    private  String ipName;
    private String domainName;
    private String [] jsonArr;


    public String getIpName() {
        return ipName;
    }

    public void setIpName(String ipName) {
        this.ipName = ipName;
    }

    public String[] getJsonArr() {
        return jsonArr;
    }

    public void setJsonArr(String[] jsonArr) {
        this.jsonArr = jsonArr;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }


}
