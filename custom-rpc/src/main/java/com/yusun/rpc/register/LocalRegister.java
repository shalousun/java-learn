package com.yusun.rpc.register;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yu 2020/11/8.
 */
public class LocalRegister {

    private static Map<String,Class> map = new HashMap<>();

    public static void registry(String interfaceName,Class implClass){
        map.put(interfaceName,implClass);
    }

    public static Class get(String interfaceName){
        return map.get(interfaceName);
    }
}
