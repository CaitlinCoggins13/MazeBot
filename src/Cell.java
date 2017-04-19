
public class Cell {

	private int row;
	private int col;
	private boolean visited;
	private boolean hasWall;

	public Cell(int r, int c) {
		row = r;
		col = c;
		visited = false;
		hasWall = false;
	}

	public void setWall() {
		hasWall = true;
	}

	public void setVisited() {
		visited = true;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public boolean hasWall() {
		return hasWall();
	}

	public boolean visited() {
		return visited;
	}
}
