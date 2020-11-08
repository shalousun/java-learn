package com.yusun.rpc.protocal.http;

import com.alibaba.fastjson.JSONObject;
import com.yusun.rpc.entity.Invocation;
import com.yusun.rpc.register.LocalRegister;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yu 2020/11/8.
 */
public class HttpServerHandler {

    public void handler(HttpServletRequest request, HttpServletResponse response) {
        try {
            Invocation invocation = JSONObject.parseObject(request.getInputStream(), Invocation.class);
            String interfaceName = invocation.getInterfaceName();
            Class clas = LocalRegister.get(interfaceName);
            Method method = clas.getMethod(invocation.getMethodName(), invocation.getParamTypes());
            String result = (String) method.invoke(clas.newInstance(), invocation.getParams());
            IOUtils.write(result, response.getOutputStream(), "UTF-8");
        } catch (IOException | NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
