package ch.baurs.groovyconsole;


import lombok.Builder;

import java.nio.charset.Charset;

@Builder
class Configuration {

    @Builder.Default
    public final AuthenticationConfiguration authConfig = AuthenticationConfiguration.builder().build();

    @Builder.Default
    public final String contextPath = "";

    @Builder.Default
    public final String mappingPath = "";

    @Builder.Default
    public final String assetsUrlPrefix = Constants.ASSETS_URL_PREFIX;

    @Builder.Default
    public final boolean inlineAssets = false;

    @Builder.Default
    public final Charset characterEncoding = Constants.UTF_8;

    @Builder.Default
    public final String theme = Constants.DEFAULT_THEME;

    @Builder.Default
    public final String prompt = Constants.DEFAULT_PROPMT;

}
