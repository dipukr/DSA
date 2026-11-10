package main;

public class Error {
	public static void fatal(String messge) {
		System.out.println("ERROR: " + messge);
		System.exit(1);
	}
	
	public static void error(String messge) {
		System.out.println("ERROR: " + messge);
		System.exit(1);
	}
}
