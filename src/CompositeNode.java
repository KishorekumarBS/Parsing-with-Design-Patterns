import org.w3c.dom.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CompositeNode extends HTMLNode {
    public CompositeNode(Node node, ElementByIdFinder elementByIdFinder) {
        super(node);
        this.elementByIdFinder = elementByIdFinder;
    }
    public static CompositeNode createSpecificCompositeNode(Node node, ElementByIdFinder elementByIdFinder) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String tagName = element.getTagName().toLowerCase();

            switch (tagName) {
                case "div":
                    return new DivElement(node, elementByIdFinder);
                case "p":
                    return new ParagraphElement(node, elementByIdFinder);

                default:
                    return new CompositeNode(node, elementByIdFinder);
            }
        }

        return new CompositeNode(node, elementByIdFinder);
    }
    private ElementByIdFinder elementByIdFinder;

    @Override
    public void processJS(String jsContent) {
        // Simple JavaScript-like processing logic for this specific example
        Element div1 = elementByIdFinder.getElementById(node, "div1");
        Element p1 = elementByIdFinder.getElementById(node, "p1");

        // createElement("p");
        Element newP = node.getOwnerDocument().createElement("p");

        // createTextNode("This is new.");
        Text newText = node.getOwnerDocument().createTextNode("This is new.");

        // para.appendChild(node);
        newP.appendChild(newText);

        // element.insertBefore(para, child);
        div1.insertBefore(newP, p1);

        // Remove p2 element
        Element p2 = elementByIdFinder.getElementById(node, "p2");
        div1.removeChild(p2);

        // Update the title using JavaScript code
        Pattern updateTitlePattern = Pattern.compile("updateTitle\\(\"(.*?)\"\\);");
        Matcher updateTitleMatcher = updateTitlePattern.matcher(jsContent);
        if (updateTitleMatcher.find()) {
            String newTitle = updateTitleMatcher.group(1);
            Element htmlElement = node.getOwnerDocument().getDocumentElement();
            Element headElement = (Element) htmlElement.getElementsByTagName("head").item(0);
            Element titleElement = (Element) headElement.getElementsByTagName("title").item(0);
            if (titleElement != null) {
                titleElement.setTextContent(newTitle);
            } else {
                titleElement = node.getOwnerDocument().createElement("title");
                titleElement.setTextContent(newTitle);
                headElement.appendChild(titleElement);
            }
        }
    }

    @Override
    public void processCSS(String cssContent) {
        // Simple CSS-like processing logic for this specific example
        String[] cssLines = cssContent.split("\\r?\\n");

        for (int i = 0; i < cssLines.length; i += 3) {
            if (i + 2 >= cssLines.length) {
                break;
            }
            String tag = cssLines[i].trim();
            String attribute = cssLines[i + 1].trim();
            String location = cssLines[i + 2].trim();

            NodeList nodes = node.getOwnerDocument().getElementsByTagName(tag);
            for (int j = 0; j < nodes.getLength(); j++) {
                Node currentNode = nodes.item(j);

                Comment comment = node.getOwnerDocument().createComment(attribute);
                if (location.equalsIgnoreCase("header")) {
                    currentNode.getParentNode().insertBefore(comment, currentNode);
                } else if (location.equalsIgnoreCase("footer")) {
                    currentNode.getParentNode().insertBefore(comment, currentNode.getNextSibling());
                }
            }
        }
    }
}