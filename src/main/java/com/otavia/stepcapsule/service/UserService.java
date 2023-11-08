package com.otavia.stepcapsule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.otavia.stepcapsule.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
 * 业务逻辑：用户服务，只定义方法而不实现方法
 *
 * @author huchenkun
 * @description 针对表【user(用户信息)】的数据库操作Service
 * @createDate 2023-10-28 23:08:07
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册 需要账户、密码、校验密码、昵称
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @param userName      昵称
     * @return
     */
    Integer userRegister(String userAccount, String userPassword, String checkPassword, String userName);

    /**
     * 用户登录 需要账户、密码
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @return
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    Integer userLogout(HttpServletRequest request);
}
