package ch.baurs.groovyconsole;

import org.slf4j.LoggerFactory;

public class LoggingHelper {

    private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    private static Boolean slf4jAvailable;

    public static void setClassLoader(ClassLoader classLoader) {
        LoggingHelper.classLoader = classLoader;
    }

    static void error(Class<?> clazz, String message) {
        error(clazz, message, null);
    }

    static void error(Class<?> clazz, String message, Throwable e) {
        if (slf4jAvailable()) {
            LoggerFactory.getLogger(clazz).error(message, e);
        }
    }

    static void warn(Class<?> clazz, String message) {
        warn(clazz, message, null);
    }

    static void warn(Class<?> clazz, String message, Throwable e) {
        if (slf4jAvailable()) {
            LoggerFactory.getLogger(clazz).warn(message, e);
        }
    }

    static void info(Class<?> clazz, String message) {
        info(clazz, message, null);
    }

    static void info(Class<?> clazz, String message, Throwable e) {
        if (slf4jAvailable()) {
            LoggerFactory.getLogger(clazz).info(message, e);
        }
    }

    static void debug(Class<?> clazz, String message) {
        debug(clazz, message, null);
    }

    static void debug(Class<?> clazz, String message, Throwable e) {
        if (slf4jAvailable()) {
            LoggerFactory.getLogger(clazz).debug(message, e);
        }
    }

    static void trace(Class<?> clazz, String message) {
        trace(clazz, message, null);
    }

    static void trace(Class<?> clazz, String message, Throwable e) {
        if (slf4jAvailable()) {
            LoggerFactory.getLogger(clazz).trace(message, e);
        }
    }

    private static boolean slf4jAvailable() {
        if (slf4jAvailable == null) {
            try {
                classLoader.loadClass("org.slf4j.LoggerFactory");
                slf4jAvailable = true;
            } catch (ClassNotFoundException e) {
                slf4jAvailable = false;
            }
        }

        return slf4jAvailable;
    }
}
