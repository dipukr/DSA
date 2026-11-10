package main;

import java.util.List;
import java.util.ArrayList;

public class Node {
	
	public Object data;
	public boolean marked;
	public List<Node> adjacents;

	public Node(Object data) {
		this.data = data;
		this.marked = false;
		this.adjacents = new ArrayList<>();
	}
}