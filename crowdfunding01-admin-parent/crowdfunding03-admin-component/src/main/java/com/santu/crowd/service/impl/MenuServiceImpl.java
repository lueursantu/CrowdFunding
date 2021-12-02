package com.santu.crowd.service.impl;

import com.santu.crowd.entity.Menu;
import com.santu.crowd.entity.MenuExample;
import com.santu.crowd.mapper.MenuMapper;
import com.santu.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Santu
 * @date 2021/11/30 21:16
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }

    @Override
    public int addMenuItem(Menu menu) {
        return menuMapper.insert(menu);
    }

    @Override
    public int updateMenuItem(Menu menu) {
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public int removeMenuItem(int id) {
        return menuMapper.deleteByPrimaryKey(id);
    }
}
