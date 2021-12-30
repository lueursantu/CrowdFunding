package com.santu.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Santu
 * @date 2021/12/28 15:34
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberLoginVO implements Serializable {
    private Integer id;
    private String username;
    private String email;
}
