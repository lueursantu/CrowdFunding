package com.santu.crowd.service.api;

import com.santu.crowd.entity.Auth;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author Santu
 * @date 2021/12/3 9:08
 */
public interface AuthService {
    List<Auth> getAll();
    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);
    void savaAuthRoleRelationship(Map<String, List<Integer>> map);
    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
