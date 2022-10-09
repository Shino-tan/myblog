package com.tanzheng.myblog.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

    // 由于有的文章的id是雪花算法生产的19位数字，初始查询返回的json数据中id为19位，
    // 而jsNumber类型最多16位，超出的位数不保证精度。导致前端再次查询文章时的请求参数id出错。
    //一定要记得加 要不然 会出现精度损失
//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo category;
}
