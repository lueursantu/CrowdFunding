package com.santu.crowd.service.impl;

import com.santu.crowd.entity.po.MemberPO;
import com.santu.crowd.entity.po.MemberPOExample;
import com.santu.crowd.mapper.MemberPOMapper;
import com.santu.crowd.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Santu
 * @date 2021/12/22 8:52
 */
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberPOByLoginAcct(String loginAcct) {
        MemberPOExample example = new MemberPOExample();

        MemberPOExample.Criteria criteria = example.createCriteria();

        criteria.andLoginAcctEqualTo(loginAcct);

        List<MemberPO> memberPOS = memberPOMapper.selectByExample(example);

        // 判断得到的List是否为空，为空则返回null，防止后面调用的时候触发空指针异常
//        if (memberPOS == null || memberPOS.size() == 0){
//            return null;
//        }

        // List非空，则返回第一个（因为LoginAcct是唯一的）
        MemberPO memberPO = memberPOS.get(0);
        return memberPO;
    }
}
