package com.santu.crowd.mvc.config;

import com.santu.crowd.entity.Admin;
import com.santu.crowd.entity.Role;
import com.santu.crowd.service.api.AdminService;
import com.santu.crowd.service.api.AuthService;
import com.santu.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Santu
 * @date 2021/12/8 16:21
 */
@Component
public class CrowdUserDetailService implements UserDetailsService {
    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.getAdminByLoginAcct(username);
        Integer adminId = admin.getId();
        // 获取角色权限
        List<Role> roleList = roleService.getAssignedRole(adminId);
        List<String> authNameList = authService.getAssignedAuthNameByAdminId(adminId);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roleList) {
            String roleName = role.getName();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));
        }
        for (String authName : authNameList) {
            authorities.add(new SimpleGrantedAuthority(authName));
        }
        return new SecurityAdmin(admin, authorities);
    }
}
