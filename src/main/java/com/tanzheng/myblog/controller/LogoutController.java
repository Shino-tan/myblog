package com.tanzheng.myblog.controller;

import com.tanzheng.myblog.service.LoginService;
import com.tanzheng.myblog.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/logout")
public class LogoutController {

    @Resource
    LoginService loginService;

    @GetMapping
    public Result logout(
            @RequestHeader("Authorization") String token
    ) {
        return loginService.logout(token);
    }

}
