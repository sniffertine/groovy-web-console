package ch.baurs.groovyconsole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GroovyConsoleFilter implements Filter {

    private Application application;

    public void init(FilterConfig config) {
        Configuration configuration = ConfigurationHelper.ofFilterConfig(config);
        application = new Application(configuration);

        LoggingHelper.info(getClass(), "Successfully initialized Application");
    }

    public void destroy() {
        application = null;
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        boolean handled = false;

        if (req instanceof HttpServletRequest && resp instanceof HttpServletResponse) {
            handled = application.handle((HttpServletRequest) req, (HttpServletResponse) resp);
        }

        if (!handled) {
            chain.doFilter(req, resp);
        }
    }

}
