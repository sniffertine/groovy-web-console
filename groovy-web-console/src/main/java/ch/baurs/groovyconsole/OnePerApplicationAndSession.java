package ch.baurs.groovyconsole;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class OnePerApplicationAndSession implements GroovyConsoleFactory {

    private static final ConcurrentMap<Object, RequestContext> instances = new ConcurrentHashMap<Object, RequestContext>();

    @Override
    public GroovyConsole getGroovyConsole(Application application, HttpServletRequest request) {
        HttpSession session = getHttpSession(request, application.configuration.sessionInactiveInterval);
        Key key = createKey(application, session);
        String attributeName = key.toString();

        if (!(session.getAttribute(attributeName) instanceof GroovyConsole)) {
            session.setAttribute(attributeName, new GroovyConsole());
        }

        return (GroovyConsole) session.getAttribute(attributeName);

    }

    protected Key createKey(Application application, HttpSession session) {

        String sessionId = session.getId();
        String applicationId = application.getId().toString();

        return new Key(applicationId, sessionId);
    }


    protected HttpSession getHttpSession(HttpServletRequest request, int maxInactiveInterval) {
        HttpSession session = request.getSession();
        if (session.isNew()) {
            session.setMaxInactiveInterval(maxInactiveInterval);
        }
        return session;
    }

    @ToString
    @RequiredArgsConstructor
    @EqualsAndHashCode(of = {"applicationId", "sessionId"})
    protected static final class Key {
        private final String applicationId;
        private final String sessionId;
    }

}
