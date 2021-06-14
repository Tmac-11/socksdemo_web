package com.example.socksdemo.controller;

import com.example.socksdemo.ExcelUtil.ExcelUtils;
import com.example.socksdemo.model.ExcelVo;
import com.example.socksdemo.model.UserAmount;
import com.example.socksdemo.model.UserInfo;
import com.example.socksdemo.service.AmountService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/amountController")
public class AmountController {


    @Autowired
    AmountService amountService;
    private static Log log= LogFactory.getLog(AmountController.class);

    @RequestMapping("/amountHtml")
    public String amountHtml(Model model){
        UserAmount userAmount=null;
        model.addAttribute("amountList",amountService.queryInfo(userAmount));
        return "amountInfo";
    }


    @RequestMapping("/querylist")
    public String querylist(Model model, @ModelAttribute UserAmount userAmount){

        model.addAttribute("amountList",amountService.queryInfo(userAmount));
        return "amountInfo";
    }


    @RequestMapping("/edit")
    public String editHtml(@ModelAttribute UserAmount userAmount, Model model){
        model.addAttribute("amount",userAmount);
        return "amountEdit";
    }

    @RequestMapping("/updAmount")
    public String updAmount(@ModelAttribute UserAmount userAmount){
        amountService.updAmount(userAmount);
        return "redirect:/amountController/amountHtml";
    }

    @RequestMapping("/delInfo")
    public String updAmount(Integer amount_id){
        amountService.delAmount(amount_id);
        return "redirect:/amountController/amountHtml";
    }


    @RequestMapping("/excel")
    @ResponseBody
    public void excel(@ModelAttribute UserAmount userAmount,HttpServletResponse response){
        List<UserAmount> list= amountService.queryInfo(userAmount);
        List<ExcelVo> excelVoList=new ArrayList<ExcelVo>();

        ExcelVo excelVo=null;
        for(UserAmount amount :list){
            excelVo=new ExcelVo();
            excelVo.setName(amount.getUser_name());
            excelVo.setAmount(amount.getUser_amount());
            excelVo.setTime(amount.getAmount_date());
            excelVo.setType(amount.getAmount_type());
            excelVo.setCreatetime(amount.getAmount_creattime().toString());
            excelVoList.add(excelVo);

        }

        String fileName="帐单_"+ LocalDate.now();
          try{
              ExcelUtils.download(response,fileName,excelVoList);

          }catch (Exception e){
              log.error("excel export fail "+e.getMessage());
          }


    }
}
