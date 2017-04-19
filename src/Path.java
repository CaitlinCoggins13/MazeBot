import java.util.Stack;

public class Path {
	private static Stack<Cell> botPath = new Stack<Cell>();

	public static void pushCell(Cell c) {
		botPath.push(c);
	}

	public static Cell popCell() {
		if (botPath.isEmpty()) {
			return null;
		}
		return botPath.pop();
	}

}
