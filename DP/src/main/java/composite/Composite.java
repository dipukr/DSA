package composite;

public class Composite {
	public void main(String[] args) throws Exception {
		ElementNode html = new ElementNode("html");
		ElementNode body = new ElementNode("body");
		ElementNode h1 = new ElementNode("h1");
		h1.addChild(new TextNode("Hello World"));
		ElementNode p = new ElementNode("p");
		p.addChild(new TextNode("This is a paragraph."));
		body.addChild(h1);
		body.addChild(p);
		html.addChild(body);
		html.draw("");
	}
}
