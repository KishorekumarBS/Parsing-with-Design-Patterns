import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Concrete Decorator class to process and apply CSS styles to elements.
class CSSDecoratorNode extends DecoratorNode {
    public CSSDecoratorNode(HTMLNode decoratedNode) {
        super(decoratedNode);
    }
    // Overrides the processCSS method to parse and apply CSS styles to elements.
    @Override
    public void processCSS(String cssContent) {
        // Call the processCSS method on the decorated node
        super.processCSS(cssContent);

        // Parse CSS content and store rules in a HashMap
        Map<String, String> cssRules = new HashMap<>();
        Pattern cssPattern = Pattern.compile("(.*?)\\{(.*?)\\}");
        Matcher cssMatcher = cssPattern.matcher(cssContent);

        while (cssMatcher.find()) {
            String tag = cssMatcher.group(1).trim();
            String attributes = cssMatcher.group(2).trim().replaceAll("\\s+", "");
            cssRules.put(tag, attributes);
        }

        // Apply CSS rules to the elements in the document
        for (Map.Entry<String, String> entry : cssRules.entrySet()) {
            String tag = entry.getKey();
            String attributes = entry.getValue();

            NodeList nodes = node.getOwnerDocument().getElementsByTagName(tag);
            for (int j = 0; j < nodes.getLength(); j++) {
                Node currentNode = nodes.item(j);

                if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) currentNode;
                    String styleAttribute = element.getAttribute("style");

                    // Append the new style attribute
                    styleAttribute = styleAttribute.isEmpty() ? attributes : styleAttribute + "; " + attributes;

                    // Set the updated style attribute to the element
                    element.setAttribute("style", styleAttribute);
                }
            }
        }
    }

}