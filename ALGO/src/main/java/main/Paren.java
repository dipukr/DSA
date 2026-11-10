package main;

import java.util.Stack;

public class Paren {
	public boolean left(char c)  {return c == '(' || c == '{' || c == '[';}
	public boolean right(char c) {return c == ')' || c == '}' || c == ']';}

	public boolean match(char a, char b) {
		switch (a) {
		case ')': return b == '(';
		case '}': return b == '{';
		case ']': return b == '[';
		}
		return false;
	}

	public boolean balanced(String expression) {
		var stack = new Stack<Character>();
		for (char ch: expression.toCharArray()) {
			if (left(ch)) stack.push(ch);
			else if (right(ch)) {
				if (stack.isEmpty() || !match(ch, stack.peek()))
					return false;
				else stack.pop();
			} else Error.fatal("illegal expression");
		}
		return stack.isEmpty();
	}

	public void evaluatePrefix(String expression) {
		var stack = new Stack<Integer>();
		for (char ch: expression.toCharArray()) {
			if (Character.isDigit(ch))
				stack.push(Integer.valueOf(ch));
			else if (ch == '+') {
				int a = stack.pop();
				int b = stack.pop();
				stack.push(a + b);
			} else if (ch == '-') {
				int a = stack.pop();
				int b = stack.pop();
				stack.push(a - b);
			} else if (ch == '*') {
				int a = stack.pop();
				int b = stack.pop();
				stack.push(a * b);
			} else if (ch == '/') {
				int a = stack.pop();
				int b = stack.pop();
				stack.push(a * b);
			}
		}
	}
}