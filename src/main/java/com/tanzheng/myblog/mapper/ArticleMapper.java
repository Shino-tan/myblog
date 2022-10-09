package com.tanzheng.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tanzheng.myblog.entity.Article;
import com.tanzheng.myblog.entity.dos.Archives;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 诗乃
 * @since 2022-08-29
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();

    IPage<Article> listArticle(
            Page<Article> page,
            Long categoryId,
            Long tagId,
            String year,
            String month
    );

}
