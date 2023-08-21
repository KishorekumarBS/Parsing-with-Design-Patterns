# Parsing-with-Design-Patterns
This project employs a combination of design patterns – the Decorator Pattern, Composite Pattern, and Factory Method Pattern – to enhance the modularity, flexibility, and reusability of a SimplifiedHTMLParser program. The Decorator Pattern enables dynamic addition of functionalities to objects without altering their structure. This encourages code reusability and adheres to the Single Responsibility Principle. The Composite Pattern treats individual objects and compositions uniformly, simplifying client code interactions and promoting flexibility by allowing easy addition of new component types. Additionally, the Factory Method Pattern facilitates the creation of FileSaver objects without specifying concrete classes, supporting extensibility for various file formats.

While these patterns introduce complexity by requiring an understanding of class relationships, they ultimately yield improved modularity and maintainability. The project's core functionality involves reading HTML, JS, and CSS content from files, parsing HTML to create a CompositeNode object, and applying decorator objects like CSSDecoratorNode, ClassAttributeDecorator, and HeadElementDecorator to modify the DOM Document. The CompositeNode class represents HTML nodes capable of processing JS and CSS content, with subclasses like DivElement and ParagraphElement for specific elements. DecoratorNode abstract class extends HTMLNode, implementing the Decorator Pattern, and its subclasses enhance the head element, add class attributes, and apply CSS styles. The Factory Method Pattern is employed through FileSaverFactory, promoting loose coupling and facilitating future extensions for different file formats. In essence, this project showcases the power of design patterns in creating a flexible and maintainable HTML parsing and modification tool.

## Overview of the code
The SimplifiedHTMLParser program performs the following tasks:
1. Read HTML, JS, and CSS content from three different files.
2. Parse the HTML content using the parseHTMLContent method, which returns a DOM Document.
3. Create a CompositeNode object called rootNode using the parsed document.
4. Instantiate three decorator objects: CSSDecoratorNode, ClassAttributeDecorator, and
HeadElementDecorator, each wrapping the previous one.
5. Process the JS content using the processJS method of the headElementDecorator.
6. Process the CSS content using the processCSS method of the headElementDecorator.
7. Add a class attribute to paragraph elements using the addClassAttributeToElements method of the
classAttributeDecorator.
8. Update the title and head element using the updateTitleAndHead method of the
headElementDecorator.
9. Add a style tag to the head element using the addStyleTagToHead method.
10. Serialize the rootNode and save it to an output.html file using the saveToFile method.
Here's a brief overview of the main components:
* CompositeNode: A class representing an HTML node with the ability to process JS and CSS content. It is
a subclass of the abstract HTMLNode class. The DivElement and ParagraphElement classes extend
CompositeNode to represent div and p elements, respectively.
* DecoratorNode: An abstract class extending HTMLNode and implementing the Decorator Design
Pattern. It has three concrete subclasses: HeadElementDecorator, ClassAttributeDecorator, and
CSSDecoratorNode.
* HeadElementDecorator: Updates the title of the head element or creates one if it doesn't exist.
* ClassAttributeDecorator: Adds a class attribute to specified elements in the document.
* CSSDecoratorNode: Parses and applies CSS styles to the elements in the document.
The above-mentioned points are the basic overview of the working code.
