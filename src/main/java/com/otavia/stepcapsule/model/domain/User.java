package com.otavia.stepcapsule.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户信息
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码（加密）
     */
    private String userPassword;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户状态（默认为 0 正常，1 为已注销）
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 用户创建时间
     */
    private Date createTime;

    /**
     * 用户最近一次登录时间
     */
    private Date updateTime;

    /**
     * 用户登录次数
     */
    private Integer loginNumber;

    /**
     * 用户步步条数
     */
    private Integer stepsNumber;

    /**
     * 用户步步集个数
     */
    private Integer stepsGroupsNumber;

    /**
     * 用户角色（默认为 0 用户，1 为管理员，2 为会员）
     */
    private Integer userRole;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
