package pl.czekaj.springsocial.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/*
	The task of this class is only to show
	my knowledge about AOP except advices in exception packages
 */

@Aspect
//@Component
public class RestControllerAspect {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Pointcut("execution(* pl.czekaj.springsocial.controller..*(..))")
		private void pointCutBeforeApi() {
	}

    @Around("pointCutBeforeApi()")
	public void loggerBeforeRestCall(ProceedingJoinPoint pjp) throws Throwable{
		log.info("======== AOP Before RestAPI Call ==========" + pjp.getSignature());
		try {
			Object result = pjp.proceed();
			log.info("======== After RestAPI Call ==========" + result.toString());
		} catch(Exception e) {
			log.info("======== After RestAPI Call Throwing ==========");
		} finally {
			log.info("======== After RestAPI Call Returning ==========");
		}
	}

}