package com.santu.crowd.mvc.handler;

import com.santu.crowd.entity.Menu;
import com.santu.crowd.service.api.MenuService;
import com.santu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Santu
 * @date 2021/11/30 21:17
 */
@RestController
public class MenuHandler {

    @Autowired
    MenuService menuService;

    @RequestMapping("/menu/do/get.json")
    public ResultEntity<Menu> getWholeTree(){
        // 通过service层方法得到全部Menu对象的List
        List<Menu> menuList = menuService.getAll();

        // 声明一个Menu对象root，用来存放找到的根节点
        Menu root = null;

        // 使用map表示每一个菜单与id的对应关系
        Map<Integer,Menu> menuMap = new HashMap<>();

        // 将菜单id与菜单对象以K-V对模式存入map
        for(Menu menu: menuList){
            menuMap.put(menu.getId(),menu);
        }

        for (Menu menu : menuList){
            Integer pid = menu.getPid();

            if (pid == null){
                // pid为null表示该menu是根节点
                root = menu;
                continue;
            }
            // 通过当前遍历的menu的pid得到其父节点
            Menu father = menuMap.get(pid);
            // 为父节点添加子节点为当前节点
            father.getChildren().add(menu);
        }

        return ResultEntity.successWithData(root);
    }

    @RequestMapping("/menu/save.json")
    public ResultEntity addMenuItem(@RequestParam("pid") Integer pid,
                                    @RequestParam("name") String name,
                                    @RequestParam("url") String url,
                                    @RequestParam("icon") String icon){
        Menu menu = new Menu(null, pid, name, url, icon, null, null);
        menuService.addMenuItem(menu);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/menu/update.json")
    public ResultEntity updateMenuItem(@RequestParam("id") Integer id,
                                       @RequestParam("name") String name,
                                       @RequestParam("url") String url,
                                       @RequestParam("icon") String icon){
        Menu menu = new Menu(id, null, name, url, icon, null, null);
        menuService.updateMenuItem(menu);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/menu/remove.json")
    public ResultEntity removeMenuItem(@RequestParam("id") int id){
        menuService.removeMenuItem(id);
        return ResultEntity.successWithoutData();
    }
}
