package com.santu.crowd.service.api;

import com.santu.crowd.entity.po.MemberPO;

/**
 * @author Santu
 * @date 2021/12/22 8:51
 */
public interface  MemberService {
    MemberPO getMemberPOByLoginAcct(String loginAcct);
}
