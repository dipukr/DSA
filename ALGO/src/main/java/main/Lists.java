package main;

public class Lists {

	public class Node {
		public int data;
		public Node next;
		public Node(int data) {
			this.data = data;
		}
	}
	
	public void draw(Node head) {
		for (Node iter = head; iter != null; iter = iter.next)
			System.out.printf("%d->", iter.data);
		System.out.println("null");
	}
	
	public int size(Node head) {
		int count = 0;
		for (Node iter = head; iter != null; iter = iter.next)
			count += 1;
		return count;
	}
	
	public int length(Node head) {
		if (head == null) return 0;
		return length(head.next) + 1;
	}
	
	public boolean empty(Node head) {
		return head == null ? true : false;
	}
	
	public void addLast(Node head, int data) {
		Node curr = head;
		while (curr.next != null)
			curr = curr.next;
		curr.next = new Node(data);
	}
	
	public void insertAfter(Node after, int data) {
		Node node = new Node(data);
		node.next = after.next;
		after.next = node;
	}
	
	public void deleteTail(Node head) {
		Node curr = head;
		while (curr.next.next != null)
			curr = curr.next;
		curr.next = null;
	}
	
	public void deleteNode(Node head, Node node) {
		Node curr = head;
		while (curr.next != null && curr.next != node)
			curr = curr.next;
		if (curr.next == node)
			curr.next = node.next;
	}
	
	public void deleteByValue(Node head, int val) {
		Node curr = head;
		while (curr.next != null && curr.next.data != val)
			curr = curr.next;
		if (curr.next != null)
			curr.next = curr.next.next;
	}
	
	public boolean searchElement(Node head, int key) {
		Node curr = head;
		while (curr != null) {
			if (curr.data == key) return true;
			curr = curr.next;
		}
		return false;
	}
	
	public Node mergeSortedLists(Node first, Node second) {
		Node sentinel = new Node(0);
		Node curr = sentinel;
		while (first != null && second != null) {
			if (first.data < second.data) {
				curr.next = first;
				first = first.next;
			} else {
				curr.next = second;
				second = second.next;
			}
			curr = curr.next;
		}
		while (first != null) {
			curr.next = first;
			first = first.next;
			curr = curr.next;
		}
		while (second != null) {
			curr.next = second;
			second = second.next;
			curr = curr.next;
		}
		return sentinel.next;
	}
	
	public Node reverseList(Node head) {
		Node prev = null, curr = head;
		while (curr != null) {
			Node next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		return prev;
	}
	
	public Node reverseK(Node head, int k) {
		Node prev = null, curr = head;
		while (curr != null && k-- != 0) {
			Node next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		head.next = curr;
		return prev;
	}
	
	public boolean hasCycle(Node head) {
		Node slow = head;
		Node fast = head;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) return true;
		}
		return false;
	}
	
	public Node detectCycle(Node head) {
		Node slow = head;
		Node fast = head;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) return slow;
		}
		return null;
	}
	
	public Node detectFirstCycleNode(Node head) {
		Node meet = detectCycle(head);
		Node start = head;
		while (start != meet) {
			start = start.next;
			meet = meet.next;
		}
		return start;
	}
	
	public void makeCycle(Node head, int pos) {
		int count = 1;
		Node start = null, iter = head;
		while (iter.next != null) {
			if (count == pos) start = iter;
			iter = iter.next;
			count++;
		}
		iter.next = start;
	}
	
	public void removeCycle(Node head) {
		if (!hasCycle(head)) return;
		Node slow = head;
		Node fast = head;
		do {
			slow = slow.next;
			fast = fast.next.next;
		} while (slow != fast);
		Node meet = fast;
		while (head.next != meet.next) {
			head = head.next;
			meet = meet.next;
		}
		head.next = null;
	}
	
	public void intersect(Node first, Node second, int pos) {
		Node fst = first;
		Node snd = second;
		while (--pos != 0) fst = fst.next;
		while (snd.next != null) snd = snd.next;
		snd.next = fst;
	}
	
	public Node intersectionPoint(Node first, Node second) {
		int firstSize = size(first);
		int secondSize = size(second);
		int diffSize = Math.abs(firstSize - secondSize);
		Node fst = first;
		Node snd = second;
		if (firstSize > secondSize)
			while (diffSize-- != 0) fst = fst.next;
		else while (diffSize-- != 0) snd = snd.next;
		while (fst != null && snd != null) {
			if (fst == snd) return fst;
			fst = fst.next;
			snd = snd.next;
		}
		return null;
	}
	
	public Node cloneList(Node head) {
		Node cloneHead = new Node(head.data);
		Node clone = cloneHead;
		Node iter = head.next;
		while (iter != null) {
			clone.next = new Node(iter.data);
			clone = clone.next;
			iter = iter.next;
		}
		return cloneHead;
	}
	
	@Leet("143")
	public Node reorderList(Node head) {
		Node slow = head, fast = head.next;
		while (slow != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		Node second = slow.next;
		slow.next = null;
		second = reverseList(second);
		Node fst = head, snd = second;
		while (fst != null && snd != null) {
			Node fstNext = fst.next;
			Node sndNext = snd.next;
			fst.next = snd;
			snd.next = fstNext;
			fst = fstNext;
			snd = sndNext;
		}
		return head;
	}
	
	@Leet("141")
	public boolean circular(Node head) {
		Node curr = head.next;
		while (curr != null && curr != head)
			curr = curr.next;
		return curr == head;
	}
}











