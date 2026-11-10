package main;

public class StackArray {

	private Object[] data;
	private int sp;

	public StackArray(int size) {
		this.data = new Object[size];
		this.sp = -1;
	}

	public void push(Object elem) {
		if (full()) Error.fatal("Stack overflow");
		data[++sp] = elem;
	}

	public Object pop() {
		if (empty()) Error.fatal("Stack underflow");
		return data[sp--];
	}

	public Object top() {
		if (empty()) Error.fatal("Stack underflow");
		return data[sp];
	}

	public int size() {
		return sp + 1;
	}
	
	public boolean full() {
		return sp == data.length - 1;
	}
	
	public boolean empty() {
		return size() == 0;
	}
}