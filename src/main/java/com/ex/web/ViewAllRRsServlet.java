package com.ex.web;

import com.ex.app.models.Employee;
import com.ex.app.models.RR;
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
import java.util.Enumeration;
import java.util.List;

public class ViewAllRRsServlet extends HttpServlet {
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
        List<RR> rrs;
        ObjectMapper objectMapper = new ObjectMapper();

        if(req.getParameter("action").equals("get")) {

            try {
                rrs = service.getAllPendingRR();
                if (rrs == null) {
                    throw new IOException();
                }
                String ret = objectMapper.writeValueAsString(rrs);

                resp.getWriter().write(ret);
                resp.setStatus(200);
                resp.setContentType("application/json");
            } catch (IOException ex) {
                System.out.println("No rrs to find");
            } catch (Exception ex) {
                System.out.println("Error retrieving all rrs");
                ex.printStackTrace();
            }
        } else{
            try {
                InputStream in = req.getInputStream();
                int rrID = Integer.parseInt(req.getParameter("id"));

                // approve
                if(req.getParameter("action").equals("approve")) {
                    RR rr = service.getRRByID(rrID);
                    rr.setResolved(true);
                    rr.setManID(req.getParameter("manId"));
                    service.updateRR(rr);
                    System.out.println("Approved");
                } else { // deny
                    RR rr = service.getRRByID(rrID);
                    rr.setResolved(true);
                    rr.setManID(req.getParameter("manId"));
                    service.updateRR(rr);
                    System.out.println("Denied");
                }
                resp.setStatus(200);
            } catch(Exception ex) {
                System.out.println("Error managing requests");
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req,resp);
    }
}
