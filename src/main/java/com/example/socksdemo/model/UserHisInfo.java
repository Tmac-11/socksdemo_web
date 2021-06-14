package com.example.socksdemo.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserHisInfo {

    private Integer his_id;
    private Integer user_id;
    private String user_name;
    private String user_password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enddate;
    private Integer resource_id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime user_creattime;
    private String resource_ip;
    private String resource_port;
    private LocalDateTime resource_loadtime;
    private String remark_type;

    public Integer getHis_id() {
        return his_id;
    }

    public void setHis_id(Integer his_id) {
        this.his_id = his_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
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

    public Integer getResource_id() {
        return resource_id;
    }

    public void setResource_id(Integer resource_id) {
        this.resource_id = resource_id;
    }

    public LocalDateTime getUser_creattime() {
        return user_creattime;
    }

    public void setUser_creattime(LocalDateTime user_creattime) {
        this.user_creattime = user_creattime;
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

    public LocalDateTime getResource_loadtime() {
        return resource_loadtime;
    }

    public void setResource_loadtime(LocalDateTime resource_loadtime) {
        this.resource_loadtime = resource_loadtime;
    }

    public String getRemark_type() {
        return remark_type;
    }

    public void setRemark_type(String remark_type) {
        this.remark_type = remark_type;
    }
}
