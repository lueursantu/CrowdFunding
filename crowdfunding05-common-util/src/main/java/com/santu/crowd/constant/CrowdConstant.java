package com.santu.crowd.constant;

/**
 * @author Santu
 * @date 2021/11/16 11:21
 */
public class CrowdConstant {
    public static final String MESSAGE_LOGIN_FAILED = "账号或密码错误";
    public static final String MESSAGE_STRING_INVALIDATE = "字符串不能为空或长度为0！";
    public static final String MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE = "错误！数据库中存在重复数据！";
    public static final String MESSAGE_ACCESS_FORBIDDEN = "还未登录，禁止访问受保护资源！";
    public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_UES = "抱歉，这个账号已经被使用！";

    public static final String ATTR_NAME_EXCEPTION = "exception";
    public static final String ATTR_NAME_LOGIN_ADMIN = "loginAdmin";
    public static final String ATTR_NAME_PAGE_INFO = "pageInfo";

    public static final String REDIS_CODE_PREFIX = "REDIS_CODE_PREFIX" ;
    public static final String ATTR_NAME_MESSAGE = "message";
    public static final String MESSAGE_CODE_NOT_EXIST = "验证码无效！请检查是否输入了正确的手机号";
    public static final String MESSAGE_CODE_INVALID = "验证码错误";
    public static final String ATTR_NAME_LOGIN_MEMBER = "loginMember";
    public static final String MESSAGE_HEADER_PIC_EMPTY = "头图不能为空！";
    public static final String MESSAGE_HEADER_PIC_UPLOAD_FAILED = "头图上传失败，请重试！";
    public static final String MESSAGE_DETAIL_PIC_EMPTY = "详情图片不能为空！";
    public static final String MESSAGE_DETAIL_PIC_UPLOAD_FAILED = "详情图片上传失败，请重试！";
    public static final String ATTR_NAME_TEMPLE_PROJECT = "templeProject";
    public static final String MESSAGE_RETURN_PIC_EMPTY = "上传回报图片不能为空！";
    public static final String MESSAGE_TEMPLE_PROJECT_MISSING = "临时ProjectVO对象未找到！";
    public static final String ATTR_NAME_PORTAL_TYPE_LIST = "portal_type_list";
    public static final String ATTR_NAME_DETAIL_PROJECT = "detailProjectVO";
}
