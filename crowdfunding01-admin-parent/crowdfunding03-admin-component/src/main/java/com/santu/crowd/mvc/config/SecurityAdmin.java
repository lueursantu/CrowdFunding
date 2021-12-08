package com.santu.crowd.mvc.config;

import com.santu.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author Santu
 * @date 2021/12/8 16:06
 */
public class SecurityAdmin extends User {
    private static final long serialVersionUID = 232L;

    private Admin originalAdmin;

    public SecurityAdmin(Admin originalAdmin, Collection<GrantedAuthority> authorities) {
        super(originalAdmin.getUserName(), originalAdmin.getUserPswd(), authorities);
        this.originalAdmin = originalAdmin;
        // 擦除密码
        this.originalAdmin.setUserPswd(null);
    }

    public Admin getOriginalAdmin() {
        return originalAdmin;
    }
}
