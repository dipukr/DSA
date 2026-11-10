package main;

public class Token {
	private int start;
	private int end;
	private TokenType type;
	private String word;
	
	public Token(int start, int end, String word, TokenType type) {
		this.start = start;
		this.end = end;
		this.type = type;
		this.word = word;
	}

	public int getBegin() {return start;}
	public int getEnd() {return end;}
	public String getTokenWord() {return word;}
	public TokenType getTokenType() {return type;}
	
	@Override
	public String toString() {
		if (this.getTokenType().isAuxiliary())
			return type + "   [" + start + ";" + end + "] ";
		else return type + "  '" + word + "' [" + start + ";" + end + "] ";
	}
}