package ch.baurs.groovyconsole;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationConfigurationTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Test
    public void testNoAuth() throws Exception {
        AuthenticationConfiguration authConfig = AuthenticationConfiguration.builder().build();

        authConfig.check(request, response);
    }

    @Test
    public void testIpAuth() throws Exception {
        AuthenticationConfiguration authConfig = AuthenticationConfiguration.builder()
                .ipAuth("1\\.2\\.3\\.\\d")
                .build();

        when(request.getRemoteAddr())
                .thenReturn("1.2.3.4")
                .thenReturn("1.2.3.5")
                .thenReturn("9.2.3.5");

        authConfig.check(request, response);
        authConfig.check(request, response);
        try {
            authConfig.check(request, response);
            fail("2.2.3.5 should not be valid");
        } catch (AuthenticationException e) {
            assertEquals("IP restriction", e.getMessage());
        }

    }


    @Test
    public void testEnvAuth() throws Exception {
        AuthenticationConfiguration authConfig = AuthenticationConfiguration.builder()
                .envAuth("envProperty=letMeIn")
                .build();


        //match
        System.setProperty("envProperty", "letMeIn");
        authConfig.check(request, response);


        //non match
        System.setProperty("envProperty", "stayOut");
        try {
            authConfig.check(request, response);
            fail("envAuth should not match stayOut");
        } catch (AuthenticationException e) {
            assertEquals("Environment restriction", e.getMessage());
        }


        //illegal config
        authConfig = AuthenticationConfiguration.builder()
                .envAuth("envPropertyIllegalFormat")
                .build();
        try {
            authConfig.check(request, response);
            fail("envAuth should not work with illegal configuration");
        } catch (AuthenticationException e) {
            assertEquals("Illegal envAuth configuration", e.getMessage());
        }

    }

}