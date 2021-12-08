package com.santu.crowd.service.impl;

import com.santu.crowd.entity.Auth;
import com.santu.crowd.entity.AuthExample;
import com.santu.crowd.mapper.AuthMapper;
import com.santu.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Santu
 * @date 2021/12/3 9:09
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    @Override
    public void savaAuthRoleRelationship(Map<String, List<Integer>> map) {
        Integer roleId = map.get("roleId").get(0);
        List<Integer> authIdList = map.get("authIdList");
        authMapper.deleteOldByRoleId(roleId);
        if(authIdList != null && authIdList.size() != 0) {
            authMapper.insertNewRelationship(roleId, authIdList);
        }
    }

    @Override
    public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
        return authMapper.selectAssignedAuthNameByAdminId(adminId);
    }
}
