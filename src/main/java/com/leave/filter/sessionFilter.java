package com.leave.filter;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Writer;

public class sessionFilter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURL = request.getRequestURI();
        System.out.println(request.getRequestURI());
        HttpSession session = request.getSession(true);
        if (requestURL.contains("/loginLeave") || requestURL.contains("/forgetPwd") || requestURL.contains("/checkCode") || requestURL.contains("/updatePwd")){
            return true;
        }else if(session.getAttribute("AdminInfo") != null){
            return true;
        }else {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/json;charset=utf-8");
            Writer out = response.getWriter();
            out.write("404");
            out.flush();
            return false;
        }
    }
}
