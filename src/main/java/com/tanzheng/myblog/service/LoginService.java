package com.tanzheng.myblog.service;

import com.tanzheng.myblog.entity.SysUser;
import com.tanzheng.myblog.vo.Result;
import com.tanzheng.myblog.vo.params.LoginParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 诗乃
 * @since 2022-08-29
 */
public interface LoginService {

    /**
     * @description 登录功能
     * @param loginParam loginParam
     * @return Result
     */
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    /**
     * @description 退出登录
     * @param token token
     * @return Result
     */
    Result logout(String token);

    /**
     * @description 注册
     * @param loginParam 请求参数 account，password，nickname
     * @return Result
     */
    Result register(LoginParam loginParam);

}
