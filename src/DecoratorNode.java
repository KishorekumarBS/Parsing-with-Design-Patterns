// This code demonstrates the Decorator Design Pattern in a context of HTML processing.
class DecoratorNode extends HTMLNode {
    protected HTMLNode decoratedNode;

    // Constructor takes an HTMLNode as an argument to initialize the decoratedNode.
    public DecoratorNode(HTMLNode decoratedNode) {
        super(decoratedNode.node);
        this.decoratedNode = decoratedNode;
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

}
