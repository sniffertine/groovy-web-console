package ch.baurs.groovyconsole;

import lombok.Getter;
import org.baswell.routes.RoutesConfiguration;
import org.baswell.routes.RoutesEngine;
import org.baswell.routes.RoutingTable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class Application {

    @Getter
    private final UUID id;

    final Configuration configuration;

    private final RoutesEngine router;

    public Application(Configuration configuration) {
        this.id = UUID.randomUUID();
        this.configuration = configuration;
        this.router = createRoutesEngine();

        String message = "Application initialized. \n\tconfiguration: " + configuration + "\n\trouter: " + router;
        LoggingHelper.info(getClass(), message);
    }

    static Application getInstance() {
        return RequestContext.getInstance().getApplication();
    }

    static Configuration getConfiguration() {
        return getInstance().configuration;
    }

    public boolean handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestContext original = RequestContext.setInstance(req, resp, this);
        try {
            return doHandle(req, resp);
        } finally {
            RequestContext.setInstance(original);
        }
    }

    protected boolean doHandle(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            //check if access fails. continue if not...
            configuration.authConfig.check(req, resp);

            boolean handled = router.process(req, resp);
            LoggingHelper.trace(getClass(), "handle request done. handled = " + handled);
            return handled;
        } catch (AccessException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);

            LoggingHelper.warn(getClass(), "Access Error", e);

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
