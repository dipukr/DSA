package main;

public class Terminal extends Symbol {
	
	public Terminal(int code, String name) {
		super(code, name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null) return false;
		return obj instanceof Terminal ts ?
			this.code() == ts.code() : false;
	}
	
	@Override
	public boolean isTerminal() {
		return true;
	}
	
	@Override
	public boolean isNonTerminal() {
		return false;
	}
}