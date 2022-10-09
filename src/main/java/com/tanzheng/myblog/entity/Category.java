package com.tanzheng.myblog.entity;

import lombok.Data;

/**
 * <p>
 *  实体类
 * </p>
 *
 * @author 诗乃
 * @since 2022-09-04
 */
@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
