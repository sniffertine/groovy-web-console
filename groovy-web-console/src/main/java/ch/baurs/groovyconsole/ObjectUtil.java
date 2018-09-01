package ch.baurs.groovyconsole;

 class ObjectUtil {
    static <T> T defaultIfNull(T boolValue, T defaultInlineAssets) {
        return boolValue != null ? boolValue : defaultInlineAssets;
    }

     public static <T> T requireNonNull(T obj, String message) {
         if (obj == null)
             throw new NullPointerException(message);
         return obj;
     }

 }
