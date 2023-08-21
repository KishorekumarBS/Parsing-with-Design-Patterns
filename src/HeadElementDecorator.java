import org.w3c.dom.Element;
import org.w3c.dom.Node;

// Concrete Decorator class to modify the <head> element's title.
class HeadElementDecorator extends DecoratorNode {
    private String newTitle;

    public HeadElementDecorator(HTMLNode decoratedNode, String newTitle) {
        super(decoratedNode);
        this.newTitle = newTitle;
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
    // Updates the title of the <head> element or creates one if it doesn't exist.
    public void updateTitleAndHead() {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            if (element.getTagName().equalsIgnoreCase("html")) {
                Element head = (Element) element.getElementsByTagName("head").item(0);
                Element title = (Element) head.getElementsByTagName("title").item(0);

                if (title != null) {
                    title.setTextContent(newTitle);
                } else {
                    title = element.getOwnerDocument().createElement("title");
                    title.setTextContent(newTitle);
                    head.appendChild(title);
                }
            }
        }
    }
}