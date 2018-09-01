package ch.baurs.groovyconsole;


import lombok.Builder;
import lombok.ToString;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Builder
@ToString
public class AccessConfiguration {

    @Builder.Default
    public final String ipAuth = null;

    @Builder.Default
    public final String envAuth = null;

    protected void check(HttpServletRequest request, HttpServletResponse response) throws AccessException {
        checkIpRestriction(request);
        checkEnvironmentRestriction(request);
    }

    protected void checkIpRestriction(HttpServletRequest request) throws AccessException {
        if (ipAuth != null) {
            String ip = request.getRemoteAddr();

            if (!ip.matches(ipAuth)) {
                throw new AccessException("IP restriction");
            }
        }

    }

    protected void checkEnvironmentRestriction(HttpServletRequest request) throws AccessException {
        if (envAuth != null) {
            String[] parts = splitOrThrow(envAuth, "=", "Illegal envAuth configuration");
            String envAuthKey = parts[0];
            String envAuthValue = parts[1];

            String sysValue = System.getProperty(envAuthKey);

            if (sysValue == null || !sysValue.matches(envAuthValue)) {
                throw new AccessException("Environment restriction");
            }
        }
    }

    private String[] splitOrThrow(String string, String splitBy, String errorMessage) throws AccessException {
        if (string == null || !string.contains(splitBy)) {
            throw new AccessException(errorMessage);
        }

        return string.split(splitBy);
    }
}
