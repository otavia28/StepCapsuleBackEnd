package com.otavia.stepcapsule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.otavia.stepcapsule.common.ErrorCode;
import com.otavia.stepcapsule.exception.BusinessException;
import com.otavia.stepcapsule.mapper.UserMapper;
import com.otavia.stepcapsule.model.domain.User;
import com.otavia.stepcapsule.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.otavia.stepcapsule.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务方法的实现
 *
 * @author huchenkun
 * @description 针对表【user(用户信息)】的数据库操作Service实现
 * @createDate 2023-10-28 23:08:07
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值，给密码加密
     */
    private static final String SALT = "otavia";

    @Override
    @Transactional
    public Integer userRegister(String userAccount, String userPassword, String checkPassword, String userName) {

        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, userName)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 5) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度过短");
        }
        // 账号中不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher1 = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher1.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号中存在特殊字符");
        }
        // 昵称中不能包含特殊字符
        Matcher matcher2 = Pattern.compile(validPattern).matcher(userName);
        if (matcher2.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "昵称中存在特殊字符");
        }
        // 密码和校验密码必须一致
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码和校验密码不一致");
        }
        // 账户不能重复，但这一步需要查询数据库，建议放到最后。
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();     // QueryWrapper 是 MyBatis-Plus 中用于构造查询条件的类
        queryWrapper1.eq("userAccount", userAccount);        // 在 QueryWrapper 对象中添加一个查询条件
        long count1 = userMapper.selectCount(queryWrapper1);                      // 查询数据库中满足条件的记录数量
        if (count1 > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        // 昵称不能重复，但这一步需要查询数据库，建议放到最后。
        QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();     // QueryWrapper 是 MyBatis-Plus 中用于构造查询条件的类
        queryWrapper2.eq("userName", userName);        // 在 QueryWrapper 对象中添加一个查询条件
        long count2 = userMapper.selectCount(queryWrapper2);                      // 查询数据库中满足条件的记录数量
        if (count2 > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "昵称重复");
        }

        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 3. 向数据库中插入用户数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUserName(userName);
        // 如果返回的是 null 的话会产生开箱错误，解决如下，当为 null 时返回 -1
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }

        return user.getId();
    }

    @Override
    @Transactional
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {

        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 5) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度过短");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度过短");
        }
        // 账号中不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号中存在特殊字符");
        }

        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 3. 在数据库中查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount can not match userPassword.");
            throw new BusinessException(ErrorCode.NULL_ERROR, "用户不存在");
        }

        // 4. 用户脱敏
        User safetyUser = getSafetyUser(user);

        // 5. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    @Override
    @Transactional
    public Integer userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }


    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUserName(originUser.getUserName());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setUserEmail(originUser.getUserEmail());
        safetyUser.setIsDelete(originUser.getIsDelete());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUpdateTime(originUser.getUpdateTime());
        safetyUser.setLoginNumber(originUser.getLoginNumber());
        safetyUser.setStepsNumber(originUser.getStepsNumber());
        safetyUser.setStepsGroupsNumber(originUser.getStepsGroupsNumber());
        safetyUser.setUserRole(originUser.getUserRole());
        return safetyUser;
    }
}




