package ch.baurs.groovyconsole;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GroovyConsoleServlet extends HttpServlet {

    private Application application;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        Configuration configuration = ConfigurationHelper.ofServletConfig(config);
        application = new Application(configuration);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    protected void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!application.handle(req, resp)) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
