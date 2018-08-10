package ch.baurs.groovyconsole;


import lombok.Builder;
import lombok.ToString;

import java.nio.charset.Charset;

@Builder
@ToString
public class Configuration {

    @Builder.Default
    final AuthenticationConfiguration authConfig = AuthenticationConfiguration.builder().build();

    @Builder.Default
    final String contextPath = "";

    @Builder.Default
    final String mappingPath = Constants.DEFAULT_MAPPING_PATH;

    @Builder.Default
    final String assetsUrlPrefix = Constants.ASSETS_URL_PREFIX;

    @Builder.Default
    final boolean inlineAssets = Constants.DEFAULT_INLINE_ASSETS;

    @Builder.Default
    final Charset characterEncoding = Constants.UTF_8;

    @Builder.Default
    final String theme = Constants.DEFAULT_THEME;

    @Builder.Default
    final String prompt = Constants.DEFAULT_PROPMT;

    @Builder.Default
    final int sessionInactiveInterval = Constants.DEFAULT_SESSION_INACTIVE_INTERVAL;

}
