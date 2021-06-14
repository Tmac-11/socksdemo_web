package com.example.socksdemo.model;

public class FileDataVo {

    private String ipaddr;
    private String port;
    private String traffic;
    private String time;

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "FileDataVo{" +
                "ipaddr='" + ipaddr + '\'' +
                ", port='" + port + '\'' +
                ", traffic='" + traffic + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
