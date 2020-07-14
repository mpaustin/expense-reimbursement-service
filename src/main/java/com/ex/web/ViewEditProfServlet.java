package com.ex.web;

import com.ex.app.models.Employee;
import com.ex.app.service.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class ViewEditProfServlet extends HttpServlet {
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
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String id = req.getParameter("id");
            Employee emp = service.getEmpByID(id);
            String ret = objectMapper.writeValueAsString(emp);
            resp.getWriter().write(ret);
            resp.setStatus(200);
            resp.setContentType("application/json");
        } catch(Exception ex) {
            System.out.println("Error getting employee profile");
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Service service = this.service;
        InputStream in = req.getInputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        resp.setStatus(500);

        Employee emp = objectMapper.readValue(in,Employee.class);
        if(emp.getEmail() != null) {
            service.updateEmpEmail(emp.getEmpId(),emp.getEmail());
            resp.setStatus(200);
        } else if(emp.getPassword() != null) {
            service.updateEmpPassword(emp.getEmpId(),emp.getPassword());
            resp.setStatus(200);
        } else {
            System.out.println("Error getting update info");
        }

    }
}
