package ch.baurs.groovyconsole;


import java.util.ArrayList;
import java.util.List;

class GroovyConsole {

    private final GroovyConsoleRenderer renderer;
    private final GroovyConsoleShell shell;
    private final String prompt;
    private final String resultPrompt;

    public GroovyConsole() {
        this.prompt = Application.getConfiguration().prompt + "$";
        this.renderer = new GroovyConsoleRenderer(prompt);
        this.shell = new GroovyConsoleShell();
        this.resultPrompt = "------->";
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
            sb.append(prompt).append(" ").append(execution.scriptText).append("\n");

            String consoleOut = toString(execution.consoleOut);
            String consoleResult = toString(execution.result);
            sb.append(resultPrompt).append(" ").append(consoleOut).append(consoleResult).append("\n");
        }

        return sb.toString();
    }

    private static String toString(Object string) {
        return string == null ? "" : String.valueOf(string);
    }

    public List<String> getHistory() {
        List<String> result = new ArrayList<String>();

        List<GroovyConsoleShell.Execution> executions = shell.getExecutions();
        for (GroovyConsoleShell.Execution execution : executions) {
            result.add(execution.scriptText);
        }

        return result;
    }
}



