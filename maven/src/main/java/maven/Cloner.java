package maven;

public class Cloner {
	public static Expr clone(Expr expr) {
		return switch (expr) {
			case Expr.Add e ->  e;
			case Expr.Sub e ->  e;
			case Expr.Num n ->  n;
			default -> null;
		};
	}
}
