package main;

import java.util.List;

public class Draws {
	private static final String HORIZONTAL = "─";
	private static final String VERTICAL = "│";
	private static final String TOP_LEFT = "┌";
	private static final String TOP_RIGHT = "┐";
	private static final String BOTTOM_LEFT = "└";
	private static final String BOTTOM_RIGHT = "┘";
	private static final String TOP_TEE = "┬";
	private static final String BOTTOM_TEE = "┴";
	private static final String LEFT_TEE = "├";
	private static final String RIGHT_TEE = "┤";
	private static final String CROSS = "┼";
	private static final int TILE_WIDTH = 5;

	public static void animate(List<int[][]> states) {
		if (states == null || states.isEmpty())
			return;
		System.out.print("\033[H\033[2J");
		System.out.flush();
		for (int i = 0; i < states.size(); i++) {
			System.out.print("\033[H");
			System.out.flush();
			System.out.println("Step " + (i + 1) + " / " + states.size() + "\n");
			draw(states.get(i));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	public static void draw(int[][] board) {
		int n = board.length;
		drawBorder(n, TOP_LEFT, TOP_TEE, TOP_RIGHT);
		for (int row = 0; row < n; row++) {
			for (int line = 0; line < 3; line++) {
				System.out.print(VERTICAL);
				for (int col = 0; col < n; col++) {
					String tileContent = getTileLine(board[row][col], line);
					System.out.print(center(tileContent, TILE_WIDTH));
					System.out.print(VERTICAL);
				}
				System.out.println();
			}
			if (row < n - 1)
				drawBorder(n, LEFT_TEE, CROSS, RIGHT_TEE);
			else
				drawBorder(n, BOTTOM_LEFT, BOTTOM_TEE, BOTTOM_RIGHT);
		}
	}

	public static void drawm(int[][] m) {
		if (m == null || m.length == 0) {
			System.out.println("[]");
			return;
		}
		int rows = m.length;
		int cols = 0;
		for (int r = 0; r < rows; r++)
			cols = Math.max(cols, m[r].length);
		int[] colWidth = new int[cols];
		for (int c = 0; c < cols; c++)
			for (int r = 0; r < rows; r++)
				if (c < m[r].length)
					colWidth[c] = Math.max(colWidth[c], String.valueOf(m[r][c]).length());
		String leftTop = "\u23A1"; // ⎡
		String leftMid = "\u23A2"; // ⎢
		String leftBot = "\u23A3"; // ⎣
		String rightTop = "\u23A4"; // ⎤
		String rightMid = "\u23A5"; // ⎥
		String rightBot = "\u23A6"; // ⎦
		for (int r = 0; r < rows; r++) {
			String L = (r == 0) ? leftTop : (r == rows - 1) ? leftBot : leftMid;
			String R = (r == 0) ? rightTop : (r == rows - 1) ? rightBot : rightMid;
			var sb = new StringBuilder();
			sb.append(L);
			for (int c = 0; c < cols; c++) {
				String cell = (c < m[r].length) ? String.valueOf(m[r][c]) : "";
				sb.append(String.format("%" + colWidth[c] + "s", cell));
				if (c < cols - 1)
					sb.append(" ");
			}
			sb.append(R);
			System.out.println(sb.toString());
		}
	}

	public static void drawBorder(int n, String left, String middle, String right) {
		System.out.print(left);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < TILE_WIDTH; j++)
				System.out.print(HORIZONTAL);
			if (i < n - 1)
				System.out.print(middle);
		}
		System.out.println(right);
	}

	public static String getTileLine(int value, int line) {
		String str = String.format("%2d", value);
		return switch (line) {
		case 0, 2 -> "   ";
		case 1 -> " " + str + " ";
		default -> "   ";
		};
	}

	public static String center(String text, int width) {
		int padding = (width - text.length()) / 2;
		return " ".repeat(Math.max(0, padding)) + text + " ".repeat(Math.max(0, width - text.length() - padding));
	}
	
	public static void draw(byte[][] maze) {
		int rows = maze.length;
		int cols = maze[0].length;
		int max = 0;
		for (byte[] r: maze)
			for (byte v: r)
				max = Math.max(max, v);
		int cellW = Math.max(1, String.valueOf(max).length());
		String horiz = "+" + "-".repeat(cellW + 2);
		String rowSep = horiz.repeat(cols) + "+";
		System.out.println(rowSep);
		for (int i = 0; i < rows; i++) {
			var line = new StringBuilder();
			for (int j = 0; j < cols; j++) {
				String s = (maze[i][j] == 0) ? "" : Byte.toString(maze[i][j]);
				int pad = cellW - s.length();
				String pads = pad <= 0 ? s : " ".repeat(pad) + s;
				line.append("| ").append(pads).append(" ");
			}
			line.append("|");
			System.out.println(line);
			System.out.println(rowSep);
		}
	}
	
	public static void drawMaze(int[][] maze) {
		int rows = maze.length;
		int cols = maze[0].length;
		int max = 0;
		for (int[] r: maze)
			for (int v: r)
				max = Math.max(max, v);
		int cellW = Math.max(1, String.valueOf(max).length());
		String horiz = "+" + "-".repeat(cellW + 2);
		String rowSep = horiz.repeat(cols) + "+";
		System.out.println(rowSep);
		for (int i = 0; i < rows; i++) {
			var line = new StringBuilder();
			for (int j = 0; j < cols; j++) {
				String s = (maze[i][j] == 0) ? "" : Integer.toString(maze[i][j]);
				int pad = cellW - s.length();
				String pads = pad <= 0 ? s : " ".repeat(pad) + s;
				line.append("| ").append(pads).append(" ");
			}
			line.append("|");
			System.out.println(line);
			System.out.println(rowSep);
		}
	}
}