package com.santu.crowd.handler;

import com.santu.crowd.constant.CrowdConstant;
import com.santu.crowd.entity.po.MemberPO;
import com.santu.crowd.service.api.MemberService;
import com.santu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Santu
 * @date 2021/12/22 8:49
 */
@RestController
public class MemberProviderHandler {

    @Autowired
    MemberService memberService;

    @RequestMapping("/get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct) {
        try {
            MemberPO memberPO = memberService.getMemberPOByLoginAcct(loginacct);
            return ResultEntity.successWithData(memberPO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
    @RequestMapping("/save/memberpo/remote")
    public ResultEntity<String> saveMemberPORemote(@RequestBody() MemberPO memberPO){
        try {
            memberService.saveMemberPO(memberPO);
            return ResultEntity.successWithoutData();

        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof DuplicateKeyException){
                return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_UES);
            }else{
                return ResultEntity.failed(e.getMessage());
            }
        }
    }
}
