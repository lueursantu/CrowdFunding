package com.santu.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.santu.crowd.constant.CrowdConstant;
import com.santu.crowd.entity.Admin;
import com.santu.crowd.exception.LoginAcctAlreadyInUseException;
import com.santu.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.santu.crowd.service.api.AdminService;
import com.santu.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author Santu
 * @date 2021/11/18 14:20
 */
@Controller
public class AdminHandler {

    @Autowired
    AdminService adminService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/admin/do/login.html")
    public String doLongin(@RequestParam("loginAcct")String loginAcct,
                           @RequestParam("userPswd")String userPswd,
                           HttpSession session){
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "redirect:/admin/main/page.html";
    }

    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session){
        session.invalidate();
        return "redirect:/admin/login/page.html";
    }

    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pagesize,
            ModelMap modelMap
    ){
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pagesize);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }

    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String removeAdminByID(
            @PathVariable("adminId") Integer adminId,
            @PathVariable("pageNum") int pageNum,
            @PathVariable("keyword") String keyword){
        adminService.removeAdminByID(adminId);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @PreAuthorize("hasAuthority('user:save')")
    @RequestMapping("/admin/add.html")
    public String addAdmin(Admin admin){
        try{
            // admin.setUserPswd(CrowdUtil.md5(admin.getUserPswd()));
            admin.setUserPswd(passwordEncoder.encode(admin.getUserPswd()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateNowStr = sdf.format(new Date());
            admin.setCreateTime(dateNowStr);
            adminService.saveAdmin(admin);
        }catch (Exception e){
            if(e instanceof org.springframework.dao.DuplicateKeyException){
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_UES);
            }
        }
        return "redirect:/admin/get/page.html?pageNum="+Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/update.html")
    public String updateAdmin(Admin admin){
        try{
            adminService.updateAdmin(admin);
        }catch (Exception e){
            e.printStackTrace();
            if(e instanceof org.springframework.dao.DuplicateKeyException){
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_UES);
            }
        }
        return "redirect:/admin/get/page.html";
    }

    @RequestMapping("/admin/to/update/page.html")
    public String updateAdmin(@RequestParam("adminId") int id, ModelMap modelMap){
        Admin admin = adminService.getAdminById(id);
        modelMap.addAttribute(admin);
        return "admin-update";
    }
}
