package main;

public sealed interface Answer<T> permits Ok, Err {
	boolean isOk();
	boolean isError();
	T value();
	String error();
}
