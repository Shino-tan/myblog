package com.tanzheng.myblog.service;

import com.tanzheng.myblog.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tanzheng.myblog.vo.Result;
import com.tanzheng.myblog.vo.TagVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 诗乃
 * @since 2022-08-29
 */
public interface ITagService extends IService<Tag> {

    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);

    /**
     * @description 查询所有的文章标签
     * @return tags
     */
    Result findAll();

    Result findAllDetail();

    Result findDetailById(Long id);
}
