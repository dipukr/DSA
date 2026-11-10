package main;

public class StackQueues {

	private Queue one = new Queue();
	private Queue two = new Queue();

	public void push(int elem) {
		one.enqueue(elem);
	}

	public int pop() {
		if (empty()) Error.fatal("Stack underflow");
		while (one.size() != 1)
			two.enqueue(one.dequeue());
		int elem = one.dequeue();
		Queue tmp = one;
		one = two;
		two = tmp;
		return elem;
	}

	public int top() {
		if (empty()) Error.fatal("Stack is empty");
		while (one.size() != 1)
			two.enqueue(one.dequeue());
		int elem = one.front();
		two.enqueue(one.dequeue());
		Queue tmp = one;
		one = two;
		two = tmp;
		return elem;
	}

	public int size() {
		return one.size();
	}

	public boolean empty() {
		return size() == 0;
	}
}