package com.santu.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Santu
 * @date 2021/12/27 16:31
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberVO {
    private String loginAcct;

    private String userPswd;

    private String username;

    private String email;

    private String phoneNum;

    private String code;
}
