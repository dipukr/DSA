package composite;

import main.Console;

public class TextNode implements Node {
	private String text;
	
	public TextNode(String text) {
		this.text = text;
	}
	
	@Override
	public void draw(String indent) {
		Console.draw("%s%s", indent, text);
	}
}
