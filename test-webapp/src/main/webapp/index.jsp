<jsp:useBean id="XmlFileContentReader" class="ch.baurs.groovyconsole.testwebapp.util.XmlFileContentReader"/>

<h1>Groovy Web Console Demo</h1>
<p>
    Welcome the the test webapp for the groovy-web-console. You can access examples of the three possible ways of usage from here.
</p>
<p>
    For more details and documentation read: <a href="https://github.com/sniffertine/groovy-web-console" target="_blank">https://github.com/sniffertine/groovy-web-console</a>
</p>


<h2>1. Built-in Filter --> GroovyConsoleFilter</h2>
<p>
    The GroovyConsoleFilter is configured in the web.xml file (test-webapp/src/main/webapp/WEB-INF/web.xml). <br>
    <br>
    To access this Groovy Console Instance, go to <a href="http://localhost:8080/groovyConsoleFilter" target="_blank">http://localhost:8080/groovyConsoleFilter</a>.
</p>
<pre lang="xml">
${XmlFileContentReader.readFromWebXml("/web-app/filter[1]")}
${XmlFileContentReader.readFromWebXml("/web-app/filter-mapping[1]")}
</pre>


<h2>2. Built-in Servlet --> GroovyConsoleServlet</h2>
<p>
    The GroovyConsoleServlet is configured in the web.xml file (test-webapp/src/main/webapp/WEB-INF/web.xml). <br>
    <br>
    To access this Groovy Console Instance, go to <a href="http://localhost:8080/groovyConsoleServlet" target="_blank">http://localhost:8080/groovyConsoleServlet</a>.
</p>
<pre lang="xml">
${XmlFileContentReader.readFromWebXml("/web-app/servlet[1]")}
${XmlFileContentReader.readFromWebXml("/web-app/servlet-mapping[1]")}
</pre>


<h2>3. Custom Servlet --> by code in your own servlet</h2>
<p>
    The Servlet MyServlet is configured in the web.xml file (test-webapp/src/main/webapp/WEB-INF/web.xml).
    But all parameters and instantiation of the Application are done directly in code.<br>
    <br>
    To access this Groovy Console Instance, go to <a href="http://localhost:8080/myServlet" target="_blank">http://localhost:8080/myServlet</a>.
</p>
<pre lang="xml">
${XmlFileContentReader.readFromWebXml("/web-app/servlet[2]")}
${XmlFileContentReader.readFromWebXml("/web-app/servlet-mapping[2]")}
</pre>
