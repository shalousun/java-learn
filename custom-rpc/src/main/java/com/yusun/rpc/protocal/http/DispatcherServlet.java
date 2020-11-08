package com.yusun.rpc.protocal.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author yu 2020/11/8.
 */
public class DispatcherServlet extends HttpServlet implements Serializable {

    private static final long serialVersionUID = -8877414346010653480L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new HttpServerHandler().handler(req,resp);
    }
}
