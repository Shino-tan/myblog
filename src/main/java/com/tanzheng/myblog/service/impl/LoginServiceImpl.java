package com.tanzheng.myblog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tanzheng.myblog.entity.SysUser;
import com.tanzheng.myblog.service.ISysUserService;
import com.tanzheng.myblog.service.LoginService;
import com.tanzheng.myblog.utils.JWTUtils;
import com.tanzheng.myblog.vo.ErrorCode;
import com.tanzheng.myblog.vo.Result;
import com.tanzheng.myblog.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 诗乃
 * @since 2022-08-29
 */
@Service
@Transactional // 开启事务支持，一旦中间的任何过程出现问题，注册的用户需要回滚
public class LoginServiceImpl implements LoginService {

    // 加密盐
    private static final String slat = "mszlu!@#";
    @Resource
    @Lazy // 懒加载，调用时才会被初始化
    private ISysUserService sysUserService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public Result login(LoginParam loginParam) {
        /*
          1.检查参数是否合法
          2.根据用户名和密码去 user 表中查询用户是否存在
          3.如果不存在 登陆失败
          4.如果存在，使用 jwt 生成 token 返回给前端
          5.token 放入 redis 当中， token：user 信息 设置过期时间（登录认证的时候 先认证 token 字符串是否合法，去 redis 认证是否存在）
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        // 检查参数是否合法
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        // 使用 md5 和 加密盐 加密
        String pwd = DigestUtils.md5Hex(password + slat);
        // 根据用户名和密码去 user 表中查询用户是否存在
        SysUser sysUser = sysUserService.findUser(account, pwd);
        // 如果不存在 登陆失败
        if (sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        // 使用 jwt 生成 token 返回给前端
        String token = JWTUtils.createToken(sysUser.getId());
        // token 放入 redis 当中
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public Result logout(String token) {
        // 删除存储在 redis 中的信息，相当于清除登录的 session 信息
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        /*
            1. 判断参数。是否合法
            2. 判断账户是否存在，存在：返回账湖已经被注册
            3. 不存在，注册用户
            4. 生成 token
            5. 存入 redis 并返回
            6. 加上事务，一旦中间的任何过程出现问题，注册的用户需要回滚
         */
        // 1. 判断参数。是否合法
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)
        ) {
           return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        // 2. 判断账户是否存在，存在：返回账湖已经被注册
        SysUser sysUser = sysUserService.findUerByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }

        // 3. 不存在，注册用户
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password + slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        // 注意 默认生成的id 是分布式id 采用了雪花算法，但是 admin 实体类中设置了 id 自增

        this.sysUserService.save(sysUser);

        // 4. 生成 token
        String token = JWTUtils.createToken(sysUser.getId());

        // 5. 存入 redis 并返回
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }


    /*
    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex("admin"+slat));
    }
*/

}
