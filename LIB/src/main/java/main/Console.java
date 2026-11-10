package main;

public class Console {
	public static void draw(String format, Object ... args) {
		System.out.println(String.format(format, args));
	}
	public static void draw(Object ... args) {
		for (Object arg: args)
			System.out.print(arg.toString());
		System.out.println();
	}
	public static void write(String format, Object ... args) {
		System.out.printf(format, args);
	}
	public static void writeln(String format, Object ... args) {
		draw(format, args);
	}
}
