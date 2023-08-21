import org.w3c.dom.Node;

abstract class HTMLNode {
    protected Node node;

    public HTMLNode(Node node) {
        this.node = node;
    }

    public abstract void processJS(String jsContent);
    public abstract void processCSS(String cssContent);
}