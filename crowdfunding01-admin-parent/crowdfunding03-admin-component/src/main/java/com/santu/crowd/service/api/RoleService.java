package com.santu.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.santu.crowd.entity.Role;

/**
 * @author Santu
 * @date 2021/11/28 15:30
 */
public interface RoleService {
    PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String Keyword);

    int saveRole(Role role);
}
