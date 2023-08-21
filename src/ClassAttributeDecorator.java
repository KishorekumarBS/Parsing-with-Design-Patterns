import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

// Concrete Decorator class to add a class attribute to an element.
class ClassAttributeDecorator extends DecoratorNode {
    private String className;

    public ClassAttributeDecorator(HTMLNode decoratedNode, String className) {
        super(decoratedNode);
        this.className = className;
    }

    // Delegates the processJS method call to the decoratedNode.
    @Override
    public void processJS(String jsContent) {
        decoratedNode.processJS(jsContent);
    }

    // Delegates the processCSS method call to the decoratedNode.
    @Override
    public void processCSS(String cssContent) {
        decoratedNode.processCSS(cssContent);
    }

    // Add this new method to the ClassAttributeDecorator class
    public void addClassAttributeToElements(String tagName) {
        NodeList elements = node.getOwnerDocument().getElementsByTagName(tagName);
        for (int i = 0; i < elements.getLength(); i++) {
            Element element = (Element) elements.item(i);
            element.setAttribute("class", className);
        }
    }
}
