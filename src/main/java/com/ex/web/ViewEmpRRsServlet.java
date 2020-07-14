package com.ex.web;

import com.ex.app.models.Employee;
import com.ex.app.models.RR;
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
import java.util.List;

public class ViewEmpRRsServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Service service = this.service;
        List<RR> empRRs = null;
        ObjectMapper objectMapper = new ObjectMapper();
        String empId = req.getParameter("id");

//        Cookie[] cookies = req.getCookies();
//        for(Cookie c : cookies) {
//            if(c.getName().equals("id")) {
//                empRRs = service.getAllEmpRR(c.getValue());
//            }
//        }

        try {
            empRRs = service.getAllEmpRR(empId);
            String ret = objectMapper.writeValueAsString(empRRs);
            resp.getWriter().write(ret);
            resp.setStatus(200);
            resp.setContentType("application/json");
        } catch (Exception ex) {
            System.out.println("Error retrieving all employees");
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
