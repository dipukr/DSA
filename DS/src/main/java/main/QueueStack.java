package main;

public class QueueStack {
	private Stack in = new Stack();
	private Stack out = new Stack();

	public void enqueue(int elem) {
		in.push(elem);
	}

	public int dequeue() {
		if (empty()) Error.error("Queue underflow");
		shift();
		return out.pop();
	}
	
	public int peek() {
		if (empty()) Error.error("Queue is empty");
		shift();
		return out.top();
	}

	public void shift() {
		if (out.empty()) {
			while (!in.empty())
				out.push(in.pop());
		}
	}

	public boolean empty() {
		return in.empty() && out.empty();
	}
}