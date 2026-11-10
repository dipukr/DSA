package main;

public class NonTerminal extends Symbol {
	
	public NonTerminal(int code, String name) {
		super(code, name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null) return false;
		if (obj instanceof Terminal ts)
			return this.code() == ts.code();
		return false;
	}
	
	@Override
	public boolean isTerminal() {
		return false;
	}
	
	@Override
	public boolean isNonTerminal() {
		return true;
	}
}