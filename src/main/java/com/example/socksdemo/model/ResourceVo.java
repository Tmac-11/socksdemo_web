package com.example.socksdemo.model;


import java.time.LocalDateTime;
import java.util.Date;

public class ResourceVo {

    private Integer resource_id;
    private String resource_ip;
    private String resource_port;
    private String resource_state;
    private LocalDateTime resource_loadtime;
    private String resource_type;
    private String resource_json;
    private String resource_domain_name;


    public Integer getResource_id() {
        return resource_id;
    }

    public void setResource_id(Integer resource_id) {
        this.resource_id = resource_id;
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

    public String getResource_domain_name() {
        return resource_domain_name;
    }

    public void setResource_domain_name(String resource_domain_name) {
        this.resource_domain_name = resource_domain_name;
    }
}
