package ch.baurs.groovyconsole;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

class GroovyConsoleShell {

    private final GroovyShell groovyShell;
    private final List<Execution> executions = new ArrayList<Execution>();
    private StringWriter consoleOut;

    public GroovyConsoleShell() {
        consoleOut = new StringWriter();
        groovyShell = createGroovyShell(consoleOut);
    }

    public void execute(String code) {
        String currentOut = null;
        Object result = null;
        try {
            if (code != null && code.length() > 0) {
                String consoleOutBefore = consoleOut.toString();
                result = groovyShell.evaluate(code);
                currentOut = consoleOut.toString().replaceFirst(consoleOutBefore, "");
            }
        } catch (Exception ex) {
            result = ex;
        }

        executions.add(new Execution(code, currentOut, result));
    }

    public List<Execution> getExecutions() {
        return executions;
    }

    public Execution getLastExecution() {
        return executions.get(executions.size() - 1);
    }

    private GroovyShell createGroovyShell(StringWriter consoleOut) {
        Binding sharedData = new Binding();
        sharedData.setProperty("out", consoleOut);

        GroovyClassLoader gcloader = new GroovyClassLoader(getClass().getClassLoader());
        CompilerConfiguration gcon = new CompilerConfiguration();
        gcon.setScriptBaseClass(Script.class.getName());

        return new GroovyShell(gcloader, sharedData, gcon);
    }

    class Execution {
        public final String scriptText;
        public final String consoleOut;
        public final Object result;

        public Execution(String scriptText, String consoleOut, Object result) {
            this.scriptText = scriptText;
            this.consoleOut = consoleOut;
            this.result = result;
        }

        @Override
        public String toString() {
            return "Execution{" +
                    "scriptText='" + scriptText + '\'' +
                    ", consoleOut='" + consoleOut + '\'' +
                    ", result=" + result +
                    '}';
        }
    }
}
