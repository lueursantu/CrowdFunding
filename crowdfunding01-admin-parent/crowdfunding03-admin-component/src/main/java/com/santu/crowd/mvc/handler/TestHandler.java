package com.santu.crowd.mvc.handler;

import com.santu.crowd.entity.Admin;
import com.santu.crowd.service.api.AdminService;
import com.santu.crowd.util.CrowdUtil;
import com.santu.crowd.util.ResultEntity;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Santu
 * @date 2021/11/15 9:05
 */
@Controller
@Slf4j
public class TestHandler {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/test/ssm.html")
    public String testSsm(ModelMap modelMap){
        List<Admin> adminList = adminService.getAll();
        modelMap.addAttribute("adminList", adminList);
        return "target";
    }

    @ResponseBody
    @RequestMapping("/send/array.json")
    public ResultEntity testReceiveArrayOne(@RequestParam("array[]") List<Integer> array){
        for(Integer num:array){
            System.out.println(num);
        }
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/test/md5.html")
    public String testMD5(@RequestParam("md5_str") String md5_str){
        String to_md5 = CrowdUtil.md5(md5_str);
        log.info(md5_str + " to md5: " + to_md5);
        return "index";
    }

}
