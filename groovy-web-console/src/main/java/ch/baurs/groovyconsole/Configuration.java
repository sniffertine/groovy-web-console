package ch.baurs.groovyconsole;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.nio.charset.Charset;

@Builder
@Getter
@ToString
public class Configuration {

    @Builder.Default
    final AccessConfiguration authConfig = AccessConfiguration.builder().build();

    @Builder.Default
    final String contextPath = "";

    @Builder.Default
    final String mappingPath = Constants.DEFAULT_MAPPING_PATH;

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
