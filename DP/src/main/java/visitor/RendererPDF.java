package visitor;

import main.Console;

public class RendererPDF implements Visitor {

	@Override
	public void visit(TextElement element) {
		Console.draw("Rendering text: %s", element.text());
	}

	@Override
	public void visit(ImageElement element) {
		Console.draw("Rendering imag: %s", element.imagePath());
	}

	@Override
	public void visit(LinkElement element) {
		Console.draw("Rendering link: %s", element.url());
	}
}
