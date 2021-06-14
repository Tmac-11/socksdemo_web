package com.example.socksdemo.service.impl;

import com.example.socksdemo.mapper.ResourceMapper;
import com.example.socksdemo.mapper.SrcFlowDataMapper;
import com.example.socksdemo.mapper.UserFlowMapper;
import com.example.socksdemo.mapper.UserMapper;
import com.example.socksdemo.model.FileDataVo;
import com.example.socksdemo.model.FlowDataVo;
import com.example.socksdemo.model.UserInfo;
import com.example.socksdemo.service.FlowDataService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Transactional
public class FlowDataServiceImpl implements FlowDataService{

    private static Log log= LogFactory.getLog(FlowDataServiceImpl.class);
    @Value("${textfile}")
    private String path;
    @Autowired
    SrcFlowDataMapper srcFlowDataMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserFlowMapper userFlowMapper;
    @Autowired
    ResourceMapper resourceMapper;

    @Override
    public String dataProcess(String fileDate) {
        List<FileDataVo> list=null;
        String error="";
        String msg="";
        String ip [] =resourceMapper.ResourceEffective();
        //String ip [] ={"38.143.19.31","38.147.162.117"};

        for(int i=0; i<ip.length;i++){
            log.info("ip:"+ip[i]);
            Map<String,Object> map=AnalyticalFile(fileDate,ip[i]);
            msg=(String)map.get("status");

            if(msg.equals("success")){
                list=(List<FileDataVo>)map.get(ip[i]);
            }else{
                error=error+msg;
                continue;

               // return "AnalyticalFile is error :"+msg;
            }
            log.info("list:"+list.size());
            try {
                if(list.size()!=0){
                    srcFlowDataMapper.insertFlowData(list);


                }

            }catch (Exception e){
                log.error("src_flow_data插入失败："+e.getMessage());
                return "insert src_flow_data fail";
            }
        }

        if(error.contains("file not exist")){
            return error;
        }else{
            return msg;
        }

    }

    /**
     * 解析txt数据
     * @return
     */
    private Map<String,Object> AnalyticalFile(String fileDate,String ip){

        Map<String,Object> map=new HashMap<String,Object>();
        List<FileDataVo> list=new ArrayList<FileDataVo>();
        FileDataVo fileDataVo=null;
//        for(int t=0;t<iplist.length;t++){
//           String ip= iplist[t];

        String filePath=path+"traffics_ip"+ip+"_"+fileDate+".txt";
        File file=new File(filePath);
        if(!file.exists()){
            String msg=" file not exist!";
           log.info(ip+"_"+fileDate+msg);
           //System.out.println(ip+fileDate+msg);
            map.put("status",ip+"_"+fileDate+msg);
            return map;
        }
        StringBuilder result=new StringBuilder();
        try{
            BufferedReader br=new BufferedReader(new FileReader(file));
            String s=null;
            while ((s=br.readLine())!=null){
                result.append(System.lineSeparator()+s+",");
            }
            br.close();
        }catch (IOException e){
            log.error(ip+fileDate+"文件读取失败："+e.getMessage());
            map.put("status","fail");
            return map;
        }
        String fileData= result.toString();
       // log.info("读取内容:"+fileData);
        String dataArr []= fileData.split("------------------------------");

        String ipaddr=null;
        String port=null;
        String traffic=null;
        for(int i=0;i<dataArr.length;i++){
            String dataString=dataArr[i];
            // System.out.println(dataString);
            Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}\\s*[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}");
            Matcher match = pattern.matcher(dataString);
            if(match.find()){

                String date=dataString.substring(match.start(),match.end());
              //  log.info("匹配日期进来:"+date);
//                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                LocalDateTime dateformatter = LocalDateTime.parse(date, fmt);
//                LocalDateTime newdate=dateformatter.plusHours(-1);

                // System.out.println("日期："+data);
                String datalistString=dataString.substring(match.end());
                String datalist [] =datalistString.split(",");
               for(int j=0;j<datalist.length;j++ ){
//                   String line=datalist[j];
////                   Pattern linePattern = Pattern.compile("ipaddr:.*(\\s*)");
////                   Matcher lineMatch = pattern.matcher(line);
//                    if(line.contains("ipaddr")){
//                        String field []=line.split("\t");
//                        for (int k=0;k<field.length;k++){
//                            if(field[k].contains(":")){
//                                int index=field[k].lastIndexOf(":");
//                                String field_x=field[k].substring(index+1);
//                                if(field[k].contains("ipaddr")){
//                                    ipaddr=field_x;
//                                }else if(field[k].contains("port")){
//                                    port=field_x;
//                                }else if(field[k].contains("traffic")){
//                                    traffic=field_x;
//                                }
//                            }else{
//                                continue;
//                            }
//                        }
//                        fileDataVo=new FileDataVo();
//                        fileDataVo.setIpaddr(ipaddr);
//                        fileDataVo.setPort(port);
//                        fileDataVo.setTraffic(traffic);
//                        fileDataVo.setTime(data);
//                        list.add(fileDataVo);
//
//
//                    }else{
//                        continue;
//                    }
//
                   String line=datalist[j];
                  // log.info("未匹配数字之前:"+line);
                   //字符串中是否包含数字
                   Pattern p = Pattern.compile(".*[0-9].*");
                   Matcher m = p.matcher(line.trim());
                   if(m.matches()){
                      // log.info("有数字行数匹配进来:"+line);
                       String field []=line.split("\t");
                        fileDataVo=new FileDataVo();
                        fileDataVo.setIpaddr(field[0].trim());
                        fileDataVo.setPort(field[1].trim());
                        fileDataVo.setTraffic(field[2].trim());
                        fileDataVo.setTime(date);
                        list.add(fileDataVo);



                   }else{
                       continue;
                   }

               }

            }else{
                continue;
            }

        }


        map.put(ip,list);
        map.put("status","success");
     //   file.delete();
    //    }

        return map;

    }

    /**
     * 计算src_flow_data表
     * 插入user_flow表
     * * @param dateParm
     * 开始时间：2020-03-22T03:31:02.617
     *结束时间：2020-03-22T04:18:26.684
     *执行时间：47min
     * @return
     */
    @Transactional
    @Override
    public String dataByDate(String dateParm) {
        String ipString []=srcFlowDataMapper.flowDataByDate(dateParm);
        if(ipString.length==0){
            return "no data";
        }
        FlowDataVo flowDataVo=null;
        List<FlowDataVo> listData=new ArrayList<FlowDataVo>();
        List<FlowDataVo> listOtherData=null;

        for(int i=0;i<ipString.length;i++) {

            String ip = ipString[i];
            //存入数据库
            List<UserInfo> userlist = resourceMapper.getUserList(ip);
            if (userlist.size() == 0) {
                continue;
            }

            for (int j = 0; j < userlist.size(); j++) {
                flowDataVo = new FlowDataVo();
                List<FileDataVo> list = srcFlowDataMapper.flowDatalist(dateParm, userlist.get(j).getResource_ip(), userlist.get(j).getResource_port());
                if (list.size() == 0) {
                    continue;
                }
                try {
                    HashMap<String, Object> map = json(list);
                    String json = (String) map.get("json");
                    BigDecimal sum = (BigDecimal) map.get("sum");
                    flowDataVo = new FlowDataVo();
                    flowDataVo.setUser_name(userlist.get(j).getUser_name());
                    flowDataVo.setFlow_json(json);
                    flowDataVo.setUser_ip(userlist.get(j).getResource_ip());
                    flowDataVo.setUser_port(userlist.get(j).getResource_port());
                    flowDataVo.setFlow_count(sum);
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(dateParm, fmt);
                    flowDataVo.setFlow_date(date);
                    listData.add(flowDataVo);

                } catch (Exception e) {
                    log.error("ip:" + userlist.get(j).getResource_ip() + ",port:" + userlist.get(j).getResource_port() + ",用户端口处理失败" + e.getMessage());
                    return "fail user processing data";
                }


            }

//            //当前ip所有其他端口
           List<String> portList=srcFlowDataMapper.flowDataByIp(dateParm,ip,listData);
//            for(int k=0;k<portList.size();k++){
//                List<FileDataVo> list= srcFlowDataMapper.flowDatalist(dateParm,ip,portList.get(k));
//                if(list.size()==0){
//                    continue;
//                }
//                try{
//                    HashMap<String,Object> map=json(list);
//                    String json=(String) map.get("json");
//                    BigDecimal sum= (BigDecimal)map.get("sum");
//                    otherFlowDataVo=new FlowDataVo();
//                    //otherFlowDataVo.setUser_name("");
//                    otherFlowDataVo.setFlow_json(json);
//                    otherFlowDataVo.setUser_ip(ip);
//                    otherFlowDataVo.setUser_port(portList.get(k));
//                    otherFlowDataVo.setFlow_count(sum);
//                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                    LocalDate date = LocalDate.parse(dateParm, fmt);
//                    otherFlowDataVo.setFlow_date(date);
//                     listOtherData.add(otherFlowDataVo);
//                }catch (Exception e){
//                    log.error("ip:"+ip+",port:"+portList.get(k)+",其他端口处理失败"+e.getMessage());
//                    return "fail processing data";
//                }
//            }
           try{
               listOtherData= listBatchVoid(portList,dateParm,ip);
           }catch (Exception e){
               return "fail user processing data";
           }

            try{
                userFlowMapper.insertData(listData);
                userFlowMapper.insertOtherData(listOtherData);

            }catch (Exception e){
                log.error("数据库插入失败："+e.getMessage());
                return "fail insert data";
            }

           listData.clear();
           listOtherData.clear();
        }




        return "success";
    }


    private List<FlowDataVo> listBatchVoid(List<String> portList,String dateParm,String ip){
        List<FlowDataVo> listOtherData=new ArrayList<FlowDataVo>();
        int num=1000;
        int times=portList.size()%num;
        int threadNum=0;
        if(times==0){
            threadNum=portList.size()/num;
        }else{
            threadNum=portList.size()/num+1;
        }

        ExecutorService eService= Executors.newFixedThreadPool(threadNum);

        List<Callable<String>> cList=new ArrayList<>();
        Callable<String> task=null;
        List<String> sList=null;

        for(int i=0;i<threadNum;i++){

          if(i==threadNum-1){
              sList=portList.subList(i*num,portList.size());
          }else {
              sList=portList.subList(i*num,(i+1)*num);
          }
          final List<String> nowList=sList;
          task=new Callable<String>() {
              @Override
              public String call() throws Exception {
                  FlowDataVo otherFlowDataVo=null;
                  for(int k=0;k<nowList.size();k++){
                      List<FileDataVo> list= srcFlowDataMapper.flowDatalist(dateParm,ip,nowList.get(k));
                      if(list.size()==0){
                          continue;
                      }
                      try{
                          HashMap<String,Object> map=json(list);
                          String json=(String) map.get("json");
                          BigDecimal sum= (BigDecimal)map.get("sum");
                          otherFlowDataVo=new FlowDataVo();
                          //otherFlowDataVo.setUser_name("");
                          otherFlowDataVo.setFlow_json(json);
                          otherFlowDataVo.setUser_ip(ip);
                          otherFlowDataVo.setUser_port(nowList.get(k));
                          otherFlowDataVo.setFlow_count(sum);
                          DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                          LocalDate date = LocalDate.parse(dateParm, fmt);
                          otherFlowDataVo.setFlow_date(date);
                          listOtherData.add(otherFlowDataVo);
                      }catch (Exception e){
                          log.error("ip:"+ip+",port:"+nowList.get(k)+",其他端口处理失败"+e.getMessage());
                          return "fail user processing data";
                      }
                  }
                  return "success";

              }
          };
          cList.add(task);

        }
        try{
            List<Future<String>> results=eService.invokeAll(cList);
            for(Future<String> str:results){
                System.out.println("执行结果："+str.get());
            }
        }catch (Exception e){
            e.getMessage();
            System.out.println("线程错误："+e.getMessage());
        }

        eService.shutdown();

            return listOtherData;

    }

    private HashMap<String,Object> json(List<FileDataVo> list){
        HashMap<String,Object> map=new HashMap<String,Object>();
        String json="{";
        BigDecimal num=null;
        BigDecimal sum=new BigDecimal("0.0");
           for(FileDataVo fileDataVo :list){
               json=json+"\""+fileDataVo.getTime()+"\""+":"+fileDataVo.getTraffic()+",";
               num= new BigDecimal(fileDataVo.getTraffic());
               sum= sum.add(num);
           }
        ;
        json=json.substring(0,json.length()-1)+"}";
        map.put("json" ,json);
        map.put("sum",sum);
        return  map;
    }


    @Override
    public String dataByServer(String dateParm) {
        return null;
    }

    @Override
    public Integer queryTable(String startdate, String enddate, String select_table) {
        Integer num=null;
        if("src_flow_data".equals(select_table)){
         num= srcFlowDataMapper.dataCount(startdate,enddate);
        }else if("user_flow".equals(select_table)){
            num=  userFlowMapper.userFlowDataCount(startdate,enddate);
        }else if("other_flow".equals(select_table)){
            num=  userFlowMapper.otherFlowDataCount(startdate,enddate);
        }

        return num;
    }


    @Override
    public Integer delTable(String startdate, String enddate, String select_table) {
        Integer num=null;
        if("src_flow_data".equals(select_table)){
            num= srcFlowDataMapper.deldataCount(startdate,enddate);
        }else if("user_flow".equals(select_table)){
           num=  userFlowMapper.deluserFlowDataCount(startdate,enddate);
        }else if("other_flow".equals(select_table)){
            num=  userFlowMapper.delotherFlowDataCount(startdate,enddate);
        }

        return num;
    }


    /**
     *
     * @param dateParm
     * @return
     */
    @Transactional
    @Override
    public String dataByProcedureDate(String dateParm) {
       int num= srcFlowDataMapper.dataCount(dateParm,dateParm);
       if(num==0){
           log.info("日期:"+dateParm+" src_flow_data表没有数据");
           return dateParm+" no data";
       }
        try {
            for (int i = 0; i < 24; i++) {
                String hourParm=null;
                if (i < 10) {
                    hourParm = " 0" + i;
                } else {
                    hourParm = " " + i;
                }
                String parm = dateParm + hourParm;
                //insert src_flow_data_tmp表
                srcFlowDataMapper.insertFlowDataTmp(parm);
                //计算此段时间数据
                srcFlowDataMapper.countFlowProcedure(parm);

            }
        }catch (Exception e){
            log.error("日期为："+dateParm+"存储过程调用失败："+e.getMessage());
            return "fail procedure";
        }
        //删除src_flow_data_tmp表中数据
        srcFlowDataMapper.delFlowDataTmp();
        try{
            //insert 到user_flow
            userFlowMapper.insertUserFlow(dateParm);
            //insert 到other_flow
            userFlowMapper.insertOtherFlow(dateParm);
        }catch (Exception e){
            log.error("日期为："+dateParm+"插入user_flow or other_flow失败："+e.getMessage());
            return "fail insert user_flow or other_flow";
        }


        return "success";
    }
}
