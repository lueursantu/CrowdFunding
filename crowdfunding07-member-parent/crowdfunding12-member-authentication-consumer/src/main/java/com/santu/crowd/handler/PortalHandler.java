package com.santu.crowd.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Santu
 * @date 2021/12/23 15:54
 */
@Controller
public class PortalHandler {

    @RequestMapping("/")
    public String showPortalPage(){
        return "portal";
    }
}
