package ch.baurs.groovyconsole;


import java.util.ArrayList;
import java.util.List;

class GroovyConsole {

    public static final String NL = "\n";
    private final GroovyConsoleRenderer renderer;
    private final GroovyConsoleShell shell;
    private final String resultPrompt;

    public GroovyConsole() {
        this.renderer = new GroovyConsoleRenderer();
        this.shell = new GroovyConsoleShell();
        this.resultPrompt = StringUtil.repeat("-", Application.getConfiguration().prompt.length() - 1) + ">";
    }

    public String render() {
        return renderer.render();
    }


    public void execute(String code) {
        shell.execute(code);
    }

    public String printExecutionState() {
        StringBuilder sb = new StringBuilder();

        for (GroovyConsoleShell.Execution execution : shell.getExecutions()) {
            sb.append(Application.getConfiguration().prompt).append(" ").append(execution.scriptText).append(NL);

            String consoleOut = toString(execution.consoleOut);
            String consoleResult = toString(execution.result);
            sb.append(resultPrompt).append(" ").append(consoleOut).append(consoleResult).append(NL);
        }

        return StringUtil.removeEnd(sb.toString(), NL);
    }

    private static String toString(Object string) {
        return string == null ? "" : String.valueOf(string);
    }

    public List<String> getHistory() {
        List<String> result = new ArrayList<String>();

        List<GroovyConsoleShell.Execution> executions = shell.getExecutions();
        for (GroovyConsoleShell.Execution execution : executions) {
            String scriptText = execution.scriptText;
            if(StringUtil.isNotEmpty(scriptText)) {
                result.add(scriptText);
            }
        }

        return result;
    }
}



