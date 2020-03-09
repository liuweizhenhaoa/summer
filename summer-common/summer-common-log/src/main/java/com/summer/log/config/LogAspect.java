package com.summer.log.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * 系统日志，切面处理类
 */
@Slf4j
@Aspect
//@Component
public class LogAspect {

//	private final ObjectMapper mapper;
//
//	@Autowired
//	public LogAspect(ObjectMapper mapper) {
//		this.mapper = mapper;
//	}
	
	@Pointcut("execution(public * com.summer.*.*.controller..*.*(..))")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		log.info(String.format("RESPONSE: URL[%s]  处理耗时[%s]ms " , request.getRequestURI(), time));
		return result;
	}


	@Before("logPointCut()")
	public void doBefore(JoinPoint joinPoint){
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		List<Object> list = new ArrayList<Object>();
		for (Object object : joinPoint.getArgs()) {
			if (
					object instanceof MultipartFile
							|| object instanceof HttpServletRequest
							|| object instanceof HttpServletResponse
			) {
				continue;
			}
			list.add(object);
		}
		log.info(String.format("REQUEST: URL [%s] HTTP_METHOD[%s] REQUEST[%s] ",request.getRequestURI(),request.getMethod(),
				list.toString()));




	}

	@AfterReturning(returning = "ret", pointcut = "logPointCut()")
	public void doAfterReturning(Object ret) throws Throwable {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// 处理完请求，返回内容
		log.info(String.format("RESPONSE: URL[%s] " , request.getRequestURI()));
	}


	
}
