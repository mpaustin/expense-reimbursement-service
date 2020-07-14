package com.ex.web;

import com.ex.app.models.*;
import com.ex.app.persistence.ConnectionFactory;
import com.ex.app.service.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;

public class LoginServlet extends HttpServlet {

    private Service service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        String serviceLookupName = context.getInitParameter("serviceLookupName");
        service = (Service)context.getAttribute(serviceLookupName);

        if(service == null) {
            throw new ServletException("Error getting service from context");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Service service = this.service;

        InputStream in = req.getInputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        Creds creds = objectMapper.readValue(in, Creds.class);
        resp.setStatus(200);

        if (creds.isManager()) {

            Manager credMan = service.getManByID(creds.getCredId());

            if (credMan == null) {

                NotAuthDTO dto = new NotAuthDTO("Invalid credentials, login failed");
                String res = new ObjectMapper().writeValueAsString(dto);
                resp.getWriter().write(res);
                resp.setContentType("application/json");
                resp.setStatus(401);

            } else {
                if (creds.getCredPassword().equals(credMan.getPassword())) {

                    AuthDTO dto = new AuthDTO("manager", credMan.getManID());
                    String res = new ObjectMapper().writeValueAsString(dto);
                    resp.getWriter().write(res);
                    resp.setContentType("application/json");

//                    Cookie manRoleCookie = new Cookie("role", "manager");
//                    Cookie manIdCookie = new Cookie("id", credMan.getManID());
//                    resp.addCookie(manRoleCookie);
//                    resp.addCookie(manIdCookie);
                } else {

                    NotAuthDTO dto = new NotAuthDTO("Invalid credentials, login failed");
                    String res = new ObjectMapper().writeValueAsString(dto);
                    resp.getWriter().write(res);
                    resp.setContentType("application/json");
                    resp.setStatus(401);

                }
            }
        } else {

            Employee credEmp = service.getEmpByID(creds.getCredId());

            if (credEmp == null) {

                NotAuthDTO dto = new NotAuthDTO("Invalid credentials, login failed");
                String res = new ObjectMapper().writeValueAsString(dto);
                resp.getWriter().write(res);
                resp.setContentType("application/json");
                resp.setStatus(401);

            } else {
                if (creds.getCredPassword().equals(credEmp.getPassword())) {

                    AuthDTO dto = new AuthDTO("employee", credEmp.getEmpId());
                    String res = new ObjectMapper().writeValueAsString(dto);
                    resp.getWriter().write(res);
                    resp.setContentType("application/json");

                    Cookie empRoleCookie = new Cookie("role", "employee");
//                    empRoleCookie.setDomain("http://127.0.0.1:8080/");
//                    empRoleCookie.setHttpOnly(true);
//                    empRoleCookie.setMaxAge(1000*60*60*24*7*4);
                    resp.addCookie(empRoleCookie);

                    Cookie empIdCookie = new Cookie("id", credEmp.getEmpId());
//                    empIdCookie.setDomain("http://127.0.0.1:8080/");
//                    empIdCookie.setHttpOnly(true);
//                    empIdCookie.setMaxAge(1000*60*60*24*7*4);
                    resp.addCookie(empIdCookie);
                } else {

                    NotAuthDTO dto = new NotAuthDTO("Invalid credentials, login failed");
                    String res = new ObjectMapper().writeValueAsString(dto);
                    resp.getWriter().write(res);
                    resp.setContentType("application/json");
                    resp.setStatus(401);

                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        super.service(req, res);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
