package com.ex.app.context;

import com.ex.app.persistence.ConnectionFactory;
import com.ex.app.persistence.Persistable;
import com.ex.app.persistence.PostgresConnectionFactory;
import com.ex.app.persistence.SQLPersistence;
import com.ex.app.service.Service;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Context implements ServletContextListener {

    ConnectionFactory connectionFactory;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();

        try {
            initConnection(context);
            initDataAccess(context);
            initService(context);
        } catch (Exception ex) {
            System.out.println("Error with context initialization");
            ex.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        connectionFactory.destroy();
    }

    private void initConnection(ServletContext context) {
        connectionFactory = new PostgresConnectionFactory(
                "jdbc:postgresql://postgresdb-2.covp8j1urmzv.us-east-2.rds.amazonaws.com:9999/project0",
                "mpaustin13", "Playmaker42");
        context.setAttribute("ConnectionFactory", connectionFactory);
    }

    private void initDataAccess(ServletContext context) {
        Persistable persistence = new SQLPersistence(connectionFactory);
        context.setAttribute("Persistence", persistence);
    }

    private void initService(ServletContext context) {
        System.out.println("Init services");
        Persistable persistence = (Persistable)context.getAttribute("Persistence");
        Service service = new Service(persistence);
        context.setAttribute("Service", service);
    }
}
