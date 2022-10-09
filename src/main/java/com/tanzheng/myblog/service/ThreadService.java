package com.tanzheng.myblog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tanzheng.myblog.entity.Article;
import com.tanzheng.myblog.mapper.ArticleMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    // 期望此操作在线程池执行，不会影响原有的主线程
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {

        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId, article.getId());
        // 设置一个，为了在多线程的环境下，线程安全     乐观锁
        queryWrapper.eq(Article::getViewCounts, viewCounts);
        // update article set view_count = 100 where view_count = 99 and id = 11
        articleMapper.update(articleUpdate, queryWrapper);

//        try {
//            // 睡眠5秒 证明不会影响主线程的使用
//            Thread.sleep(5000);
//            System.out.println("更新完成了。。。。。。");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }


}
