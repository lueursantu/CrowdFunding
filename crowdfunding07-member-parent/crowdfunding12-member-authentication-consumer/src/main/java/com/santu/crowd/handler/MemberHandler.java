package com.santu.crowd.handler;

import com.santu.crowd.api.MySQLRemoteService;
import com.santu.crowd.api.RedisRemoteService;
import com.santu.crowd.constant.CrowdConstant;
import com.santu.crowd.entity.po.MemberPO;
import com.santu.crowd.entity.vo.MemberLoginVO;
import com.santu.crowd.entity.vo.MemberVO;
import com.santu.crowd.util.CrowdUtil;
import com.santu.crowd.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Santu
 * @date 2021/12/26 19:07
 */
@Controller
public class MemberHandler {

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @ResponseBody
    @RequestMapping("/auth/member/send/short/message.json")
    public ResultEntity authMemberSendShortMessage(@RequestParam("email") String email){
        ResultEntity<String> shortMessageResult = CrowdUtil.sendShortMessage(email);
        if (ResultEntity.SUCCESS.equals(shortMessageResult.getResult())){
            String key = CrowdConstant.ATTR_REDIS_CODE_PREFIX + email;
            String code = shortMessageResult.getData();
            ResultEntity<String> redisResult =
                    redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 5L, TimeUnit.MINUTES);
            if (ResultEntity.SUCCESS.equals(redisResult.getResult())){
                return ResultEntity.successWithoutData();
            } else {
                return ResultEntity.failed(redisResult.getMessage());
            }
        } else {
            return ResultEntity.failed(shortMessageResult.getMessage());
        }
    }

    @RequestMapping("/auth/member/do/register.html")
    public String authMemberDoRegister(MemberVO memberVO, ModelMap modelMap){
        String email = memberVO.getEmail();
        ResultEntity<String> redisResult = redisRemoteService.getRedisValueByKeyRemote(CrowdConstant.ATTR_REDIS_CODE_PREFIX + email);
        if(ResultEntity.FAILED.equals(redisResult.getResult())){
            modelMap.addAttribute("message", CrowdConstant.MESSAGE_CODE_NOT_EXIST);
            return "member-reg";
        }
        if (!Objects.equals(redisResult.getData(), memberVO.getCode())){
            modelMap.addAttribute("message", CrowdConstant.MESSAGE_CODE_INVALID);
            return "member-reg";
        }
        redisRemoteService.removeRedisKeyByKeyRemote(CrowdConstant.ATTR_REDIS_CODE_PREFIX + email);
        // 1、加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String formPwd = memberVO.getUserPswd();
        String encode = bCryptPasswordEncoder.encode(formPwd);

        // 2、将加密后的密码放入MemberVO对象
        memberVO.setUserPswd(encode);

        MemberPO memberPO = new MemberPO();
        BeanUtils.copyProperties(memberVO, memberPO);

        ResultEntity<String> mySqlResult = mySQLRemoteService.saveMemberPORemote(memberPO);
        if (Objects.equals(mySqlResult, ResultEntity.FAILED)){
            modelMap.addAttribute("message", mySqlResult.getMessage());
            return "member-reg";
        }
        return "redirect:/auth/member/to/login/page.html";
    }

    @RequestMapping("/auth/member/do/Login.html")
    public String authMemberDoLogin(@RequestParam("loginAcct") String loginAcct,
                                    @RequestParam("userPswd") String userPswd,
                                    HttpSession session, ModelMap modelMap){
        ResultEntity<MemberPO> resultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginAcct);
        MemberPO memberPO = resultEntity.getData();
        String dbPswd = memberPO.getUserPswd();

        // 匹配密码
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches(userPswd, dbPswd);
        if(!matches){
            modelMap.addAttribute("message", CrowdConstant.MESSAGE_MEMBER_FAILED);
            return "member-login";
        }

        MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(), memberPO.getUsername(), memberPO.getEmail());
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER, memberLoginVO);

        return "redirect:/";

    }
}
