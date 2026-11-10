package main;

import java.util.Arrays;

public class BinaryHeap {
	
	private double[] data;
	private int count;

	public BinaryHeap() {
		this.data = new double[8];
		this.count = 0;
	}
	
	public void insert(double elem) {
		if (count == data.length)
			data = Arrays.copyOf(data, data.length * 2);
		data[count++] = elem;
		moveUP();
	}

	public double deleteMin() {
		double val = findMin();
		data[0] = data[count - 1];
		count--;
		moveDown();
		return val;
	}

	public double findMin() {
		if (empty())
			Error.fatal("Heap overflow.");
		return data[0];
	}

	public void moveUP() {
		int i = count - 1;
		while (hasParent(i) && data[parent(i)] > data[i]) {
			swap(i, parent(i));
			i = parent(i);
		}
	}

	public void moveDown() {
		int i = 0;
		while (hasLeft(i)) {
			int smaller = left(i);
			if (hasRight(i) && data[left(i)] > data[right(i)])
				smaller = right(i);
			if (data[i] > data[smaller])
				swap(i, smaller);
			else break;
			i = smaller;
		}
	}
	
	public void swap(int i, int j) {
		double temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}

	public int size() {return count;}
	public boolean empty() {return size() == 0;}

	public int left(int i) {return i * 2 + 1;}
	public int right(int i) {return i * 2 + 2;}
	public int parent(int i) {return (i - 1) / 2;}

	public boolean hasParent(int i) {return i > 0;}
	public boolean hasLeft(int i) {return left(i) < size();}
	public boolean hasRight(int i) {return right(i) < size();}
}
