package main;

public class Strs {
	public String compressedString(String word) {
		var result = new StringBuilder();
		int i = 1, count = 1;
		char prevChar = word.charAt(0);
		while (true) {
			char currChar = word.charAt(i++);
			if (currChar == prevChar && count < 9) {
				count++;
			} else {
				result.append(count);
				result.append(prevChar);
				count = 1;
			}
			if (i == word.length()) {
				result.append(count);
				result.append(currChar);
				break;
			}
			prevChar = currChar;
		}
		return String.valueOf(result);
	}
	
	public static void main(String[] args) {
		var algo = new Strs();
		System.out.println(algo.compressedString("aaabbbcccc"));
	}
}
