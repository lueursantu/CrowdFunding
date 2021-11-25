package com.santu.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.santu.crowd.entity.Admin;

import java.util.List;

/**
 * @author Santu
 * @date 2021/11/12 10:04
 */
public interface AdminService {
    public int saveAdmin(Admin admin);
    public List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    Integer removeAdminByID(Integer id);
}
