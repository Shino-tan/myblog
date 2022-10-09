package com.tanzheng.myblog.service;

import com.tanzheng.myblog.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tanzheng.myblog.vo.Result;
import com.tanzheng.myblog.vo.UserVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 诗乃
 * @since 2022-08-29
 */
public interface ISysUserService extends IService<SysUser> {

    UserVo findUserVoById(Long id);

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * @description 根据 token 查询用户信息
     * @param token token
     * @return Result
     */
    Result getUserByToken(String token);

    /**
     * @description 根据账户查找用户
     * @param account 账号
     * @return SysUser
     */
    SysUser findUerByAccount(String account);

}
