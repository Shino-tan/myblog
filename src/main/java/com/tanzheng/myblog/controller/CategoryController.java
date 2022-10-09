package com.tanzheng.myblog.controller;


import com.tanzheng.myblog.service.ICategoryService;
import com.tanzheng.myblog.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 诗乃
 * @since 2022-09-04
 */
@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Resource
    private ICategoryService categoryService;

    // /categorys
    @GetMapping
    public Result categories() {
        return categoryService.findAll();
    }

    // /categorys/detail
    @GetMapping("/detail")
    public Result categoriesDetail() {
        return categoryService.findAllDetail();
    }

    // /category/detail/{id}
    @GetMapping("detail/{id}")
    public Result categoriesDetailById(
            @PathVariable("id") Long id
    ) {
        return categoryService.categoriesDetailById(id);
    }

}
