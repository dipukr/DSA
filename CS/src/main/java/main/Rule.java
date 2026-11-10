package main;

import java.util.Arrays;

public class Rule {
	private int ruleNo;
	private NonTerminal left;
	private Symbol[] right;
	
	public Rule(int ruleNo, NonTerminal left, Symbol[] right) {
		this.ruleNo = ruleNo;
		this.left = left;
		this.right = right;
	}

	public int ruleNo() {return ruleNo;}
	public NonTerminal left() {return left;}
	public Symbol[] right() {return right;}

	@Override
	public String toString() {
		return String.format("Rule{ruleNo=%s, left=%s, right=%s}",
			ruleNo, left, Arrays.toString(right));
	}
}