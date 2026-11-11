package visitor;

public class LinkElement implements Element {

	private String url;
	
	public LinkElement(String url) {
		this.url = url;
	}
	
	public String url() {
		return url;
	}
	
	@Override
	public void accept(Visitor vistor) {
		vistor.visit(this);
	}
}
