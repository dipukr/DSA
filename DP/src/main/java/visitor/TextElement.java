package visitor;

public class TextElement implements Element {

	private String text;
	
	public TextElement(String text) {
		this.text = text;
	}
	
	public String text() {
		return text;
	}
	
	@Override
	public void accept(Visitor vistor) {
		vistor.visit(this);
	}
}
