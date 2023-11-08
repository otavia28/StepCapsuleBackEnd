package com.otavia.stepcapsule.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 接收请求参数
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -6042885651064522503L;

    private String userAccount;

    private String userPassword;
}
