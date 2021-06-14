package com.example.socksdemo.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.LocalDate;
import java.util.Random;

public class UdpUtill {

    private static Log log= LogFactory.getLog(UdpUtill.class);

    public static  String sendData(String data,String ip,int port){
        log.info("开始udp通信");
        //String data="add: {\"server_port\":19610, \"password\":\"123456\"}";
        DatagramSocket datagramSocket=null;
        try {
            //实例化，制定发送端口
            datagramSocket=new DatagramSocket(14323);
            //指定目的地址以及端口
            InetAddress destination=InetAddress.getByName(ip);
            DatagramPacket datagramPacket=new DatagramPacket(data.getBytes(),data.length(),destination,port);
            //发送数据
            datagramSocket.send(datagramPacket);
            datagramSocket.setSoTimeout(10000);

            //接收数据
            byte[] buf=new byte[1024];
            DatagramPacket receivePacket=new DatagramPacket(buf, 0, buf.length);
            datagramSocket.receive(receivePacket);
            //从接收数据包取出数据
            String receivedata=new String(receivePacket.getData() , 0 ,receivePacket.getLength());
            System.out.println(receivedata);
            if("ok".equals(receivedata)){
                log.info("结束udp通信");
                return "success";

            }

        } catch (Exception e) {
            log.error("udp通信发生错误！"+e.getMessage());


        }finally {
            datagramSocket.close();
        }

        return   "fail";

    }




    public static void main(String[] args) {
//        String data="remove: {\"server_port\":19610}";
//        String ip="129.226.149.102";
//        int port=8080;
//        String msg=sendData( data, ip, port);
//        System.out.println("返回结果："+msg);
       // String ip="192.119.93.58";
       String ip="104.168.201.207";
        String port="41183";
        String password="123456";
      //  String data="add: {\"server_port\":19610, \"password\":\"123456\"}";
        String data="add: {\"server_port\":"+port+", \"password\":\""+password+"\"}";

        String info=sendData(data,ip,6001);
        System.out.println(info);


    }



}
