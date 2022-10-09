package com.tanzheng.myblog.utils;

import com.tanzheng.myblog.entity.SysUser;

/**
 * <p>
 *  ThreadLocal保存用户信息工具类
 * </p>
 *
 * @author 诗乃
 * @since 2022-09-03
 */
public class UserThreadLocal {
    // ThreadLocal 将自身作为 Key，值作为 Value 存储到 Thread 类中的 ThreadLocalMap 中
    private UserThreadLocal() {}
    // 线程变量隔离
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static SysUser get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }

}
