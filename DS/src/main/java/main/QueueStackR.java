package main;

public class QueueStackR {
	private Stack stack = new Stack();
	
	public void enqueue(int elem) {
		stack.push(elem);
	}
	
	public int dequeue() {
		if (empty()) Error.fatal("Queue underflow");
		int top = stack.top();
		if (stack.empty()) return top;
		int elem = dequeue();
		stack.push(top);
		return elem;
	}
	
	public boolean empty() {
		return stack.empty();
	}
	
	public int size() {
		return stack.size();
	}
}
