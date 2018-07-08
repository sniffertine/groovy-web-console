package ch.baurs.groovyconsole;

import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;

import static ch.baurs.groovyconsole.StringUtil.defaultString;

class ConfigurationHelper {

    private static final String PARAM_MAPPING_PATH = "mappingPath";
    private static final String PARAM_IP_AUTH = "ipAuth";
    private static final String PARAM_ENV_AUTH = "envAuth";
    private static final String PARAM_ATTR_AUTH = "attrAuth";

    static Configuration ofServletConfig(ServletConfig config) {
        String contextPath = config.getServletContext().getContextPath();
        String mappingPath = config.getInitParameter(PARAM_MAPPING_PATH);

        String ipAuth = config.getInitParameter(PARAM_IP_AUTH);
        String envAuth = config.getInitParameter(PARAM_ENV_AUTH);
        String attrAuth = config.getInitParameter(PARAM_ATTR_AUTH);

        return of(contextPath, mappingPath, ipAuth, envAuth, attrAuth);
    }

    static Configuration ofFilterConfig(FilterConfig config) {
        String contextPath = config.getServletContext().getContextPath();
        String mappingPath = config.getInitParameter(PARAM_MAPPING_PATH);

        String ipAuth = config.getInitParameter(PARAM_IP_AUTH);
        String envAuth = config.getInitParameter(PARAM_ENV_AUTH);
        String attrAuth = config.getInitParameter(PARAM_ATTR_AUTH);

        return of(contextPath, mappingPath, ipAuth, envAuth, attrAuth);
    }


    private static Configuration of(String contextPath, String mappingPath, String ipAuth, String envAuth, String attrAuth) {
        AuthenticationConfiguration authConfig = AuthenticationConfiguration.builder()
                .ipAuth(ipAuth)
                .envAuth(envAuth)
                .build();

        return Configuration.builder()
                .authConfig(authConfig)
                .contextPath(defaultString(contextPath))
                .mappingPath(defaultString(mappingPath))
                .inlineAssets(false)
                .characterEncoding(Constants.UTF_8)
                .theme(Constants.DEFAULT_THEME)
                .build();
    }
}
