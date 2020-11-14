package com.sunyu.pool.excption;

/**
 * @author yu 2020/11/14.
 */
public class PolicyException extends RuntimeException {
    public PolicyException(){
        super();
    }

    public PolicyException(String message){
        super(message);
    }
}
