package com.santu.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.santu.crowd.entity.Role;

import java.util.List;

/**
 * @author Santu
 * @date 2021/11/28 15:30
 */
public interface RoleService {
    PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String Keyword);

    int saveRole(Role role);

    int updateRole(Role role);

    int removeRole(List<Integer> roleIdList);

    List<Role>getAssignedRole(Integer adminId);

    List<Role>getUnAssignedRole(Integer adminId);
}
