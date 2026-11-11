package visitor;

public interface Visitor {
	void visit(TextElement element);
	void visit(ImageElement element);
	void visit(LinkElement element);
}
