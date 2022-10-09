package com.tanzheng.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tanzheng.myblog.entity.Tag;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 诗乃
 * @since 2022-08-29
 */
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * @description 根据文章 id 查询标签列表
     * @param articleId
     * @return Tag
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * @description 查找出现次数前 limit 的 tagId
     * @param limit
     * @return Long
     */
    List<Long> findHotsTagIds(int limit);

    /**
     * @description 通过已经查找到的最热标签 tagId，查询 tagName，tag对象
     * @param tagIds
     * @return Tag
     */
    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
