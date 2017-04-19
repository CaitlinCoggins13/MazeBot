import lejos.robotics.subsumption.Behavior;

public class MoveForward implements Behavior {

	@Override
	public boolean takeControl() {
		// Stop moving forward if it's going to move out of bound.
		return Robot.canMoveForward();
	}

	@Override
	public void action() {
		Robot.travel(-8);
		updatePath();
	}

	private void updatePath() {
		int Orientation = Robot.getOrientation();
		Cell currCell = Robot.getCurrCell();
		int row = currCell.getRow();
		int col = currCell.getCol();
		Cell nextCell = null;
		if (Orientation == 0) {
			nextCell = new Cell(row, col + 1);
			Robot.setCurrCell(nextCell);
		} else if (Orientation == 1) {
			nextCell = new Cell(row - 1, col);
			Robot.setCurrCell(nextCell);
		} else if (Orientation == 2) {
			nextCell = new Cell(row, col - 1);
			Robot.setCurrCell(nextCell);
		} else if (Orientation == 3) {
			nextCell = new Cell(row + 1, col);
			Robot.setCurrCell(nextCell);
		}
		Path.pushCell(nextCell);
	}

	@Override
	public void suppress() {
		Robot.stop();
	}

}
