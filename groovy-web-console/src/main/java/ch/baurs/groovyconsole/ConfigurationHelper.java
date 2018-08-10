package ch.baurs.groovyconsole;

import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;

import static ch.baurs.groovyconsole.BooleanUtil.defaultIfNull;
import static ch.baurs.groovyconsole.StringUtil.defaultString;

class ConfigurationHelper {

    private static final String PARAM_MAPPING_PATH = "mappingPath";
    private static final String PARAM_THEME = "theme";
    private static final String PARAM_PROMPT = "prompt";
    private static final String PARAM_INLINE_ASSETS = "inlineAssets";
    private static final String PARAM_IP_AUTH = "ipAuth";
    private static final String PARAM_ENV_AUTH = "envAuth";
    private static final String PARAM_SESSION_INACTIVE_INTERVAL = "sessionInactiveInterval";

    static Configuration ofServletConfig(ServletConfig config) {
        String contextPath = config.getServletContext().getContextPath();
        String mappingPath = config.getInitParameter(PARAM_MAPPING_PATH);
        String theme = config.getInitParameter(PARAM_THEME);
        String prompt = config.getInitParameter(PARAM_PROMPT);
        Boolean inlineAssets = BooleanUtil.parseBoolean(config.getInitParameter(PARAM_INLINE_ASSETS));
        int sessionInactiveInterval = NumberUtil.parseInt(config.getInitParameter(PARAM_SESSION_INACTIVE_INTERVAL), Constants.DEFAULT_SESSION_INACTIVE_INTERVAL);

        String ipAuth = config.getInitParameter(PARAM_IP_AUTH);
        String envAuth = config.getInitParameter(PARAM_ENV_AUTH);

        return of(contextPath, mappingPath, ipAuth, envAuth, theme, prompt, inlineAssets, sessionInactiveInterval);
    }

    static Configuration ofFilterConfig(FilterConfig config) {
        String contextPath = config.getServletContext().getContextPath();
        String mappingPath = config.getInitParameter(PARAM_MAPPING_PATH);
        String theme = config.getInitParameter(PARAM_THEME);
        String prompt = config.getInitParameter(PARAM_PROMPT);
        Boolean inlineAssets = BooleanUtil.parseBoolean(config.getInitParameter(PARAM_INLINE_ASSETS));
        int sessionInactiveInterval = NumberUtil.parseInt(config.getInitParameter(PARAM_SESSION_INACTIVE_INTERVAL), Constants.DEFAULT_SESSION_INACTIVE_INTERVAL);

        String ipAuth = config.getInitParameter(PARAM_IP_AUTH);
        String envAuth = config.getInitParameter(PARAM_ENV_AUTH);

        return of(contextPath, mappingPath, ipAuth, envAuth, theme, prompt, inlineAssets, sessionInactiveInterval);
    }

    private static Configuration of(String contextPath, String mappingPath, String ipAuth, String envAuth, String theme, String prompt, Boolean inlineAssets, int sessionInactiveInterval) {
        AuthenticationConfiguration authConfig = AuthenticationConfiguration.builder()
                .ipAuth(ipAuth)
                .envAuth(envAuth)
                .build();

        return Configuration.builder()
                .authConfig(authConfig)
                .contextPath(defaultString(contextPath))
                .mappingPath(defaultString(mappingPath, Constants.DEFAULT_MAPPING_PATH))
                .inlineAssets(defaultIfNull(inlineAssets, Constants.DEFAULT_INLINE_ASSETS))
                .characterEncoding(Constants.UTF_8)
                .theme(defaultString(theme, Constants.DEFAULT_THEME))
                .prompt(defaultString(prompt, Constants.DEFAULT_PROPMT))
                .sessionInactiveInterval(sessionInactiveInterval)
                .build();
    }

}
