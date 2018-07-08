package ch.baurs.groovyconsole;

import org.slf4j.LoggerFactory;

public class LoggingHelper {

    private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    private static Boolean slf4jAvailable;

    public static void error(Class<?> clazz, String message, Throwable e) {
        if (slf4jAvailable()) {
            LoggerFactory.getLogger(clazz).error(message, e);
        }
    }

    public static void warn(Class<?> clazz, String message, Throwable e) {
        if (slf4jAvailable()) {
            LoggerFactory.getLogger(clazz).warn(message, e);
        }
    }

    public static void info(Class<?> clazz, String message, Throwable e) {
        if (slf4jAvailable()) {
            LoggerFactory.getLogger(clazz).info(message, e);
        }
    }

    public static void debug(Class<?> clazz, String message, Throwable e) {
        if (slf4jAvailable()) {
            LoggerFactory.getLogger(clazz).debug(message, e);
        }
    }

    public static void trace(Class<?> clazz, String message, Throwable e) {
        if (slf4jAvailable()) {
            LoggerFactory.getLogger(clazz).trace(message, e);
        }
    }

    public static void setClassLoader(ClassLoader classLoader) {
        LoggingHelper.classLoader = classLoader;
    }

    private static boolean slf4jAvailable() {

        if (slf4jAvailable == null) {
            try {
                classLoader.loadClass("org.slf4j.LoggerFactory_");
                slf4jAvailable = true;
            } catch (ClassNotFoundException e) {
                slf4jAvailable = false;
            }
        }
        return slf4jAvailable;
    }
}
