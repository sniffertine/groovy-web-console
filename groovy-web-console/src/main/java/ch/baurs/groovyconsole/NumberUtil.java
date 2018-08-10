package ch.baurs.groovyconsole;

public class NumberUtil {
    public static Integer parseInt(String number, Integer defaultValue) {
        if (StringUtil.isNotBlank(number)) {
            try {
                return Integer.parseInt(number);
            } catch (NumberFormatException ignored) {
            }
        }
        return defaultValue;
    }
}
