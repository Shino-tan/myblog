package com.tanzheng.myblog.controller;


import com.tanzheng.myblog.service.ICommentService;
import com.tanzheng.myblog.vo.Result;
import com.tanzheng.myblog.vo.params.CommentParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 诗乃
 * @since 2022-09-05
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Resource
    ICommentService commentService;

    // /comments/article/{id}
    @GetMapping("/article/{id}")
    public Result comments(
            @PathVariable("id") Long articleId
    ) {
        return commentService.commentsByArticleId(articleId);
    }

    // /comments/create/change
    @PostMapping("/create/change")
    public Result comment(
            @RequestBody CommentParam commentParam
    ) {
        return commentService.comment(commentParam);
    }


}
