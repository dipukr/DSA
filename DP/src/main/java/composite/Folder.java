package composite;

import java.util.ArrayList;
import java.util.List;

import main.Console;

public class Folder implements Node {
	
	private String name;
	private List<Node> childs = new ArrayList<>();
	
	public Folder(String name) {
		this.name = name;
	}
	
	public void addFile(Node child) {
		childs.add(child);
	}
	
	public String name() {
		return name;
	}
	
	@Override
	public void draw(String indent) {
		Console.draw("%sFolder: %s", indent, name);
		childs.forEach(child -> child.draw(indent + "    "));
	}
}
