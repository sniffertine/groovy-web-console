package ch.baurs.groovyconsole;

import groovy.lang.GroovySystem;
import org.baswell.routes.RedirectTo;
import org.baswell.routes.RequestParameters;
import org.baswell.routes.Route;
import org.baswell.routes.Routes;

import javax.json.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Routes(value = "/")
public class GroovyConsoleHandler {

    private static final String ATTR_GROOVY_CONSOLE = "GroovyConsole";

    @Route
    public String get() {
        throw new RedirectTo("show");
    }

    @Route(returnedStringIsContent = true)
    public String show(HttpServletRequest req, HttpServletResponse resp) {
        return getConsole(req).render();
    }

    @Route(value = "execute", returnedStringIsContent = true)
    public String postExecute(HttpServletRequest req, RequestParameters parameters, HttpServletResponse resp) throws IOException {
        GroovyConsole console = getConsole(req);

        String code = parameters.get("code", "");
        console.execute(code);

        return getStatus(req, resp);
    }

    @Route(value = "status", returnedStringIsContent = true)
    public String getStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        GroovyConsole console = getConsole(req);

        StringBuilder sb = new StringBuilder();
        sb.append("Welcome to the Groovy Web Console! [Using Groovy version ").append(GroovySystem.getVersion()).append("]\n\n");
        sb.append(console.printExecutionState());

        JsonObjectBuilder json = Json.createObjectBuilder();
        json.add("out", sb.toString());
        json.add("history", createHistoryJson(console.getHistory()));

        StringWriter out = new StringWriter();
        JsonWriter writer = Json.createWriter(out);
        writer.write(json.build());
        writer.close();
        return out.toString();
    }

    private JsonArray createHistoryJson(List<String> history) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (String command : history) {
            arrayBuilder.add(command);
        }

        return arrayBuilder.build();
    }


    private GroovyConsole getConsole(HttpServletRequest req) {
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
