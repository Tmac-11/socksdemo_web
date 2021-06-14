package com.example.socksdemo.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class SearchVo {

    private Integer user_id;
    private String user_name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enddate;
    private String resource_ip;
    private String resource_port;
    private String resource_state;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }

    public String getResource_ip() {
        return resource_ip;
    }

    public void setResource_ip(String resource_ip) {
        this.resource_ip = resource_ip;
    }

    public String getResource_port() {
        return resource_port;
    }

    public void setResource_port(String resource_port) {
        this.resource_port = resource_port;
    }

    public String getResource_state() {
        return resource_state;
    }

    public void setResource_state(String resource_state) {
        this.resource_state = resource_state;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
