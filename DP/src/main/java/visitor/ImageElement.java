package visitor;

public class ImageElement implements Element {
	
	private String imagePath;
	
	public ImageElement(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public String imagePath() {
		return imagePath;
	}

	@Override
	public void accept(Visitor vistor) {
		vistor.visit(this);
	}
}
