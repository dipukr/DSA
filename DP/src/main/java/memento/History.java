package memento;

import java.util.LinkedList;
import java.util.List;

public class History {
	
	private List<EditorState> states = new LinkedList<>();
	
	public void push(EditorState state) {
		states.add(state);
	}
	
	public EditorState pop() {
		return states.removeLast();
	}
}
