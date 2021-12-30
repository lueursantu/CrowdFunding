package com.santu.crowd.util;

import com.santu.crowd.constant.CrowdConstant;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Santu
 * @date 2021/12/29 9:48
 */
public class AccessPassResources {
    // 保存不被过滤的请求
    public static final Set<String> PASS_RES_SET = new HashSet<>();

    // 静态代码块中加入不被过滤的内容
    static {
        PASS_RES_SET.add("/");
        PASS_RES_SET.add("/auth/member/to/reg/page.html");
        PASS_RES_SET.add("/auth/member/to/login/page.html");
        PASS_RES_SET.add("/auth/member/send/short/message.json");
        PASS_RES_SET.add("/auth/member/do/register.html");
        PASS_RES_SET.add("/auth/member/do/login.html");
        PASS_RES_SET.add("/auth/member/do/logout.html");
        PASS_RES_SET.add("/error");
        PASS_RES_SET.add("/favicon.ico");
    }

    // 保存不被过滤的静态资源
    public static final Set<String> STATIC_RES_SET = new HashSet<>();

    // 静态代码块中加入不被过滤的内容
    static {
        STATIC_RES_SET.add("bootstrap");
        STATIC_RES_SET.add("css");
        STATIC_RES_SET.add("fonts");
        STATIC_RES_SET.add("img");
        STATIC_RES_SET.add("jquery");
        STATIC_RES_SET.add("layer");
        STATIC_RES_SET.add("script");
        STATIC_RES_SET.add("ztree");
    }

    /**
     *
     * @param servletPath 当前请求的路径 就是localhost:8080/aaa/bbb/ccc中的 “aaa/bbb/ccc”
     * @return true: 表示该资源是静态资源; false: 表示该资源不是静态资源
     */
    public static boolean judgeIsStaticResource(String servletPath){

        // 先判断字符串是否为空
        if (servletPath == null || servletPath.length() == 0){
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        // 通过“/”来分割得到的请求路径
        String[] split = servletPath.split("/");

        // 请求路径是”/“的情况
        if (split.length == 1){
            return true;
        }

        // split[0]是一个空字符串，因此取split[1]，相当于/aaa/bbb/ccc的“aaa”
        String path = split[1];

        // 判断是否包含得到的请求的第一个部分
        return STATIC_RES_SET.contains(path);
    }
}
