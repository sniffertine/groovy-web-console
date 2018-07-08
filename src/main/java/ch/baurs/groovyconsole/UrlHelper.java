package ch.baurs.groovyconsole;

class UrlHelper {

    private static final String SLASH = "/";

    public static String joinSegmentsToPath(String... segments) {
        StringBuilder result = new StringBuilder();

        for (String segment : segments) {
            result.append(SLASH).append(removeStartAndEndSlash(segment));
        }

        return result.toString();
    }

    private static String removeStartAndEndSlash(String string) {
        if (string.startsWith(SLASH)) {
            string = string.substring(1);
        }
        if (string.endsWith(SLASH)) {
            string = string.substring(0, string.length() - 1);
        }
        return string;
    }

    public static String createUrl(String pathRelativeToGroovyConsole) {
        return UrlHelper.joinSegmentsToPath(
                Application.getConfiguration().contextPath,
                Application.getConfiguration().mappingPath,
                pathRelativeToGroovyConsole
        );
    }
}
