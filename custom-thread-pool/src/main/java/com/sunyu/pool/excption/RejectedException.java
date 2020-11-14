package com.sunyu.pool.excption;

/**
 * @author yu 2020/11/14.
 */
public class RejectedException extends RuntimeException {
    public RejectedException(){
        super();
    }

    public RejectedException(String message){
        super(message);
    }
}
