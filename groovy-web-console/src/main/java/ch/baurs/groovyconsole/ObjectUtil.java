package ch.baurs.groovyconsole;

public class ObjectUtil {
    static <T> T defaultIfNull(T boolValue, T defaultInlineAssets) {
        return boolValue != null ? boolValue : defaultInlineAssets;
    }

}
