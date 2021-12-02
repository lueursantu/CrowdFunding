package com.santu.crowd.mvc.handler;

import com.santu.crowd.entity.Admin;
import com.santu.crowd.entity.Role;
import com.santu.crowd.service.api.AdminService;
import com.santu.crowd.service.api.RoleService;
import javafx.scene.control.RadioMenuItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Santu
 * @date 2021/12/2 10:04
 */
@Controller
@Slf4j
public class AssignHandler {

    @Autowired
    AdminService adminService;

    @Autowired
    RoleService roleService;

    @RequestMapping("/assign/to/assign/role/page.html")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId,
                                   @RequestParam("pageNum") Integer pageNum,
                                   @RequestParam("keyword") String keyword,
                                   ModelMap modelMap){
        // 获取已分配角色的列表
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);

        // 获取未分配角色的列表
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);

        // 存入模型
        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);
        return "assign-role";
    }

    @RequestMapping("/assign/do/role/assign.html")
    public String saveAdminRoleRelationship(@RequestParam("adminId") Integer adminId,
                                            @RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("keyword") String keyword,
                                            @RequestParam(value="roleIdList", required = false) List<Integer> roleIdList){

        adminService.saveAdminRoleRelationship(adminId, roleIdList);

        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }
}
