package com.tanzheng.myblog.vo.params;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.tanzheng.myblog.vo.CategoryVo;
import com.tanzheng.myblog.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}
