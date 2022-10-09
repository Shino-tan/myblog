package com.tanzheng.myblog.controller;

import com.tanzheng.myblog.entity.SysUser;
import com.tanzheng.myblog.utils.UserThreadLocal;
import com.tanzheng.myblog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 诗乃
 * @since 2022-08-29
 */
@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }


}
