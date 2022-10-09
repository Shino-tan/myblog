package com.tanzheng.myblog.controller;


import com.tanzheng.myblog.service.ITagService;
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
 * @since 2022-08-29
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    @Resource
    ITagService tagService;

    @GetMapping("/hot")
    public Result hot() {
        int limit = 6;  // 查询最热门的 6个标签
        return tagService.hots(limit);
    }

    @GetMapping
    public Result findAll(){
        return tagService.findAll();
    }

    // /tags/detail
    @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }

    // /tags/detail/{id}
    @GetMapping("detail/{id}")
    public Result findDetailById(
            @PathVariable("id") Long id
    ) {
        return tagService.findDetailById(id);
    }



}
