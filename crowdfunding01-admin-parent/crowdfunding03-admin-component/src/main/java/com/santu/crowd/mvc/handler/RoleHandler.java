package com.santu.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.santu.crowd.entity.Role;
import com.santu.crowd.service.api.RoleService;
import com.santu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Santu
 * @date 2021/11/28 15:33
 */
@Controller
public class RoleHandler {
    @Autowired
    RoleService roleService;

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
