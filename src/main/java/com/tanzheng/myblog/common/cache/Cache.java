package com.tanzheng.myblog.common.cache;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
// 切点
public @interface Cache {

    // 过期时间
    long expire() default 1 * 60 * 1000;

    // 缓存标识 key
    String name() default "";

}
