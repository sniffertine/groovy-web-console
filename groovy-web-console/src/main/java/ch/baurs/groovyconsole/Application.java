package ch.baurs.groovyconsole;

import org.baswell.routes.RoutesConfiguration;
import org.baswell.routes.RoutesEngine;
import org.baswell.routes.RoutingTable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Application {

    private static Application instance;
    private final RoutesEngine router;
    private final Configuration configuration;

    public Application(Configuration configuration) {
        instance = this;
        this.configuration = configuration;
        this.router = createRoutesEngine();

        String message = "Application initialized. \n\tconfiguration: " + configuration + "\n\trouter: " + router;
        LoggingHelper.info(getClass(), message);
    }

    public static Application getInstance() {
        return instance;
    }

    public static Configuration getConfiguration() {
        return getInstance().configuration;
    }

    public boolean handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //check if authentication fails. continue if not...
            configuration.authConfig.check(req, resp);

            boolean handled = router.process(req, resp);
            LoggingHelper.trace(getClass(), "handle request done. handled = " + handled);
            return handled;
        } catch (AuthenticationException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);

            LoggingHelper.warn(getClass(), "Authentication Error", e);

            return true;
        }
    }

    private RoutesEngine createRoutesEngine() {
        RoutesConfiguration routesConfiguration = new RoutesConfiguration();
        routesConfiguration.rootPath = configuration.mappingPath;
        routesConfiguration.routesMetaPath = configuration.mappingPath + "/routes";
        routesConfiguration.caseInsensitive = true;

        RoutingTable routingTable = new RoutingTable(routesConfiguration);
        routingTable.add(new AssetsHandler());
        routingTable.add(new GroovyConsoleHandler());
        routingTable.build();

        return new RoutesEngine(routingTable);
    }

    private ApplicationScope getApplicationScope(HttpServletRequest req){
        HttpSession session = req.getSession();
        if (session.isNew()) {
            session.setMaxInactiveInterval(24 * 3600); //24 hours
        }
        if (!(session.getAttribute(ATTR_GROOVY_CONSOLE) instanceof GroovyConsole)) {
            session.setAttribute(ATTR_GROOVY_CONSOLE, new GroovyConsole());
        }

        return (GroovyConsole) session.getAttribute(ATTR_GROOVY_CONSOLE);

    }

}
