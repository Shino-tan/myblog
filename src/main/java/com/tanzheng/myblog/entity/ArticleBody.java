package com.tanzheng.myblog.entity;

import lombok.Data;

/**
 * <p>
 * 实体类
 * </p>
 *
 * @author 诗乃
 * @since 2022-09-04
 */

@Data
public class ArticleBody {

    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}
