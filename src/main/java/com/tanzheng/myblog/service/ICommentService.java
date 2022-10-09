package com.tanzheng.myblog.service;

import com.tanzheng.myblog.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tanzheng.myblog.vo.Result;
import com.tanzheng.myblog.vo.params.CommentParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 诗乃
 * @since 2022-09-05
 */
public interface ICommentService extends IService<Comment> {

    /**
     * @description 根据文章id 查询所有的评论列表
     * @param articleId articleId
     * @return Result
     */
    Result commentsByArticleId(Long articleId);

    Result comment(CommentParam commentParam);
}
