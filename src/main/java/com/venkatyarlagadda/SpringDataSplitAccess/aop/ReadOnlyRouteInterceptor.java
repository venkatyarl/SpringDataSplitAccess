package com.venkatyarlagadda.SpringDataSplitAccess.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.venkatyarlagadda.SpringDataSplitAccess.routing.RoutingDataSource;

/**
 * 
 * @author Venkat Yarlagadda
 * @version V1
 *
 */
@Aspect
@Component
@Order(0)
public class ReadOnlyRouteInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReadOnlyRouteInterceptor.class);

	@Around("@annotation(transactional)")
	public Object proceed(ProceedingJoinPoint proceedingJoinPoint, Transactional transactional) throws Throwable {
		try {
			if(transactional.readOnly()) {
				RoutingDataSource.setReplicaRoute();
				LOGGER.info("Routing database call to the read replica");
			}
			return proceedingJoinPoint.proceed();
		} finally {
			RoutingDataSource.clearReplicaRoute();
		}
	}

}
