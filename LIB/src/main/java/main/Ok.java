package main;

public final class Ok<T> implements Answer<T> {
	private final T value;

	public Ok(T value) {
		this.value = value;
	}

	public boolean isOk() {return true;}
	public boolean isError() {return false;}

	public T value() {return value;}
	public String error() {return null;}

	@Override
	public String toString() {
		return "Ok(" + value + ")";
	}
	
	public static <T> Ok<T> of(T val) {
		return new Ok<T>(val);
	}
}
