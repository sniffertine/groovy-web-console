package ch.baurs.groovyconsole;

import javax.servlet.http.HttpServletRequest;

class UrlHelper {

    private static final String SLASH = "/";

    public static String joinSegmentsToPath(String... segments) {
        StringBuilder result = new StringBuilder();

        for (String segment : segments) {
            result.append(SLASH).append(removeStartAndEndSlash(segment));
        }

        return result.toString().replaceAll("//+", "/");
    }

    public static String removeStartAndEndSlash(String string) {
        if (string.startsWith(SLASH)) {
            string = string.substring(1);
        }
        if (string.endsWith(SLASH)) {
            string = string.substring(0, string.length() - 1);
        }
        return string;
    }

    public static String createUrl(String pathRelativeToGroovyConsole) {
        return joinSegmentsToPath(
                Application.getConfiguration().contextPath,
                Application.getConfiguration().mappingPath,
                pathRelativeToGroovyConsole
        );
    }

    public static String getFullRequestURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

}
