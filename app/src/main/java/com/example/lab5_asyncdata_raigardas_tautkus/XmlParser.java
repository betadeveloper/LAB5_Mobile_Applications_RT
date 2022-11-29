package com.example.lab5_asyncdata_raigardas_tautkus;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlParser {
    public static String parseExchangeRates(InputStream stream) throws IOException {
        StringBuilder result = new StringBuilder();
        try {
            DocumentBuilderFactory xmlDocFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder xmlDocBuilder = xmlDocFactory.newDocumentBuilder();
            Document doc = xmlDocBuilder.parse(stream);

            NodeList currencyNodes = doc.getElementsByTagName(Constants.ITEM);
            for (int i = 0; i < currencyNodes.getLength(); ++i) {
                Node currencyNode = currencyNodes.item(i);
                if (currencyNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) currencyNode;
                    result.append(XmlParser.buildResult(element)).append("\n");
                }
            }
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private static String getTagValue(Element el, String tagName) {
        return el.getElementsByTagName(tagName).item(0).getTextContent();
    }

    private static String buildResult(Element el) {
        return  XmlParser.getTagValue(el, Constants.TARGET) + "/" +
                XmlParser.getTagValue(el, Constants.BASE) + " Conversion \n" +
                XmlParser.getTagValue(el, Constants.RATE) + " - " +
                XmlParser.getTagValue(el, Constants.DESCRIPTION) + "\n " +
                XmlParser.getTagValue(el, Constants.INVERSEDESCRIPTION)+ " \n";
    }
}
