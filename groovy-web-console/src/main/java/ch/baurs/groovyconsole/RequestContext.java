package ch.baurs.groovyconsole;

import lombok.AccessLevel;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.ref.WeakReference;

/**
 * <p>
 * Sets the context for the currently running request. You can access the request specific instances of
 * {@link HttpServletRequest}, {@link HttpServletResponse} and {@link Application} from here.
 * </p>
 * <p>
 * Also, you can define a custom {@link GroovyConsoleFactory} if you want to change the scope of one Console. Per default we use {@link OnePerApplicationAndSession}.
 * </p>
 */
public class RequestContext {

    private static final ThreadLocal<RequestContext> currentInstance = new ThreadLocal<RequestContext>();

    private static GroovyConsoleFactory consoleFactory = new OnePerApplicationAndSession();

    @Getter(value = AccessLevel.PACKAGE)
    private final HttpServletRequest request;

    @Getter(value = AccessLevel.PACKAGE)
    private final HttpServletResponse response;

    @Getter(value = AccessLevel.PACKAGE)
    private final Application application;

    private WeakReference<GroovyConsole> groovyConsole;

    private static boolean factoryWasUsed = false;


    private RequestContext(HttpServletRequest request, HttpServletResponse response, Application application) {
        this.request = ObjectUtil.requireNonNull(request, "request must not be null");
        this.response = ObjectUtil.requireNonNull(response, "response must not be null");
        this.application = ObjectUtil.requireNonNull(application, "application must not be null");
    }

    static RequestContext getInstance() {
        RequestContext requestContext = currentInstance.get();
        if (requestContext == null) {
            throw new IllegalStateException(RequestContext.class.getSimpleName() + " not set");
        }

        return requestContext;
    }

    public static synchronized void setConsoleFactory(GroovyConsoleFactory newFactory) {
        if (consoleFactory != null && factoryWasUsed) {
            LoggingHelper.warn(RequestContext.class, "Replacing active GroovyConsoleFactory. Currentyl active sessions will be lost.");
        }
        consoleFactory = newFactory;
        factoryWasUsed = false;
    }

    /**
     * sets the context for the current thread ( = request) and returns the old context that was present before.
     */
    static RequestContext setInstance(HttpServletRequest request, HttpServletResponse response, Application application) {
        RequestContext newContext = new RequestContext(request, response, application);
        return setInstance(newContext);
    }

    /**
     * sets the context for the current thread ( = request) and returns the old context that was present before.
     */
    static RequestContext setInstance(RequestContext newContext) {
        RequestContext original = currentInstance.get();
        currentInstance.set(newContext);
        return original;
    }

}
