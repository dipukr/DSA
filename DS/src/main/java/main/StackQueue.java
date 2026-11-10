package main;

public class StackQueue {

	private Queue one = new Queue();
	private Queue two = new Queue();

	public void push(int elem) {
		while (!one.empty())
			two.enqueue(one.dequeue());
		one.enqueue(elem);
		while (!two.empty())
			one.enqueue(two.dequeue());
	}

	public int pop() {
		if (empty()) Error.fatal("Stack underflow");
		return one.dequeue();
	}

	public int top() {
		if (empty()) Error.fatal("Stack underflow");
		return one.front();
	}

	public int size() {
		return one.size();
	}

	public boolean empty() {
		return size() == 0;
	}
}