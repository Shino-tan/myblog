package com.tanzheng.myblog.controller;


import com.tanzheng.myblog.common.aop.LogAnnotation;
import com.tanzheng.myblog.common.cache.Cache;
import com.tanzheng.myblog.service.IArticleService;
import com.tanzheng.myblog.vo.ArticleVo;
import com.tanzheng.myblog.vo.Result;
import com.tanzheng.myblog.vo.params.ArticleParam;
import com.tanzheng.myblog.vo.params.PageParams;
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
// 以 json 串数据进行交互
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Resource
    private IArticleService articleService;

    /**
     * @description 首页 文章列表
     * @param pageParams pageParams
     * @return Result
     */
    @PostMapping
    @LogAnnotation(module = "文章列表", operation = "获取文章列表")   // 添加此注解表示要对此接口记录日志
    @Cache(expire = 5 * 60 * 1000, name = "listArticle")
    public Result listArticle(
            @RequestBody PageParams pageParams
    ) {
        return articleService.listArticle(pageParams);
    }

    /**
     * @description 首页 最热文章
     * @return Result
     */
    @PostMapping("/hot")
    @Cache(expire = 5 * 60 * 1000, name = "hot_article")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * @description 首页 最新文章
     * @return Result
     */
    @PostMapping("/new")
    public Result newArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * @description 首页 文章归档
     * @return Result
     */
    @PostMapping("/listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    /**
     * @description 根据文章 id 获取文章
     * @param id 路径参数，文章 id
     * @return Result
     */
    @PostMapping("/view/{id}")
    public Result findArticleById(
            @PathVariable("id") Long id
    ) {
        ArticleVo articleVo = articleService.findArticleById(id);
        return Result.success(articleVo);
    }

    // /articles/publish
    @PostMapping("/publish")
    public Result publish(
            @RequestBody ArticleParam articleParam
    ) {
        return articleService.publish(articleParam);
    }



}
