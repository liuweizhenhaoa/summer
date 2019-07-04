//package com.summer.limit.aop;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.summer.common.exception.BussinessException;
//import com.summer.common.limit.RateLimiter;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.*;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * 系统日志，切面处理类
// *
// * @author taiqirui
// * @email taiqirui@jtkjbike.com
// * @date 2017年3月8日 上午11:07:35
// */
//@Slf4j
//@Aspect
//@Component
//public class RateLimiterAspect {
//
////	@Autowired
////	private final ObjectMapper mapper;
//
//	@Autowired
//	RedisTemplate redisTemplate;
//
////	@Autowired
////	public RateLimiterAspect(ObjectMapper mapper) {
////		this.mapper = mapper;
////	}
//
//	@Pointcut("@annotation(com.summer.common.limit.RateLimiter)")
//	public void limitPointCut() {
//
//	}
//
//	@Around("@annotation(rateLimiter)")
//	public Object around(ProceedingJoinPoint point, RateLimiter rateLimiter) throws Throwable {
//		if(log.isDebugEnabled()){
//			log.debug("RateLimterHandler[分布式限流处理器]开始执行限流操作");
//		}
//
//		Signature signature = point.getSignature();
//		if(!(signature instanceof MethodSignature)){
//			throw new IllegalArgumentException("the Annotation @RateLimter must used on method!");
//
//		}
//		/**
//		 * 获取注解参数
//		 *
//		 */
//		//限流模块key
//		String limitKey = rateLimiter.key();
//		//限流阈值
//		long limitTimes = rateLimiter.limit();
//
//		//限流超时时间
//		long expireTime = rateLimiter.expire();
//		if(log.isDebugEnabled()){
//			log.debug("RateLimterHandler[分布式限流处理器]参数值为-limitTimes={},limitTimeout={}",limitTimes,expireTime);
//		}
//
//		/**
//		 * 执行Lua脚本
//		 */
//		List<String> keyList = new ArrayList<>();
//		//设置key值为注解中的值
//		keyList.add(limitKey);
//		/**
//		 * 调用脚本并执行
//		 */
//		long result = redisTemplate.execute(getRe);
//
//
//
//		return null;
//	}
//
//
//	@Before("limitPointCut()")
//	public void doBefore(JoinPoint joinPoint){
//
//	}
//
//	@AfterReturning(returning = "ret", pointcut = "limitPointCut()")
//	public void doAfterReturning(Object ret) throws Throwable {
//			}
//
//
//
//}
