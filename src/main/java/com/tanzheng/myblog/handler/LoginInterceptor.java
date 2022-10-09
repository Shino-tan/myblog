package com.tanzheng.myblog.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tanzheng.myblog.entity.SysUser;
import com.tanzheng.myblog.service.LoginService;
import com.tanzheng.myblog.utils.UserThreadLocal;
import com.tanzheng.myblog.vo.ErrorCode;
import com.tanzheng.myblog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  拦截器类
 * </p>
 *
 * @author 诗乃
 * @since 2022-09-03
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在执行 controller方法之前进行执行
        /*
            1. 需要判断，请求的接口路径，是否为 HandlerMethod（controller方法）
            2. 判断 token 是否为空，如果为空，未登录
            3. 如果 token 不为空，登录验证 loginService checkToken
            4. 如果认证成功，放行即可
         */
        if (!(handler instanceof HandlerMethod)) {
            // handler 可能是资源（RequestResourceHandler）
            return true;
        }
        String token = request.getHeader("Authorization");

        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end=============================");

        if (StringUtils.isBlank(token)) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        // 登录验证成功，放行
        // 希望在 controller 中直接获取用户信息，怎么获取
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 如果不删除 ThreadLocal 中用完的信息，会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}
