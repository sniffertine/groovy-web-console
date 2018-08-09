package ch.baurs.groovyconsole.testwebapp.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

public class FileIncludeUtil {

    public static String readXmlContent(String fileName) throws IOException {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(fileName);
        String string = IOUtils.resourceToString(fileName, Charset.forName("UTF-8"), Thread.currentThread().getContextClassLoader());
        return StringEscapeUtils.escapeXml11(string);
    }

}
