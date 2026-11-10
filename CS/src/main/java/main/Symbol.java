package main;

public abstract class Symbol {

	private int code;
	private String name;

	public Symbol(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int code() {return code;}
	public String name() {return name;}
	public String toString() {return name;}
	public int hashCode() {return code;}

	public abstract boolean isTerminal();
	public abstract boolean isNonTerminal();
}