# groovy-web-console
A web-based Groovy console written as a pure Java Servlet or Filter

## General
As a developer, it would be very convenient if you could access your
running Java application and write some debug code for runtime analysis.
This is especially useful for debugging purposes.

Using this project enables you to open a web (HTML) based console view
that lets you immediately access your runtime environment using
the <a href="http://groovy-lang.org" target="_blank">groovy langauge</a>.

## Demo
1. Clone the code to your local machine
```console
git clone https://github.com/sniffertine/groovy-web-console.git
```
2. Build the project and run tomcat 7
```console
mvn clean install org.apache.tomcat.maven:tomcat7-maven-plugin:2.2:run
```
3. Open your webbrowser here: <a href="http://localhost:8080" target="_blank">http://localhost:8080</a>

## Setup
The setup is very simple, as the code is availabe via the
<a href="https://mvnrepository.com/artifact/ch.baurs/groovy-web-console" target="_blank">maven central repository</a>.
Just add the dependency to your project pom.xml
```xml
<dependency>
    <groupId>ch.baurs</groupId>
    <artifactId>groovy-web-console</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage
There are three possible usage scenarios.

### 1. Java Servlet

Configure the GroovyConsoleServlet in your web.xml

```xml
<servlet>
    <servlet-name>groovyConsoleServlet</servlet-name>
    <servlet-class>ch.baurs.groovyconsole.GroovyConsoleServlet</servlet-class>
    <init-param>
        <param-name>mappingPath</param-name>
        <param-value>/groovyConsoleServlet</param-value>
    </init-param>
    <init-param>
        <param-name>theme</param-name>
        <param-value>twilight</param-value>
    </init-param>
    <init-param>
        <param-name>prompt</param-name>
        <param-value>console via servlet $></param-value>
    </init-param>
    <!--
            <init-param>
                <param-name>ipAuth</param-name>
                <param-value>127.0.0.1</param-value>
            </init-param>
            <init-param>
                <param-name>envAuth</param-name>
                <param-value>xxx=xxx</param-value>
            </init-param>
    -->
</servlet>
<servlet-mapping>
    <servlet-name>groovyConsoleServlet</servlet-name>
    <url-pattern>/groovyConsoleServlet/*</url-pattern>
</servlet-mapping>
```

### 2. Java Servlet Filter

Configure the GroovyConsoleServlet in your web.xml

```xml
<filter>
    <filter-name>groovyConsoleFilter</filter-name>
    <filter-class>ch.baurs.groovyconsole.GroovyConsoleFilter</filter-class>
    <init-param>
        <param-name>mappingPath</param-name>
        <param-value>/groovyConsoleFilter</param-value>
    </init-param>
    <init-param>
        <param-name>theme</param-name>
        <param-value>twilight</param-value>
    </init-param>
    <init-param>
        <param-name>prompt</param-name>
        <param-value>console via filter $></param-value>
    </init-param>
    <!--
            <init-param>
                <param-name>ipAuth</param-name>
                <param-value>127.0.0.1</param-value>
            </init-param>
            <init-param>
                <param-name>envAuth</param-name>
                <param-value>xxx=xxx</param-value>
            </init-param>
    -->
</filter>
<filter-mapping>
    <filter-name>groovyConsoleFilter</filter-name>
    <url-pattern>/groovyConsoleFilter/*</url-pattern>
    <dispatcher>REQUEST</dispatcher>
</filter-mapping>
```

### 3. Your own code

Configure the Application directly in your onw Java code. Here, I use an empty Servlet to demonstrate.

```xml
<servlet>
    <servlet-name>myServlet</servlet-name>
    <servlet-class>ch.baurs.groovyconsole.testwebapp.MyServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>myServlet</servlet-name>
    <url-pattern>/myServlet/*</url-pattern>
</servlet-mapping>
```

```java
package my.web.project.servlet;

public class MyServlet extends HttpServlet {

    private Application application;

    @Override
    public void init(ServletConfig config) throws ServletException {

        Configuration configuration = Configuration.builder()
                .mappingPath("/myServlet")
                .prompt("console directly configured $>")
                .theme("mdn-like")
                .build();

        application = new Application(configuration);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        application.handle(req, resp);
    }
}

```

## Configuration
...
