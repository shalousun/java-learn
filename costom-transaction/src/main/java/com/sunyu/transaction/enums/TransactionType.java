package com.sunyu.transaction.enums;

/**
 * @author yu 2021/1/2.
 */
public enum TransactionType {

    rollback("rollback"),
    commit("commit");

    private String type;

    private TransactionType(String type){
        this.type = type;
    }
}
