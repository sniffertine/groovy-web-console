package ch.baurs.groovyconsole;

import org.apache.catalina.LifecycleState;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

@Ignore
public class WebappTest {

    private Tomcat tomcat;

    @Before
    public void setup() throws Throwable {

        //init webapp
        String target = getClass().getResource("/").getPath() + "src/main";
        String workingDir = getClass().getResource("").getPath() + "tomcat_working_dir";
        String contextPath = "/test_webapp";
        String absolutePath = workingDir + contextPath;
        FileUtils.copyFileToDirectory(new File(target + "/groovy-web-console-0.9.1-SNAPSHOT.jar"), new File(absolutePath + "/WEB-INF/lib/"));
        FileUtils.copyFileToDirectory(new File(target + "/groovy-web-console-0.9.1-SNAPSHOT.jar"), new File(workingDir + "/webapps" + contextPath + "/WEB-INF/lib/"));

        //init tomcat
        tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.setBaseDir(workingDir);
        tomcat.getHost().setAppBase(workingDir);
        tomcat.getHost().setAutoDeploy(true);
        tomcat.getHost().setDeployOnStartup(true);

        tomcat.addWebapp(tomcat.getHost(), contextPath, absolutePath);

        tomcat.start();
    }

    @After
    public final void teardown() throws Throwable {
        if (tomcat.getServer() != null
                && tomcat.getServer().getState() != LifecycleState.DESTROYED) {
            if (tomcat.getServer().getState() != LifecycleState.STOPPED) {
                tomcat.stop();
            }
            tomcat.destroy();
        }
    }

    @Test
    public void test() throws Exception {
        Thread.sleep(100000);
    }

    protected int getTomcatPort() {
        return tomcat.getConnector().getLocalPort();
    }

}