package ch.baurs.groovyconsole.testwebapp.util;

import org.apache.commons.text.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;

public class XmlFileContentReader {

    public static String readFromWebXml(String xpath) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        ServletContext sc = ServletContextHolder.getServletContext();

        URL webXml = sc.getResource("WEB-INF/web.xml");

        return readFromUrl(webXml, xpath);
    }

    public static String readFromUrl(URL url, String xpathQuery) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        factory.setIgnoringComments(true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(url.openStream());
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        Node node = (Node) xpath.evaluate(xpathQuery, doc, XPathConstants.NODE);

        String nodeString = getNodeString(node);
        String formatted = formatByXerces(nodeString, 4);

        return StringEscapeUtils.escapeXml11(formatted);
    }

    private static String getNodeString(Node node) {
        try {
            StringWriter writer = new StringWriter();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", 4);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "40");
            transformer.transform(new DOMSource(node), new StreamResult(writer));

            return writer.toString();
        } catch (TransformerException e) {
            throw new IllegalStateException("Could not parse node", e);
        }
    }

    public static String formatByXerces(String xml, int indent) {
        return new XerxesFormatter().format(xml, indent);
    }

}
