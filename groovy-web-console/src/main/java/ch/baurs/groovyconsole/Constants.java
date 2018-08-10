package ch.baurs.groovyconsole;

import java.nio.charset.Charset;

class Constants {

    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static final String ASSETS_URL_PREFIX = "assets/";

    public static final String DEFAULT_MAPPING_PATH = "/groovyWebConsole";

    public static final String DEFAULT_THEME = "vibrant-ink";

    public static final String DEFAULT_PROPMT = "gc$";

    public static final boolean DEFAULT_INLINE_ASSETS = false;

    public static final int DEFAULT_SESSION_INACTIVE_INTERVAL = 24 * 3600; //24 hours;
}
