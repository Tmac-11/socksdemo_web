package com.example.socksdemo.utils;

import com.jcraft.jsch.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ShellUtil {


    private static Log logger= LogFactory.getLog(ShellUtil.class);

    public static int shellExecute(String ip, Integer port, String username, String password, String command, List<String> result) {
        int returnCode = 0;
        JSch jsch = new JSch();
        Session session = null;
        try {
            //创建session并且打开连接，因为创建session之后要主动打开连接
            if (port == 0) {
                session = jsch.getSession(username, ip, port);
            } else {
                session = jsch.getSession(username, ip, port);
            }

            session.setPassword(password);

            //关闭主机密钥检查，否则会导致连接失败，重要！！！
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            logger.info("连接服务器" + session.getHost());

            session.connect();
            //打开通道，设置通道类型，和执行的命令
            Channel channel = session.openChannel("exec");
            ChannelExec channelExec = (ChannelExec) channel;
            channelExec.setCommand(command);

            channelExec.setInputStream(null);
            BufferedReader input = new BufferedReader(new InputStreamReader((channelExec.getInputStream())));
            channelExec.connect();
            logger.info("The remote command is:" + command);
            //接受远程服务器执行命令的结果

            String line = null;
            logger.info("stdout信息开始打印");
            while ((line = input.readLine()) != null) {
                result.add(line);
//              logger.info(line);
            }
            logger.info("stdout信息打印结束");
            input.close();

            //得到returnCode
            if (channelExec.isClosed()) {
                returnCode = channelExec.getExitStatus();
            }

            //关闭通道
            channelExec.disconnect();
            //关闭session
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnCode;
    }





    public static void main(String[] args) {
//        String ip="38.143.19.31";
//        Integer port=22;
//        String username="root";
//        String password="tA#b6L}_87ul,|9";
//        String commamd ="sh /worker_server/traffic/parse.sh";
//        List<String> result=new ArrayList<String>();
//       int staute= shellExecute(ip,port,username,password,commamd,result);
//       if(staute==0){
//                System.out.println(result.toString());
//       }

        BigDecimal sum=new BigDecimal("0.1");

        BigDecimal sum1=new BigDecimal("0.1");
        sum=sum.add(sum1);
        System.out.println(sum);
    }
}
