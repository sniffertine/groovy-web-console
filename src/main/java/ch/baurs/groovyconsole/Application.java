package ch.baurs.groovyconsole;

import org.baswell.routes.RoutesConfiguration;
import org.baswell.routes.RoutesEngine;
import org.baswell.routes.RoutingTable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Application {

    private static Application instance;
    private final RoutesEngine router;
    private final Configuration configuration;

    public Application(Configuration configuration) {
        instance = this;
        this.configuration = configuration;
        this.router = createRoutesEngine();
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

            return router.process(req, resp);
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

}
