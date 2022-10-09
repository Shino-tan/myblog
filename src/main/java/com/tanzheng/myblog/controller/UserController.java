package com.tanzheng.myblog.controller;

import com.tanzheng.myblog.service.ISysUserService;
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
@RequestMapping("users")
public class UserController {

    @Resource
    ISysUserService sysUserService;

    @GetMapping("currentUser")
    public Result currentUser(
            @RequestHeader("Authorization") String token
    ) {
        return sysUserService.getUserByToken(token);
    }

}
