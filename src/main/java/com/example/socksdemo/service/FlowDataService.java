package com.example.socksdemo.service;

public interface FlowDataService {

    /**
     * YYYY_MM_DD_HH  2020_2_7_0
     * @param fileDate
     * @return
     */
    public String dataProcess(String fileDate);
    public String dataByDate(String dateParm);
    public String dataByServer(String dateParm);
    public Integer queryTable(String startdate,String enddate,String select_table);
    public Integer delTable(String startdate,String enddate,String select_table);
    public String dataByProcedureDate(String dateParm);


}
