package spring.bean;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
@Component
public class PerformanceClass {
	private final Logger logger;
	private final StopWatch stopWatch;

	public PerformanceClass(Logger logger, StopWatch stopWatch) {
		this.logger = logger;
		this.stopWatch = stopWatch;
	}

	@Pointcut("execution(* spring.bean.object.MyClass1.*(..)) || execution(* spring.bean.object.MyClass2Inter.*(..))")
	public void taskTimeMethod() {}

	@Around("taskTimeMethod()")
	public Object excectionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		if (logger.isLoggable(Level.FINER)) {
			String methodName = joinPoint.getSignature().getName();
			stopWatch.start(methodName);
			logger.fine("성능 측정 시작");
			try {
				return joinPoint.proceed();
			} finally {
				stopWatch.stop();
				logger.fine("메서드 이름 : " + methodName + " 시간 : " + stopWatch.getTotalTimeMillis());
			}
		} else {
			return joinPoint.proceed();
		}
	}

	@Around("taskTimeMethod()")
	@Order(2)
	public Object test1(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("test1");
		return joinPoint.proceed();
	}

	@Around("taskTimeMethod()")
	@Order(1)
	public Object test2(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("test2");
		return joinPoint.proceed();
	}

	/* AOP 적용 전
	public void method(String methodName, Runnable task) {
		// 디버그일시에만 실행!
		if (logger.isLoggable(Level.FINER)) {
			stopWatch.start(methodName);
			logger.fine("성능 측정 시작");
			try {
				task.run();
			} finally {
				stopWatch.stop();
				logger.fine("메서드 이름 : " + methodName + " 시간 : " + stopWatch.getTotalTimeMillis());
			}
		} else {
			task.run();
		}
	}
	*/
}
