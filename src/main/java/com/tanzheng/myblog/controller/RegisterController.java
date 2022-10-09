package com.tanzheng.myblog.controller;

import com.tanzheng.myblog.service.LoginService;
import com.tanzheng.myblog.vo.Result;
import com.tanzheng.myblog.vo.params.LoginParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 诗乃
 * @since 2022-09-02
 */
@RestController
@RequestMapping("register")
public class RegisterController {

    @Resource
    LoginService loginService;

    @PostMapping
    public Result register(
            @RequestBody LoginParam loginParam
    ) {
        // sso单点登录，后期如果把登录注册功能提出去(单独的服务，可以独立提供接口服务)
        return loginService.register(loginParam);
    }

}
