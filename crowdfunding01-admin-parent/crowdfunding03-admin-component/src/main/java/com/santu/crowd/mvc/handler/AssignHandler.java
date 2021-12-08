package com.santu.crowd.mvc.handler;

import com.santu.crowd.entity.Admin;
import com.santu.crowd.entity.Auth;
import com.santu.crowd.entity.Role;
import com.santu.crowd.service.api.AdminService;
import com.santu.crowd.service.api.AuthService;
import com.santu.crowd.service.api.RoleService;
import com.santu.crowd.util.ResultEntity;
import javafx.scene.control.RadioMenuItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

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

    @Autowired
    AuthService authService;

    @ResponseBody
    @RequestMapping("/assign/do/save/role/auth/relationship.json")
    public ResultEntity savaAuthRoleRelationship(@RequestBody Map<String, List<Integer>> map){
        authService.savaAuthRoleRelationship(map);
        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(@RequestParam("roleId") Integer roleId){
        List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(authIdList);
    }

    @ResponseBody
    @RequestMapping("/assign/get/tree.json")
    public ResultEntity<List<Auth>> getAll(){
        List<Auth> authList = authService.getAll();
        return ResultEntity.successWithData(authList);
    }

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
