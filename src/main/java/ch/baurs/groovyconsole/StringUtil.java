package ch.baurs.groovyconsole;

class StringUtil {

    static final String EMPTY = "";

    static String defaultString(final String str) {
        return str == null ? EMPTY : str;
    }

    static String defaultString(final String str, final String defaultStr) {
        return str == null ? defaultStr : str;
    }

    static <T extends CharSequence> T defaultIfBlank(final T str, final T defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

}
