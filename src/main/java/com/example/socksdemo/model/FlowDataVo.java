package com.example.socksdemo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FlowDataVo {

    private String user_name;
    private String user_ip;
    private String user_port;
    private BigDecimal flow_count;
    private LocalDate flow_date;
    private String flow_json;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public String getUser_port() {
        return user_port;
    }

    public void setUser_port(String user_port) {
        this.user_port = user_port;
    }

    public BigDecimal getFlow_count() {
        return flow_count;
    }

    public void setFlow_count(BigDecimal flow_count) {
        this.flow_count = flow_count;
    }

    public LocalDate getFlow_date() {
        return flow_date;
    }

    public void setFlow_date(LocalDate flow_date) {
        this.flow_date = flow_date;
    }

    public String getFlow_json() {
        return flow_json;
    }

    public void setFlow_json(String flow_json) {
        this.flow_json = flow_json;
    }


    @Override
    public String toString() {
        return "FlowDataVo{" +
                "user_name='" + user_name + '\'' +
                ", user_ip='" + user_ip + '\'' +
                ", user_port='" + user_port + '\'' +
                ", flow_count=" + flow_count +
                ", flow_date=" + flow_date +
                ", flow_json='" + flow_json + '\'' +
                '}';
    }
}
