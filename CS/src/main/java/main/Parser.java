package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

// The {@code Parser} class represents predictive parser. It accepts only LL(1)
// grammar. If the grammar is not LL(1) most likely you will get
// {@code StackOverflowError}. Productions in the grammar use the following format, for example:
// Goal -> A 
// A -> ( A ) | Two 
// Two -> a
// Symbol is inferred as nonterminal by first uppercase char. "->" designates
// definition, "|" designates alternation, and newlines designate termination.
// Use "EPSILON" to represent an empty string. Place spaces between things you
// don't want to read as one symbol: ( A ) != (A).

public class Parser {

	public static Terminal epsilon = new Terminal(0, "EPSILON"); // Terminal symbol of grammar which represents empty string
	public static Terminal endOfProgram = new Terminal(-1, "ENDOFPROGRAM"); // Terminal symbol which represents end of program
	
	public NonTerminal startSymbol;              // Start symbol of the grammar
	public List<Rule> rules;                     // List of rules in the grammar without alternations
	public Set<Symbol> alphabet;                 // Grammar's alphabet. Contains terminal and nonterminal symbols
	public Map<String, Symbol> nameToSymbol;     // Mapping from string representation of symbol to its object
	public Map<Symbol, Set<Terminal>> firstSet;  // Mapping from symbol to it's first set
	public Map<Symbol, Set<Terminal>> followSet; // Mapping from symbol to it's follow set
	public Map<SimpleEntry<NonTerminal, Terminal>, Symbol[]> parsingTable; // Representation of parsing table for LL(1) parser
	public Stack<Terminal> input; // Stack of terminals, which were constructed from input tokens
	public List<Rule> sequenceOfAppliedRules; // Sequence of applied rules during the derivations

	// Initializes a newly created {@code Parser} object
	
	public Parser() {
		rules = new ArrayList<Rule>();
		alphabet = new HashSet<Symbol>();
		nameToSymbol = new HashMap<String, Symbol>();
		alphabet.add(epsilon);
		firstSet = new HashMap<Symbol, Set<Terminal>>();
		followSet = new HashMap<Symbol, Set<Terminal>>();
		parsingTable = new HashMap<SimpleEntry<NonTerminal, Terminal>, Symbol[]>();
		sequenceOfAppliedRules = new ArrayList<Rule>();
	}

	// Parses the source, represented by the list of tokens, using the specified LL(1) grammar rules
	// grammarFile: file with grammar rules
	// list: list of tokens from the input
	// throws: FileNotFoundException: if file doesn't exist
	// throws: AnalyzerException: if the input contains syntax error
	
	public void parse(File grammarFile, List<Token> list) throws FileNotFoundException, AnalyzerError {
		parseRules(grammarFile);
		calculateFirst();
		calculateFollow();
		buildParsingTable();
		input = convertTokensToStack(list);
		performParsingAlgorithm();
	}

	// Returns the sequence of grammar rules, which were applied during the parsing
	// return: list of applied rules
	
	public List<Rule> getSequenceOfAppliedRules() {
		return sequenceOfAppliedRules;
	}

	// Implements LL(1) predictive parsing algorithm
	// throws: AnalyzerException: if syntax error was found
	
	public void performParsingAlgorithm() throws AnalyzerError {
		Stack<Symbol> stack = new Stack<Symbol>();
		stack.push(endOfProgram);
		stack.push(startSymbol);
		int parsedTokensCount = 0;
		do {

			Symbol stackTop = stack.peek();
			Terminal inputTop = input.peek();
			if (stackTop.isTerminal()) {
				if (stackTop.equals(inputTop)) {
					stack.pop();
					input.pop();
					parsedTokensCount++;
				} else {
					throw new AnalyzerError("Syntax error after token #" + parsedTokensCount, parsedTokensCount);
				}
			} else {
				SimpleEntry<NonTerminal, Terminal> tableKey = 
						new SimpleEntry<NonTerminal, Terminal>( (NonTerminal) stackTop, inputTop);
				if (parsingTable.containsKey(tableKey)) {
					stack.pop();
					Symbol[] tableEntry = parsingTable.get(tableKey);
					for (int j = tableEntry.length - 1; j > -1; j--) {
						if (!tableEntry[j].equals(epsilon))
							stack.push(tableEntry[j]);
					}
					sequenceOfAppliedRules.add(getRule((NonTerminal) stackTop, tableEntry));
				} else {
					throw new AnalyzerError("Syntax error after token #" + parsedTokensCount, parsedTokensCount);
				}
			}
		} while (!stack.isEmpty() && !input.isEmpty());

		if (!input.isEmpty())
			throw new AnalyzerError("Syntax error after token #" + parsedTokensCount, parsedTokensCount);
	}

	// Converts a list of tokens from lexer to stack of terminals for parser.
	// Fist token in the input will be at the top of the stack. 
	// inputTokens: list of input tokens
	// return: stack of terminal symbols

	public Stack<Terminal> convertTokensToStack(List<Token> inputTokens) {
		Stack<Terminal> input = new Stack<Terminal>();
		Collections.reverse(inputTokens);
		input.push(endOfProgram);
		for (Token token : inputTokens) {
			Terminal s = (Terminal) nameToSymbol.get(token.getTokenWord());
			if (s == null) {
				switch (token.getTokenType()) {
				case Identifier:
					s = (Terminal) nameToSymbol.get("id");
					break;
				case IntConstant:
					s = (Terminal) nameToSymbol.get("intConst");
					break;
				case DoubleConstant:
					s = (Terminal) nameToSymbol.get("doubleConst");
					break;
				default:
					throw new RuntimeException("Somethig is wrong!");
				}
			}
			input.push(s);
		}
		return input;
	}

	// Automatically builds LL(1) parsing table by using follow and first set
	
	public void buildParsingTable() {
		for (Rule r: rules) {
			Symbol[] rightSide = r.right();
			NonTerminal leftSide = r.left();
			Set<Terminal> firstSetForRightSide = first(rightSide);
			Set<Terminal> followSetForLeftSide = followSet.get(leftSide);

			for (Terminal s : firstSetForRightSide) {
				parsingTable.put(new SimpleEntry<NonTerminal, Terminal>(leftSide, s), rightSide);
			}

			if (firstSetForRightSide.contains(epsilon)) {
				for (Terminal s : followSetForLeftSide) {
					parsingTable.put(new SimpleEntry<NonTerminal, Terminal>(leftSide, s), rightSide);
				}
			}
		}
	}

	public void calculateFirst() {
		for (Symbol s : alphabet) {
			firstSet.put(s, new HashSet<Terminal>());
		}
		for (Symbol s : alphabet) {
			first(s);
		}
	}

	// Calculates first set for specified symbol. By using the next rules:
	// 1. If X is terminal, then FIRST(X) is {X}.
	// 2. If X -> EPSILON is production, then add EPSILON to FIRST(X).
	// 3. If X is nonterminal and X -> Y1 Y2 ... Yk is a production, 
	// then place <i>a</i> (terminal) in FIRST(X) if for some i <i>a</i> is in FIRST(Yi), and Y1, ... ,Yi-1 -> EPSILON. 
	// If EPSILON is in FIRST(Yj) for all j = 1, 2, ... , k, then add EPSILON to FIRST(X).
	// param: s: terminal or nonterminal symbol of grammar
	
	public void first(Symbol s) {
		Set<Terminal> first = firstSet.get(s);
		Set<Terminal> auxiliarySet;
		if (s.isTerminal()) {
			first.add((Terminal) s);
			return;
		}

		for (Rule r : getRulesWithLeftSide((NonTerminal) s)) {
			Symbol[] rightSide = r.right();
			first(rightSide[0]);
			auxiliarySet = new HashSet<Terminal>(firstSet.get(rightSide[0]));
			auxiliarySet.remove(epsilon);
			first.addAll(auxiliarySet);

			for (int i = 1; i < rightSide.length && firstSet.get(rightSide[i - 1]).contains(epsilon); i++) {
				first(rightSide[i]);
				auxiliarySet = new HashSet<Terminal>(firstSet.get(rightSide[i]));
				auxiliarySet.remove(epsilon);
				first.addAll(auxiliarySet);
			}

			boolean allContainEpsilon = true;
			for (Symbol rightS : rightSide) {
				if (!firstSet.get(rightS).contains(epsilon)) {
					allContainEpsilon = false;
					break;
				}
			}
			if (allContainEpsilon)
				first.add(epsilon);
		}
	}

	// Calculates first set for chain of symbols
	// chain: string of symbols
	// return: first set for the specified string
	
	public Set<Terminal> first(Symbol[] chain) {
		Set<Terminal> firstSetForChain = new HashSet<Terminal>();
		Set<Terminal> auxiliarySet;
		auxiliarySet = new HashSet<Terminal>(firstSet.get(chain[0]));
		auxiliarySet.remove(epsilon);
		firstSetForChain.addAll(auxiliarySet);

		for (int i = 1; i < chain.length && firstSet.get(chain[i - 1]).contains(epsilon); i++) {
			auxiliarySet = new HashSet<Terminal>(firstSet.get(chain[i]));
			auxiliarySet.remove(epsilon);
			firstSetForChain.addAll(auxiliarySet);
		}

		boolean allContainEpsilon = true;
		for (Symbol s : chain) {
			if (!firstSet.get(s).contains(epsilon)) {
				allContainEpsilon = false;
				break;
			}
		}
		if (allContainEpsilon)
			firstSetForChain.add(epsilon);

		return firstSetForChain;
	}

	public void calculateFollow() {
		for (Symbol s : alphabet) {
			if (s.isNonTerminal())
				followSet.put(s, new HashSet<Terminal>());
		}

		Map<SimpleEntry<Symbol, Symbol>, Boolean> callTable = new HashMap<SimpleEntry<Symbol, Symbol>, Boolean>();
		for (Symbol firstS : alphabet) {
			for (Symbol secondS : alphabet) {
				callTable.put(new SimpleEntry<Symbol, Symbol>(firstS, secondS), false);
			}
		}

		NonTerminal firstSymbol = rules.get(0).left();
		followSet.get(firstSymbol).add(endOfProgram);
		for (Symbol s : alphabet) {
			if (s.isNonTerminal()) {
				follow((NonTerminal) s, null, callTable);
			}
		}
	}

	// Calculates follow set for nonterminal symbols
	 
	public void follow(NonTerminal s, Symbol caller, Map<SimpleEntry<Symbol, Symbol>, Boolean> callTable) {
		Boolean called = callTable.get(new SimpleEntry<Symbol, Symbol>(caller, s));
		if (called != null) {
			if (called == true) return;
			else callTable.put(new SimpleEntry<Symbol, Symbol>(caller, s), true);
		}

		Set<Terminal> follow = followSet.get(s);
		Set<Terminal> auxiliarySet;

		List<SimpleEntry<NonTerminal, Symbol[]>> list = leftRightChain(s);
		for (SimpleEntry<NonTerminal, Symbol[]> pair: list) {
			Symbol[] rightChain = pair.getValue();
			NonTerminal leftSide = pair.getKey();
			if (rightChain.length != 0) {
				auxiliarySet = first(rightChain);
				auxiliarySet.remove(epsilon);
				follow.addAll(auxiliarySet);
				if (first(rightChain).contains(epsilon)) {
					follow(leftSide, s, callTable);
					follow.addAll(followSet.get(leftSide));
				}
			} else {
				follow(leftSide, s, callTable);
				follow.addAll(followSet.get(leftSide));
			}
		}
	}

	// Constructs grammar rules from file
	// grammarFile: file with grammar rules
	// throws: FileNotFoundException: if file with the specified pathname does not exist

	public void parseRules(File grammarFile) throws FileNotFoundException {
		nameToSymbol.put("EPSILON", epsilon);

		Scanner data = new Scanner(grammarFile);
		int code = 1;
		int ruleNumber = 0;
		while (data.hasNext()) {
			StringTokenizer t = new StringTokenizer(data.nextLine());
			String symbolName = t.nextToken();
			if (!nameToSymbol.containsKey(symbolName)) {
				Symbol s = new NonTerminal(code, symbolName);
				if (code == 1)
					startSymbol = (NonTerminal) s;
				nameToSymbol.put(symbolName, s);
				alphabet.add(s);
				code++;
			}
			t.nextToken(); // ->

			NonTerminal leftSide = (NonTerminal) nameToSymbol.get(symbolName);
			while (t.hasMoreTokens()) {
				List<Symbol> rightSide = new ArrayList<Symbol>();
				do {
					symbolName = t.nextToken();
					if (!symbolName.equals("|")) {
						if (!nameToSymbol.containsKey(symbolName)) {
							Symbol s;
							if (Character.isUpperCase(symbolName.charAt(0)))
								s = new NonTerminal(code++, symbolName);
							else
								s = new Terminal(code++, symbolName);
							nameToSymbol.put(symbolName, s);
							alphabet.add(s);
						}
						rightSide.add(nameToSymbol.get(symbolName));
					}
				} while (!symbolName.equals("|") && t.hasMoreTokens());
				rules.add(new Rule(ruleNumber++, leftSide, rightSide.toArray(new Symbol[] {})));
			}
		}
	}

	// Returns rules with specified left side
	// nonTerminalSymbol: symbol in the left side of the production
	// return: set of rules which contain the specified symbol in the left side

	public Set<Rule> getRulesWithLeftSide(NonTerminal nonTerminalSymbol) {
		Set<Rule> set = new HashSet<Rule>();
		for (Rule r: rules) {
			if (r.left().equals(nonTerminalSymbol))
				set.add(r);
		}
		return set;
	}

	// Returns list of pairs. First element of the pair is the left side of the
	// rule if this rule contains specified symbol {@code s} in the right side.
	// The second element contains symbols after {@code s} in the right side of the rule.
	
	public List<SimpleEntry<NonTerminal, Symbol[]>> leftRightChain(Symbol s) {
		List<SimpleEntry<NonTerminal, Symbol[]>> list = new ArrayList<SimpleEntry<NonTerminal, Symbol[]>>();
		for (Rule r: rules) {
			Symbol[] rightChain = r.right();
			int index = Arrays.asList(rightChain).indexOf(s);
			if (index != -1) {
				rightChain = Arrays.copyOfRange(rightChain, index + 1, rightChain.length);
				list.add(new SimpleEntry<NonTerminal, Symbol[]>(r.left(), rightChain));
			}
		}
		return list;
	}

	// Returns the rule with specified left and right side
	// leftSide: symbol in the left side of the production
	// rightSide: symbols in the right side
	// return: rule with specified left and right side or {@code null} if such rule doesn't exist in grammar
	
	public Rule getRule(NonTerminal leftSide, Symbol[] rightSide) {
		Set<Rule> setOfRules = getRulesWithLeftSide(leftSide);
		for (Rule r : setOfRules) {
			if (rightSide.length != r.right().length)
				continue;
			for (int i = 0; i < rightSide.length; i++) {
				if (r.right()[i] != rightSide[i])
					break;
				else {
					if (i == rightSide.length - 1) {
						return r;
					}
				}
			}
		}
		return null;
	}
}
