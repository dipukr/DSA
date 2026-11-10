package maven;

public abstract class Expr {
	public abstract void accept(Visitor visitor);
	
	public static class Add extends Expr {
		public Expr left;
		public Expr right;
		
		public Add(Expr left, Expr right) {
			this.left = left;
			this.right = right;
		}

		@Override
		public void accept(Visitor visitor) {
			visitor.visit(this);
		}
	}
	
	public static class Sub extends Expr {
		public Expr left;
		public Expr right;
		
		public Sub(Expr left, Expr right) {
			this.left = left;
			this.right = right;
		}

		@Override
		public void accept(Visitor visitor) {
			visitor.visit(this);
		}
	}
	
	public static class Num extends Expr {
		public int num;
		
		public Num(int num) {
			this.num = num;
		}

		@Override
		public void accept(Visitor visitor) {
			visitor.visit(this);
		}
	}
	
	public interface Visitor {
		void visit(Add expr);
		void visit(Sub expr);
		void visit(Num expr);
	}
}
