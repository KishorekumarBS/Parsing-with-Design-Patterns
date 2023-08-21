import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.xml.parsers.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimplifiedHTMLParser {
    public static void main(String[] args) {
        // Load HTML, JS, and CSS files
        String htmlContent = readFromFile("/Users/patron/Desktop/Project2part2/Designp/src/input.html");
        String jsContent = readFromFile("/Users/patron/Desktop/Project2part2/Designp/src/inputjs.js");
        String cssContent = readFromFile("/Users/patron/Desktop/Project2part2/Designp/src/inputcss.css");

        // Parse HTML content
        Document document = parseHTMLContent(htmlContent);

        // Create a specific CompositeNode from the parsed document
        CompositeNode rootNode = CompositeNode.createSpecificCompositeNode(document.getDocumentElement(), SimplifiedHTMLParser::getElementById);

        // Decorate rootNode with CSSDecoratorNode, ClassAttributeDecorator, and HeadElementDecorator
        CSSDecoratorNode cssDecoratorNode = new CSSDecoratorNode(rootNode);
        ClassAttributeDecorator classAttributeDecorator = new ClassAttributeDecorator(cssDecoratorNode, "sample-class");
        HeadElementDecorator headElementDecorator = new HeadElementDecorator(classAttributeDecorator, "Sample HTML");

        // Process JS content using the decorator
        headElementDecorator.processJS(jsContent);

        // Process CSS content using the decorator
        headElementDecorator.processCSS(cssContent);

        // Add the class attribute to the elements
        classAttributeDecorator.addClassAttributeToElements("p"); // Add the class attribute to <p> elements, for example

        // Update the title and head element
        headElementDecorator.updateTitleAndHead();

        // Update the output with the new elements
        addStyleTagToHead(document, cssContent);
        saveToFile(rootNode, "output.html");
        System.out.println("Output.html file created parsing successful");
    }
    // Adds a new style tag with the specified CSS content to the head element of the given document
    private static void addStyleTagToHead(Document document, String cssContent) {
        // Locate the head element in the document
        Element head = (Element) document.getElementsByTagName("head").item(0);
        // Create a new style element and set its type attribute
        Element style = document.createElement("style");
        style.setAttribute("type", "text/css");
        // Set the CSS content as the text content of the style element
        style.setTextContent(cssContent);
        // Append the style element to the head
        head.appendChild(style);
    }

    // Reads the content of a file and returns it as a string
    private static String readFromFile(String fileName) {
        try {
            // Read all bytes from the file and convert them to a string
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            // Print the stack trace if there's an exception and return an empty string
            e.printStackTrace();
            return "";
        }
    }

    // Parses an HTML string and returns a Document object representing the parsed content
    private static Document parseHTMLContent(String htmlContent) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            // Parse the HTML content and return the resulting Document object
            return builder.parse(new InputSource(new StringReader(htmlContent)));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // Print the stack trace if there's an exception and return null
            e.printStackTrace();
            return null;
        }
    }

    // Recursively searches for an element with a given id attribute in a node tree rooted at the given rootNode
    private static Element getElementById(Node rootNode, String id) {
        if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) rootNode;
            if (id.equals(element.getAttribute("id"))) {
                return element;
            }
        }

        // Iterate through the child nodes and recursively search for the element with the given id
        NodeList children = rootNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Element found = getElementById(children.item(i), id);
            if (found != null) {
                return found;
            }
        }

        // Return null if the element is not found
        return null;
    }

    // Serializes a Node object to its string representation
    private static String serializeNode(Node node) {
        StringBuilder builder = new StringBuilder();
        serializeNodeHelper(node, builder);
        return builder.toString();
    }
    // Helper method for recursively serializing a Node object to a StringBuilder
    private static void serializeNodeHelper(Node node, StringBuilder builder) {
        // Append the text content if it's a text node
        if (node.getNodeType() == Node.TEXT_NODE) {
            builder.append(node.getTextContent());
        } else if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            builder.append("<").append(element.getTagName());

            // Append the attributes of the element
            NamedNodeMap attributes = element.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                Node attribute = attributes.item(i);
                builder.append(" ").append(attribute.getNodeName()).append("=\"").append(attribute.getNodeValue()).append("\"");
            }

            // Append the 'style' attribute if it exists
            if (element.hasAttribute("style")) {
                builder.append(" style=\"").append(element.getAttribute("style")).append("\"");
            }

            builder.append(">");
            // Recursively serialize the child nodes
            NodeList children = element.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                serializeNodeHelper(children.item(i), builder);
            }
            builder.append("</").append(element.getTagName()).append(">");
        } else if (node.getNodeType() == Node.COMMENT_NODE) {
            // Append the comment data if it's a comment node
            Comment comment = (Comment) node;
            builder.append("<!--").append(comment.getData()).append("-->");
        }
    }



    private static void saveToFile(CompositeNode rootNode, String fileName) {
        try {
            FileSaverFactory fileSaverFactory = new FileSaverFactory();
            FileSaver htmlFileSaver = fileSaverFactory.createHTMLFileSaver(fileName);
            String modifiedHtmlContent = serializeNode(rootNode.node);
            htmlFileSaver.save(modifiedHtmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

interface ElementByIdFinder {
    Element getElementById(Node rootNode, String id);
}








