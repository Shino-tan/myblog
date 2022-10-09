package com.tanzheng.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tanzheng.myblog.entity.Article;
import com.tanzheng.myblog.vo.ArticleVo;
import com.tanzheng.myblog.vo.Result;
import com.tanzheng.myblog.vo.params.ArticleParam;
import com.tanzheng.myblog.vo.params.PageParams;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 诗乃
 * @since 2022-08-29
 */
public interface IArticleService extends IService<Article> {

    /**
     * @description 分页查询文章列表
     * @param pageParams pageParams
     * @return Result
     */
    Result listArticle(PageParams pageParams);

    /**
     * @description 最热文章
     * @param limit limit
     * @return Result
     */
    Result hotArticle(int limit);

    /**
     * @description 最新文章
     * @param limit limit
     * @return Result
     */
    Result newArticles(int limit);

    /**
     * @description 文章归档
     * @return Result
     */
    Result listArchives();

    /**
     * @description 查看文章详情
     * @param id 文章 id
     * @return ArticleVo
     */
    ArticleVo findArticleById(Long id);

    /**
     * @description 发布文章
     * @param articleParam articleParam
     * @return articleVo
     */
    Result publish(ArticleParam articleParam);
}
