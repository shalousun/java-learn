package com.yusun.rpc.protocal.http;

import com.alibaba.fastjson.JSONObject;
import com.yusun.rpc.framework.RpcRequest;
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
            RpcRequest rpcRequest = JSONObject.parseObject(request.getInputStream(), RpcRequest.class);
            String interfaceName = rpcRequest.getInterfaceName();
            Class clas = LocalRegister.get(interfaceName);
            Method method = clas.getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            String result = (String) method.invoke(clas.newInstance(), rpcRequest.getParams());
            IOUtils.write(result, response.getOutputStream(), "UTF-8");
        } catch (IOException | NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
