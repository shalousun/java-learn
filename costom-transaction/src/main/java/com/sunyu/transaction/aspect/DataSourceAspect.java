package com.sunyu.transaction.aspect;

import com.sunyu.transaction.connection.CustomConnection;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.sql.Connection;

/**
 * @author yu 2021/1/2.
 */
@Aspect
public class DataSourceAspect {

    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Connection around(ProceedingJoinPoint joinPoint) throws Throwable{
        Connection result = (Connection) joinPoint.proceed();
        CustomConnection customConnection = new CustomConnection(result);
        return customConnection;
    }
}
