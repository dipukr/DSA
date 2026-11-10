package main;

public class AnalyzerError extends Exception {
	private int pos;
	private String message;
	
	public AnalyzerError(int pos) {
		this.pos = pos;
	}

	public AnalyzerError(String message, int pos) {
		this.pos = pos;
		this.message = message;
	}
	
	public int pos() {return pos;}
	public String message() {return message;}
}