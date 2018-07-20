package ch.baurs.groovyconsole;

class GroovyConsoleRenderer {

    private final String formAction;
    private final String statusUrl;
    private String prompt;

    public GroovyConsoleRenderer(String prompt) {
        this.prompt = prompt;
        formAction = UrlHelper.createUrl("execute");
        statusUrl = UrlHelper.createUrl("status");
    }

    public String render() {
        StringBuilder out = new StringBuilder();
        renderHead(out);
        renderBody(out);

        return out.toString();
    }


    private void renderHead(StringBuilder out) {
        out.append("<!doctype html>\n")
                .append("<html lang='en'>\n")
                .append("    <head>\n")
                .append("        <meta characterEncoding='utf-8'>\n")
                .append("        <meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1'>\n")
                .append("        <title>Groovy Web Console</title>\n")
                .append("        <meta name='description' content='A web-based Groovy console written as a pure Java Servlet'>\n")
                .append("        <meta name='viewport' content='width=device-width, initial-scale=1'>\n")
                .append("\n")
                .append(renderCss("codemirror-5.38.0/lib/codemirror.css"))
                .append(renderCss("codemirror-5.38.0/theme/" + Application.getConfiguration().theme + ".css"))
                .append(renderCss("groovyconsole.css"))
                .append("\n")
                .append(renderJs("jquery/jquery-1.11.2.min.js"))
                .append(renderJs("codemirror-5.38.0/lib/codemirror.js"))
                .append(renderJs("codemirror-5.38.0/mode/groovy/groovy.js"))
                .append(renderJs("codemirror-5.38.0/addon/edit/matchbrackets.js"))
                .append(renderJs("polyfill.js"))
                .append(renderJs("groovyconsole.js"))
                .append("\n")
                .append("    </head>\n");

    }

    private void renderBody(StringBuilder out) {
        out.append("    <body>\n");
        out.append("    <div class='groovyConsole' data-theme='" + Application.getConfiguration().theme + "' data-status-url='" + statusUrl + "' data-prompt='" + prompt + "'>\n");
        out.append("        <form action='" + formAction + "' method='post'>\n");
        out.append("            <textarea class='output'></textarea>\n");
        out.append("            <textarea class='input'></textarea>\n");
//        out.append("            <button>submit</button>\n");
        out.append("        </form>\n");
        out.append("    </div>\n");
        out.append("    </body>\n");
        out.append("</html>\n");
    }


    private String renderCss(String path) {
        if (Application.getConfiguration().inlineAssets) {
            return "<style type='text/css'>\n" + FileLoader.readFile(path) + "\n</style>\n";
        } else {
            return "<link rel='stylesheet' type='text/css' href='" + AssetHelper.createAssetUrl(path) + "'/>\n";
        }
    }

    private String renderJs(String path) {
        if (Application.getConfiguration().inlineAssets) {
            return "<script type='application/javascript'>\n" + FileLoader.readFile(path) + "\n</script>\n";
        } else {
            return "<script type='application/javascript' src='" + AssetHelper.createAssetUrl(path) + "'></script>\n";
        }
    }

}
