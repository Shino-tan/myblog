package com.tanzheng.myblog.controller;

import com.tanzheng.myblog.utils.QiniuUtils;
import com.tanzheng.myblog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 * @description 图片上传控制器
 * @author 诗乃
 * @since 2022-08-29
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Resource
    QiniuUtils qiniuUtils;

    @PostMapping
    public Result upload(
            @RequestParam("image") MultipartFile file   // MultipartFile spring 中专门用于接收文件的类型
    ) {
        // 原始文件名称，比如 aa。png
        String originalFilename = file.getOriginalFilename();
        // 获取唯一的文件名称
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        // 上传文件至七牛云中
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload) {
            return Result.success(QiniuUtils.url + fileName);
        }
        return Result.fail(20001, "上传失败");
    }

}
