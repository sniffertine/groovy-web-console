<jsp:useBean id="FileIncludeUtil" class="ch.baurs.groovyconsole.testwebapp.util.FileIncludeUtil"/>
<h1>Groovy Web Console Test Webapp</h1>
<p>
    Welcome the the test webapp for the groovy-web-console. You can access the three possible ways of usage from here
</p>


<h2>1. Filter --> GroovyConsoleFilter</h2>
<p>
    The GroovyConsoleFilter is configured in the web.xml file (test-webapp/src/main/webapp/WEB-INF/web.xml). <br>
    <br>
    To access this Groovy Console Instance, go to <a href="http://localhost:8080/groovyConsoleFilter" target="_blank">http://localhost:8080/groovyConsoleFilter</a>.
</p>
<pre lang="xml">
${FileIncludeUtil.readXmlContent("GroovyConsoleFilter_config.xml")}
</pre>


<h2>2. Servlet --> GroovyConsoleServlet</h2>
<p>
    The GroovyConsoleServlet is configured in the web.xml file (test-webapp/src/main/webapp/WEB-INF/web.xml). <br>
    <br>
    To access this Groovy Console Instance, go to <a href="http://localhost:8080/groovyConsoleServlet" target="_blank">http://localhost:8080/groovyConsoleServlet</a>.
</p>
<pre lang="xml">
${FileIncludeUtil.readXmlContent("GroovyConsoleServlet_config.xml")}
</pre>


<h2>3. Servlet --> by code in your own servlet</h2>
<p>
    The Servlet MyServlet is configured in the web.xml file (test-webapp/src/main/webapp/WEB-INF/web.xml).
    But all parameters and instantiation of the Application are done directly in code.<br>
    <br>
    To access this Groovy Console Instance, go to <a href="http://localhost:8080/myServlet" target="_blank">http://localhost:8080/myServlet</a>.
</p>
<pre lang="xml">
${FileIncludeUtil.readXmlContent("MyServlet_config.xml")}
</pre>
