package composite;

import main.Console;

public class File implements Node {

	private String name;
	
	public File(String name) {
		this.name = name;
	}
	
	@Override
	public void draw(String indent) {
		Console.draw("%sFile: %s", indent, name);
	}

}
