package com.tanzheng.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanzheng.myblog.entity.Article;
import com.tanzheng.myblog.entity.ArticleBody;
import com.tanzheng.myblog.entity.ArticleTag;
import com.tanzheng.myblog.entity.SysUser;
import com.tanzheng.myblog.entity.dos.Archives;
import com.tanzheng.myblog.mapper.ArticleBodyMapper;
import com.tanzheng.myblog.mapper.ArticleMapper;
import com.tanzheng.myblog.mapper.ArticleTagMapper;
import com.tanzheng.myblog.service.*;
import com.tanzheng.myblog.utils.UserThreadLocal;
import com.tanzheng.myblog.vo.ArticleBodyVo;
import com.tanzheng.myblog.vo.ArticleVo;
import com.tanzheng.myblog.vo.Result;
import com.tanzheng.myblog.vo.TagVo;
import com.tanzheng.myblog.vo.params.ArticleParam;
import com.tanzheng.myblog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 诗乃
 * @since 2022-08-29
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ITagService tagService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ArticleBodyMapper articleBodyMapper;
    @Resource
    private ICategoryService categoryService;
    @Resource
    private ThreadService threadService;
    @Resource
    private ArticleTagMapper articleTagMapper;

    // 将数据库中的 List<Article> 转换为 List<ArticleVo>
    // 因为我们的 vue 前端中的 data 格式是 ArticleVo

    /**
     * @param records r
     * @return List<ArticleVo>
     * @description 将 vo 对象转换为 Result
     */
    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records
        ) {

            articleVoList.add(copy(record, isTag, isAuthor, false, false));
        }
        return articleVoList;
    }

    // 重载
    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records
        ) {
            articleVoList.add(copy(record, isTag, isAuthor, isBody, isCategory));
        }
        return articleVoList;
    }

    /**
     * @param article, isTag, isAuthor
     * @return ArticleVo
     * @description 将 article 对象转换为 vo 对象，isTag, isAuthor 是否有标签or作者属性
     */
    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        BeanUtils.copyProperties(article, articleVo);   // article -> articleVo
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        // 不是所有接口都需要 标签，作者信息
        if (isTag) {
            // 文章对应的 id
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }

        if (isAuthor) {
            // 文章对应的作者
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }

        if (isBody) {
            // 文章内容
            ArticleBodyVo articleBody = findArticleBodyById(article.getId());
            articleVo.setBody(articleBody);
        }

        if (isCategory) {
            // 类别信息
            long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }

        return articleVo;
    }

    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage = this.articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth()
        );
        return Result.success(copyList(articleIPage.getRecords(),true,true));
    }

//    @Override
//    public Result listArticle(PageParams pageParams) {
//
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        QueryWrapper<Article> queryWrapper1 = new QueryWrapper<>();
//
//        // 查询文章的参数 加上分类id，判断不为空 加上分类条件
//        if (pageParams.getCategoryId() != null) {
//            // and category_id = #{categoryId}
//            queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
//        }
//
//        List<Long> articleIdList = new ArrayList<>();
//        if (pageParams.getTagId() != null) {
//            // 加入标签，条件查询
//            // article 表中，并没有 tag 字段，一个文章有多个标签
//            // article_tag  article_id & tag_id  关联表（1：n）
//            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//            for (ArticleTag articleTag : articleTags
//                 ) {
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if (articleIdList.size() != 0) {
//                // and id in(1, 2, 3)
//                queryWrapper.in(Article::getId, articleIdList);
//            }
//        }
//
//        // 是否置顶排序
//        // order by create_date desc
//        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
//
//        // 1.分页查询数据，article数据库表
//        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//        List<Article> records = articlePage.getRecords();
//
//        // 不能直接返回 List
//        List<ArticleVo> articleVoList = copyList(records, true, true);
//        return Result.success(articleVoList);
//    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit " + limit);
        // select id, title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit " + limit);
        // select id,title from article order by create_date desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    @Override
    public ArticleVo findArticleById(Long id) {
        /*
            1. 根据文章 id，查询文章信息
            2. 根据 bodyId 和 categoryId 去做关联查询
         */
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = copy(article, true, true, true, true);
        // 查看完文章了，新增阅读数，有没有问题？
        // 查看完文章之后，本应该直接返回数据了，这时候做了一个更新操作，更新是加写锁的，就会阻塞其他的读操作，性能就会比较低
        // 更新 增加了此次接口的 耗时，如果一旦更新出问题，不难影响查看文章的操作
        // 线程池  可以把更新操作，扔到线程池中去执行，和主线程就不相关了
        threadService.updateArticleViewCount(articleMapper, article);
        return articleVo;
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        LambdaQueryWrapper<ArticleBody> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleBody::getArticleId, bodyId);
        ArticleBody articleBody = articleBodyMapper.selectOne(queryWrapper);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    @Override
    @Transactional
    public Result publish(ArticleParam articleParam) {
        // 此接口要加入到登录拦截当中
        SysUser sysUser = UserThreadLocal.get();
        /*
            1. 发布文章，目的，构建 Article 对象
            2. 作者id -- 当前的登录用户
            3. 标签 -- 要将标签加入到，关联列表当中（标签文章关联表）
            4. body 内容存储，article bodyId
         */
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        article.setWeight(Article.Article_Common);
        article.setBodyId(-1L);

        // 插入之后，回生成一个文章 id
        this.articleMapper.insert(article);

        // 获取 tag
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(Long.parseLong(tag.getId()));
                this.articleTagMapper.insert(articleTag);
            }
        }

        // 获取 body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBody.setArticleId(article.getId());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        return Result.success(articleVo);
    }

}
