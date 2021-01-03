package com.sunyu.transaction.aspect;

import com.sunyu.transaction.enums.TransactionType;
import com.sunyu.transaction.transaction.MyTransactionManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.core.Ordered;

import java.sql.Connection;

/**
 * @author yu 2021/1/2.
 */
public class MyTransactionAspect implements Ordered {

    @Around("@annotation(com.sunyu.transaction.annotation.MyTransaction)")
    public void invoke(ProceedingJoinPoint joinPoint){
        String groupId = MyTransactionManager.createTransactionGroup();
        //创建全局事务
        try{
            joinPoint.proceed();
            MyTransactionManager.addTransaction(groupId, TransactionType.commit);
        }catch (Throwable throwable){
            MyTransactionManager.addTransaction(groupId, TransactionType.rollback);
            throwable.printStackTrace();
        }

    }

    @Override
    public int getOrder() {
        return 10000;
    }
}
