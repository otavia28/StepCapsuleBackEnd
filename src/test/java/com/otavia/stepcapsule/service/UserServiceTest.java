package com.otavia.stepcapsule.service;

import com.otavia.stepcapsule.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户服务测试
 */
@SpringBootTest
class UserServiceTest {


    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUserName("test01");
        user.setAvatarUrl("https://cn.bing.com/images/search?view=detailV2&ccid=Wqp%2f%2bOOx&id=6A655D8DB5D4AB09F76440D24FB219503D595496&thid=OIP.Wqp_-OOxHtRvg9dpm5xBEwAAAA&mediaurl=https%3a%2f%2fp.qqan.com%2fup%2f2018-3%2f15210182804289411.jpg&exph=400&expw=400&q=%e5%a4%b4%e5%83%8f+%e5%8d%a1%e9%80%9a%e5%8a%a8%e6%bc%ab&simid=608010569029402020&FORM=IRPRST&ck=48666980671C058C4033EF6AAB486D90&selectedIndex=231");
        user.setUserAccount("003");
        user.setUserPassword("xxx");
        user.setUserEmail("123456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister() {
        // 密码为空
        String userAccount = "abcabc";
        String userPassword = "";
        String checkPassword = "12345678";
        String userName = "xiaoming";
        long result = userService.userRegister(userAccount, userPassword, checkPassword, userName);
        Assertions.assertEquals(-1, result);

        // 账户小于 5 位
        userAccount = "abc";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, userName);
        Assertions.assertEquals(-1, result);

        // 密码小于 8 位
        userAccount = "abcabc";
        userPassword = "123456";
        checkPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword, userName);
        Assertions.assertEquals(-1, result);

        // 账户中有特殊字符
        userAccount = "abcabc!!";
        userPassword = "12345678";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, userName);
        Assertions.assertEquals(-1, result);

        // 昵称中有特殊字符
        userAccount = "abcabc";
        userName = "xiaoming!!";
        result = userService.userRegister(userAccount, userPassword, checkPassword, userName);
        Assertions.assertEquals(-1, result);

        // 密码和校验密码不一致
        checkPassword = "123456789";
        userName = "xiaoming";
        result = userService.userRegister(userAccount, userPassword, checkPassword, userName);
        Assertions.assertEquals(-1, result);

        // 账户重复
        userAccount = "001001";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, userName);
        Assertions.assertEquals(-1, result);

        // 添加成功
        userAccount = "002002";
        result = userService.userRegister(userAccount, userPassword, checkPassword, userName);
        Assertions.assertTrue(result > 0);
    }
}
