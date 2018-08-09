package ch.baurs.groovyconsole.testwebapp;

import ch.baurs.groovyconsole.Application;
import ch.baurs.groovyconsole.Configuration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyServlet extends HttpServlet {

    private Application application;

    @Override
    public void init(ServletConfig config) throws ServletException {
        Configuration configuration = Configuration.builder()
                .mappingPath("/myServlet")
                .prompt("console directly configured $>")
                .theme("mdn-like")
                .build();

        application = new Application(configuration);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        application.handle(req, resp);
    }
}
