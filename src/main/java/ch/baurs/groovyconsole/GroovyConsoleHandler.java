package ch.baurs.groovyconsole;

import groovy.lang.GroovySystem;
import org.baswell.routes.*;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

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

    @Route(value = "execute", returnedStringIsContent = true, contentType = MIMETypes.JSON)
    public String postExecute(HttpServletRequest req, RequestParameters parameters, HttpServletResponse resp) throws IOException {
        GroovyConsole console = getConsole(req);

        String code = parameters.get("code", "");
        console.execute(code);

        return getStatus(req, resp);
    }

    @Route(value = "status", returnedStringIsContent = true, contentType = MIMETypes.JSON)
    public String getStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        GroovyConsole console = getConsole(req);

        StringBuilder sb = new StringBuilder();
        sb.append("Welcome to the Groovy Web Console! [Using Groovy version ").append(GroovySystem.getVersion()).append("]\n\n");
        sb.append(console.printExecutionState());

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("out", sb.toString());
        data.put("history", console.getHistory());

        JSONObject json = new JSONObject(data);

        return json.toString(4);
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
