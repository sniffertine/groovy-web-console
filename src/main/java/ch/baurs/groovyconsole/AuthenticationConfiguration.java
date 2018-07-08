package ch.baurs.groovyconsole;


import lombok.Builder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Builder
class AuthenticationConfiguration {

    @Builder.Default
    public final String ipAuth = null;

    @Builder.Default
    public final String envAuth = null;

    public void check(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        checkIpRestriction(request);
        checkEnvironmentRestriction(request);
    }

    protected void checkIpRestriction(HttpServletRequest request) throws AuthenticationException {
        if (ipAuth != null) {
            String ip = request.getRemoteAddr();

            if (!ip.matches(ipAuth)) {
                throw new AuthenticationException("IP restriction");
            }
        }

    }

    protected void checkEnvironmentRestriction(HttpServletRequest request) throws AuthenticationException {
        if (envAuth != null) {
            String[] parts = split(envAuth, "=", "Illegal envAuth configuration");
            String envAuthKey = parts[0];
            String envAuthValue = parts[1];

            String sysValue = System.getProperty(envAuthKey);

            if (sysValue == null || !sysValue.matches(envAuthValue)) {
                throw new AuthenticationException("Environment restriction");
            }
        }
    }

    private String[] split(String string, String splitBy, String errorMessage) throws AuthenticationException {
        if (string == null || !string.contains(splitBy)) {
            throw new AuthenticationException(errorMessage);
        }

        return string.split(splitBy);
    }
}
