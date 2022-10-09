package com.tanzheng.myblog.controller;


import com.tanzheng.myblog.service.IAdminService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 诗乃
 * @since 2022-09-03
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    IAdminService adminService;

}
