package com.su.mybatis.nospring.model;

import lombok.*;
import lombok.extern.log4j.Log4j2;

/**
 * @Author: suzheng
 * @Version:
 * @Package: com.su.mybatis.nospring
 * @Company: SIBU_KANGER
 * @Description:
 * @Date: 2016/06/12
 */
@NoArgsConstructor
@Log4j2
@Data
@ToString
public class User {
    private int id;
    private String name;
    private String dept;
    private String phone;
    private String website;
}
