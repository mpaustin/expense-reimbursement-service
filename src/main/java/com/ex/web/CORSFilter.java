package com.ex.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // if OPTION request
        // set ALLOW-ACCESS-CONTROL-*
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        response.setHeader("ACCESS-CONTROL-ALLOW-ORIGIN", "*");
        response.setHeader("ACCESS-CONTROL-ALLOW-METHOD", "GET, POST, PUT");
        response.setHeader("ACCESS-CONTROL-ALLOW-HEADERS", "CONTENT-TYPE");

//        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

        if(request.getMethod().equals("OPTION")) {
            return;
        } else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
