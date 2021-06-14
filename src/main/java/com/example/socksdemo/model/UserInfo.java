package com.example.socksdemo.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserInfo {

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
    private String user_type;
    private String user_code;
    private Integer user_rights;
    private String resource_ip;
    private String resource_port;
    private String resource_state;
    private LocalDateTime resource_loadtime;
    private String resouce_code;
    private String resource_type;
    private String resource_json;
    private String key_type;
    private  String resource_domain_name;

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

    public String getResource_state() {
        return resource_state;
    }

    public void setResource_state(String resource_state) {
        this.resource_state = resource_state;
    }

    public LocalDateTime getResource_loadtime() {
        return resource_loadtime;
    }

    public void setResource_loadtime(LocalDateTime resource_loadtime) {
        this.resource_loadtime = resource_loadtime;
    }

    public String getResouce_code() {
        if("1".equals(resource_state)){
            resouce_code="资源有效";
        }else if("0".equals(resource_state)){
            resouce_code="资源无效";
        }
        return resouce_code;
    }

    public void setResouce_code(String resouce_code) {
        this.resouce_code = resouce_code;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getResource_json() {
        return resource_json;
    }

    public void setResource_json(String resource_json) {
        this.resource_json = resource_json;
    }

    public String getKey_type() {
        return key_type;
    }

    public void setKey_type(String key_type) {
        this.key_type = key_type;
    }

    public String getResource_domain_name() {
        return resource_domain_name;
    }

    public void setResource_domain_name(String resource_domain_name) {
        this.resource_domain_name = resource_domain_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public Integer getUser_rights() {
        return user_rights;
    }

    public void setUser_rights(Integer user_rights) {
        this.user_rights = user_rights;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "用户id=" + user_id +
                ", 用户名='" + user_name + '\'' +
                ", 用户密码='" + user_password + '\'' +
                ", 开始日期=" + startdate +
                ", 结束日期=" + enddate +
                ", 资源id=" + resource_id +
                ", 用户创建时间=" + user_creattime +
                ", 资源ip='" + resource_ip + '\'' +
                ", 资源端口='" + resource_port + '\'' +
                ", 资源状态代码='" + resource_state + '\'' +
                ", 资源创建时间=" + resource_loadtime +
                ", 资源状态='" + resouce_code + '\'' +
                '}'+System.getProperty("line.separator");
    }
}
