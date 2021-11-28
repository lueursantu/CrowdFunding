package com.santu.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.santu.crowd.entity.Role;
import com.santu.crowd.mapper.RoleMapper;
import com.santu.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Santu
 * @date 2021/11/28 15:30
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);
        return new PageInfo<>(roleList);
    }
}
