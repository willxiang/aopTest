package com.example.demo.aspect;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class LogAspect {

	private Logger logger = LoggerFactory.getLogger(LogAspect.class);

	private long startTime = 0;
	private long endTime = 0;

	// 设置切点表达式
	@Pointcut("execution(* com.example.demo.controller..*(..))")
	private void pointcut() {
	}

	@Before("execution(* com.example.demo.controller..*(..))")
	public void Before(JoinPoint joinPoint) {
		startTime = System.currentTimeMillis();
	}

	@Around("execution(* com.example.demo.controller..*(..))")
	public Object Around(ProceedingJoinPoint pjp) throws Throwable {
		/**
		 * 1.获取request信息 2.根据request获取session 3.从session中取出登录用户信息
		 */

		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();

		logger.info("ClassName:{},MethodName:{}", className, methodName);

		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();

//		// 从session中获取用户信息
//		String loginInfo = (String) session.getAttribute("username");
//		if (loginInfo != null && !"".equals(loginInfo)) {
//			userName = operLoginModel.getLogin_Name();
//		} else {
//			userName = "用户未登录";
//		}

		// 获取输入参数
		Map<String, String[]> parameterMap = request.getParameterMap();

		Set<String> keySet = parameterMap.keySet();
		logger.info("---------------------");
		for (String key : keySet) {
			logger.info("key:{},value:{}", key, parameterMap.get(key));
		}
		logger.info("---------------------");

		// 获取请求地址
		logger.info("request URI:{}", request.getRequestURI());

		// 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
		Object result = pjp.proceed();// result的值就是被拦截方法的返回值
		logger.info("response:{}", result);
		return result;
	}

	// 方法后置切面
	@After(value = "pointcut()")
	public void After(JoinPoint joinPoint) throws ClassNotFoundException {
		endTime = System.currentTimeMillis();
		printOptLog();
	}

	/**
	 * 
	 * @Title：printOptLog
	 * @Description: 输出日志
	 * @author shaojian.yu
	 * @date 2014年11月2日 下午4:47:09
	 */
	private void printOptLog() {
		logger.info("花费时间：" + (endTime - startTime) + " ms");
	}

}