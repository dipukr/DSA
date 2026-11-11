package main;

public class CircularQueue {
	
	private Object[] data;
	private int capacity;
	private int head;
	private int tail;

	public CircularQueue(int capacity) {
		this.data = new Object[capacity];
		this.capacity = capacity;
		this.head = 0;
		this.tail = -1;
	}

	public void enqueue(Object elem) {
		if (full()) Error.fatal("Queue overflow");
		tail = (tail + 1) % capacity;
		data[tail] = elem;
	}

	public Object dequeue() {
		if (empty()) Error.fatal("Queue underflow");
		Object elem = data[head];
		head = (head + 1) % capacity;
		return elem;
	}

	public Object front() {
		if (empty()) Error.fatal("Queue underflow");
		return data[head];
	}
	
	public boolean empty() {
		return tail == -1;
	}

	public boolean full() {
		return (head == (tail + 1) % capacity);
	}

	public int size() {
		return tail >= head ? tail - head + 1 : capacity - head + tail + 1;
	}
	
	public static void main(String[] args) {
		var queue = new CircularQueue(90);
	}
}
