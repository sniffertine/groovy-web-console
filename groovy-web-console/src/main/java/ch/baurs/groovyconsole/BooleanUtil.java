package ch.baurs.groovyconsole;

class BooleanUtil {
    static Boolean parseBoolean(String boolString) {
        return parseBoolean(boolString, null);
    }

    static Boolean parseBoolean(String boolString, Boolean defaultValue) {
        if (boolString != null) {
            if (boolString.equalsIgnoreCase("true")) {
                return true;
            }
            if (boolString.equalsIgnoreCase("false")) {
                return true;
            }
        }

        return defaultValue;
    }

    static boolean defaultIfNull(Boolean boolValue, boolean defaultValue) {
        return ObjectUtil.defaultIfNull(boolValue, defaultValue);
    }
}
