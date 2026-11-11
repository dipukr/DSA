package composite;

import java.util.ArrayList;
import java.util.List;

import main.Console;

public class ElementNode implements Node {

	private String tagName;
	private List<Node> childs = new ArrayList<>();
	
	public ElementNode(String tagName) {
		this.tagName = tagName;
	}
	
	public void addChild(Node child) {
		childs.add(child);
	}
	
	public String tagName() {
		return tagName;
	}
	
	@Override
	public void draw(String indent) {
		Console.draw("%s<%s>", indent, tagName);
		childs.forEach(child -> child.draw(indent + "    "));
		Console.draw("%s</%s>", indent, tagName);
	}
}
