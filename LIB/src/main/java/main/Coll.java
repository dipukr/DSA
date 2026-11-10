package main;

import java.util.Scanner;

public class Coll {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		Answer<Integer> answer = Ok.of(10067);
		switch (answer) {
		case Ok<Integer> ok -> System.out.println("User: " + ok.value());
		case Err<Integer> err -> System.err.println("Error: " + err.error());
		}
	}
}