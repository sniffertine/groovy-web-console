package ch.baurs.groovyconsole.testwebapp.util;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public class XerxesFormatter {

    public String format(String unformattedXml, int indent) {
        try {
            Document document = parseXmlFile(unformattedXml);

            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(100000);
            format.setIndenting(true);
            format.setIndent(indent);
            format.setOmitXMLDeclaration(true);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);

            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * This function converts String XML to Document object
     *
     * @param in - XML String
     * @return Document object
     */
    private Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));

            return db.parse(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}