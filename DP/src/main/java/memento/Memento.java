package memento;

public class Memento {
	public void main(String[] args) throws Exception {
		Editor editor = new Editor();
		History history = new History();
		editor.setContent("Satyam");
		history.push(editor.createState());
		editor.setContent("Shivam");
		history.push(editor.createState());
		editor.setContent("Sundram");
		System.out.println(editor.content());
		editor.restore(history.pop());
		System.out.println(editor.content());
	}
}
