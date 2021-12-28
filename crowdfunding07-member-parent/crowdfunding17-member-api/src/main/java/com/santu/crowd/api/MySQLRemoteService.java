package com.santu.crowd.api;

import com.santu.crowd.entity.po.MemberPO;
import org.springframework.cloud.openfeign.FeignClient;
import com.santu.crowd.util.ResultEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Santu
 * @date 2021/12/21 15:39
 */
@FeignClient("crowd-mysql")
public interface MySQLRemoteService {
    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginAcct);

    @RequestMapping("/save/memberpo/remote")
    ResultEntity<String> saveMemberPORemote(@RequestBody() MemberPO memberPO);
}
