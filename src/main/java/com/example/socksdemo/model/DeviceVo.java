package com.example.socksdemo.model;

public class DeviceVo {


    private Integer device_id;
    private String device_ip;
    private String device_state;
    private String state_code;
    private String device_domain_name;
    private Integer device_count;


    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public String getDevice_ip() {
        return device_ip;
    }

    public void setDevice_ip(String device_ip) {
        this.device_ip = device_ip;
    }

    public String getDevice_state() {
        return device_state;
    }

    public void setDevice_state(String device_state) {
        this.device_state = device_state;
    }

    public String getState_code() {
        if("1".equals(device_state)){
            state_code="有效";
        }else if("0".equals(device_state)){
            state_code="无效";
        }
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getDevice_domain_name() {
        return device_domain_name;
    }

    public void setDevice_domain_name(String device_domain_name) {
        this.device_domain_name = device_domain_name;
    }

    public Integer getDevice_count() {
        return device_count;
    }

    public void setDevice_count(Integer device_count) {
        this.device_count = device_count;
    }
}
