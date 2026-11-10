package main;

public final class Err<T> implements Answer<T> {
	private final String error;

	public Err(String error) {
		this.error = error;
	}

	public boolean isOk() {return false;}
	public boolean isError() {return true;}

	public T value() {return null;}
	public String error() {return error;}

	@Override
	public String toString() {
		return "Error(" + error + ")";
	}
	
	public static <T> Err<T> of(String error) {
		return new Err<T>(error);
	}
}
