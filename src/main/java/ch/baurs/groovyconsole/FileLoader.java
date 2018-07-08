package ch.baurs.groovyconsole;

import java.io.InputStream;

class FileLoader {

    static String readFile(String path) {
        InputStream inputStream = getResourceAsStream(path);

        if (inputStream == null) {
            throw new IllegalArgumentException("Could not read file from path: " + path);
        }

        return streamToString(inputStream);
    }

    static InputStream getResourceAsStream(String path) {
        return FileLoader.class.getResourceAsStream(path);
    }

    static String streamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
