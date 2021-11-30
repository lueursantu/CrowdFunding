package com.santu.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.santu.crowd.entity.Role;
import com.santu.crowd.service.api.RoleService;
import com.santu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Santu
 * @date 2021/11/28 15:33
 */
@Controller
public class RoleHandler {
    @Autowired
    RoleService roleService;

    @ResponseBody
    @RequestMapping("/remove/role.json")
    public ResultEntity removeRole(@RequestBody List<Integer> roleIdList){
        try {
            roleService.removeRole(roleIdList);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping("/update/role.json")
    public ResultEntity updateRole(@RequestParam("roleId") int id,
                                   @RequestParam("roleName") String roleName){
        try {
            roleService.updateRole(new Role(id, roleName));
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping("/save/role.json")
    public ResultEntity saveRole(@RequestParam("roleName") String roleName) {
        try {
            roleService.saveRole(new Role(null, roleName));
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping("/role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "") String keyword){

        PageInfo<Role> PageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
        return ResultEntity.successWithData(PageInfo);
    }
}
