package com.tanzheng.myblog.controller;


import com.tanzheng.myblog.service.LoginService;
import com.tanzheng.myblog.vo.Result;
import com.tanzheng.myblog.vo.params.LoginParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 诗乃
 * @since 2022-08-29
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private LoginService loginService;


    @PostMapping
    public Result login(
            @RequestBody LoginParam loginParam
    ) {
        // 登录：需要验证用户    访问用户表
        return loginService.login(loginParam);
    }



}
