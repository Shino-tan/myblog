package com.tanzheng.myblog.common.cache;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanzheng.myblog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.time.Duration;

// aop 定义一个切面，切面定义了切点和通知的关系
@Aspect
@Component
@Slf4j
public class CacheAspect {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    // 切点路径
    @Pointcut("@annotation(com.tanzheng.myblog.common.cache.Cache)")
    public void pt() {}

    // 环绕通知，在方法的前后进行增强
    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp){
        try {
            Signature signature = pjp.getSignature();
            //类名
            String className = pjp.getTarget().getClass().getSimpleName();
            //调用的方法名
            String methodName = signature.getName();

            Class[] parameterTypes = new Class[pjp.getArgs().length];
            Object[] args = pjp.getArgs();
            //参数
            String params = "";
            if (args != null) {
                for(int i = 0; i < args.length; i++) {
                    if(args[i] != null) {
                        params += JSON.toJSONString(args[i]);
                        parameterTypes[i] = args[i].getClass();
                    } else {
                        parameterTypes[i] = null;
                    }
                }
            }
            if (StringUtils.isNotEmpty(params)) {
                //加密 以防出现key过长以及字符转义获取不到的情况
                params = DigestUtils.md5Hex(params);
            }
            Method method = pjp.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
            //获取Cache注解
            Cache annotation = method.getAnnotation(Cache.class);
            //缓存过期时间
            long expire = annotation.expire();
            //缓存名称
            String name = annotation.name();
            //先从redis获取
            String redisKey = name + "::" + className + "::" + methodName + "::" + params;
            String redisValue = redisTemplate.opsForValue().get(redisKey);
            // 拿到缓存如果不为空，直接进行返回
            if (StringUtils.isNotEmpty(redisValue)) {
                log.info("命中缓存~~~, {}, {}", className, methodName);
                return JSON.parseObject(redisValue, Result.class);
            }
            // 如果为空，则需要访问方法，并将方法返回的结果转换为 json 存入到 redis 中
            Object proceed = pjp.proceed();
            redisTemplate.opsForValue().set(redisKey, new ObjectMapper().writeValueAsString(proceed), Duration.ofMillis(expire));
            log.info("存入缓存~~~ ,{}, {}", className, methodName);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Result.fail(-999, "系统错误");
    }

}
