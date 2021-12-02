package com.santu.crowd.service.api;

import com.santu.crowd.entity.Menu;

import java.util.List;

/**
 * @author Santu
 * @date 2021/11/30 21:16
 */
public interface MenuService {
    List<Menu> getAll();
    int addMenuItem(Menu menu);
    int updateMenuItem(Menu menu);
    int removeMenuItem(int id);
}
