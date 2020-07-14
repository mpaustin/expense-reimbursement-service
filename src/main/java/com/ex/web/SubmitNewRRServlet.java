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
import java.io.InputStream;
import java.util.Date;

public class SubmitNewRRServlet extends HttpServlet {
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
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(500);
//        Cookie[] cookies = req.getCookies();

        try {
//            for(Cookie c : cookies) {
//                if(c.getName().equals("id")) {
//                    empID = Integer.parseInt(c.getValue());
//                }
//            }

            InputStream in = req.getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            RR rr = objectMapper.readValue(in, RR.class);

            service.addNewRR(rr);
            resp.setStatus(200);

        } catch(Exception ex) {
            System.out.println("Error creating new reimbursement request");
            ex.printStackTrace();
        }
    }
}
