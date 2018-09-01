package ch.baurs.groovyconsole;

import javax.servlet.http.HttpServletRequest;

public interface GroovyConsoleFactory {

    GroovyConsole getGroovyConsole(Application application, HttpServletRequest request);

}
