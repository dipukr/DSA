package memento;

public class Editor {
	
	private String content;
	
	public EditorState createState() {
		return new EditorState(content);
	}
	
	public void restore(EditorState state) {
		this.content = state.content();
	}

	public String content() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
