package main;

public final class QueueArray {
	private int[] data;
	private int front;
	private int rear;

	public QueueArray(int size) {
		this.data = new int[size];
		this.front = 0;
		this.rear = -1;
	}

	public void enqueue(int elem) {
		if (full()) Error.error("Queue overflow.");
		data[++rear] = elem;
	}

	public int dequeue() {
		if (empty()) Error.error("Queue empty.");
		return data[front++];
	}
	
	public int front() {
		if (empty()) Error.error("Queue empty.");
		return data[front];
	}

	public int size() {
		return 0;
	}
	
	public boolean empty() {
		return front > rear;
	}
	
	public boolean full() {
		return rear == data.length;
	}
}
