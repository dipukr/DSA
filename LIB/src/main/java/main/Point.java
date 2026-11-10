package main;

public class Point {
	
	public int x;
	public int y;
	
	public static Point of(int x, int y) {
		Point point = new Point();
		point.x = x;
		point.y = y;
		return point;
	}
	
	public double distance(Point other) {
		double dx = Math.pow(other.x - this.x, 2);
		double dy = Math.pow(other.y - this.y, 2);
		return Math.sqrt(dx + dy);
	}
	
	@Override
	public String toString() {
		return String.format("(%d,%d)", x, y);
	}
}
