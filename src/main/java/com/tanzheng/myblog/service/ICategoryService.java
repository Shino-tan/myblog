package com.tanzheng.myblog.service;

import com.tanzheng.myblog.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tanzheng.myblog.vo.CategoryVo;
import com.tanzheng.myblog.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 诗乃
 * @since 2022-09-04
 */
public interface ICategoryService extends IService<Category> {

    /**
     * @description 查找类别
     * @param categoryId 类别 id
     * @return CategoryVo
     */
    CategoryVo findCategoryById(long categoryId);

    /**
     * @description 查找所有类别
     * @return Result
     */
    Result findAll();

    /**
     * @description 实现文章分类
     * @return categories
     */
    Result findAllDetail();

    /**
     * @description 分类文章详情
     * @param id 文章id
     * @return categoryVo
     */
    Result categoriesDetailById(Long id);
}
