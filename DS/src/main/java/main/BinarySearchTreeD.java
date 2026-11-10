package main;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.IOException;

public class BinarySearchTreeD {
	public class Node {
		public int key;
		public long left;
		public long right;
		public Node(int key) {this(key, -1, -1);}
		public Node(int key, long left, long right) {
			this.key = key;
			this.left = left;
			this.right = right;
		}
	}
	
	private RandomAccessFile file;
	
	public BinarySearchTreeD(String fileName) {
		try {
			this.file = new RandomAccessFile(fileName, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void write(Node node, long pos) {
		try {
			file.seek(pos);
			file.writeInt(node.key);
			file.writeLong(node.left);
			file.writeLong(node.right);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Node read(long pos) {
		try {
			file.seek(pos);
			int data = file.readInt();
			long left = file.readLong();
			long right = file.readLong();
			return new Node(data, left, right);
		} catch (IOException e) {
			return null;
		}
	}
	
	public long length() {
		try {
			return file.length();
		} catch (IOException e) {
			return -1;
		}
	}
	
	public boolean search(int key) {
		if (length() == 0) return false;
		long currPos = 0;
		while (currPos != -1) {
			Node node = read(currPos);
			if (key == node.key) return true;
			if (key < node.key) currPos = node.left;
			if (key > node.key) currPos = node.right;
		}
		return false;
	}
	
	public void insert(int key) {
		if (search(key)) return;
		if (length() == 0) {
			write(new Node(key), 0);
			return;
		}
		long currPos = 0;
		while (true) {
			Node curr = read(currPos);
			if (key < curr.key) {
				if (curr.left == -1) {
					long newPos = length();
					curr.left = newPos;
					write(curr, currPos);
					write(new Node(key), newPos);
					return;
				}
				currPos = curr.left;
			}
			if (key > curr.key) {
				if (curr.right == -1) {
					long newPos = length();
					curr.right = newPos;
					write(curr, currPos);
					write(new Node(key), newPos);
					return;
				}
				currPos = curr.right;
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		var bst = new BinarySearchTreeD("data");
		bst.insert(40);
		bst.insert(20);
		bst.insert(10);
		bst.insert(60);
		bst.insert(50);
		bst.insert(80);
		bst.insert(70);
		System.out.println(bst.search(50));
	}
}










