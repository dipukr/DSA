package visitor;

import main.Console;

public class ExporterPDF implements Visitor {

	@Override
	public void visit(TextElement element) {
		Console.draw("Exporting text: %s", element.text());
	}

	@Override
	public void visit(ImageElement element) {
		Console.draw("Exporting imag: %s", element.imagePath());
	}

	@Override
	public void visit(LinkElement element) {
		Console.draw("Exporting link: %s", element.url());
	}
}
