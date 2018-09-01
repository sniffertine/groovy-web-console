package ch.baurs.groovyconsole.testwebapp.util;

import lombok.AccessLevel;
import lombok.Getter;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletContextHolder implements ServletContextListener {

    @Getter
    private static ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        servletContext = sce.getServletContext();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        servletContext = null;
    }

}
