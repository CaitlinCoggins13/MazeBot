import java.util.Stack;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.ArcMoveController;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Robot {

	static ArcMoveController robot = new DifferentialPilot(5.6f, 11.0f, Motor.A, Motor.C, true);
	// TODO: do we know the starting location? If not, how do we find out the
	// row and col of starting cell
	static Cell currCell;
	static int orientation = 0;
	static UltrasonicSensor uSensor = new UltrasonicSensor(SensorPort.S1);
	static LightSensor lSensor = new LightSensor(SensorPort.S3);

	private static Stack<Cell> botPath = new Stack<Cell>();

	public static void pushCellToPath(Cell c) {
		botPath.push(c);
	}

	public static Cell popCellFromPath() {
		if (botPath.isEmpty()) {
			return null;
		}
		return botPath.pop();
	}

	public static void turnLeft() {
		orientation = (orientation + 1) % 4;
		rotate(130);
	}

	public static void turnRight() {
		orientation = (orientation - 1 + 4) % 4;
		rotate(-130);
	}

	public static void rotate(int i) {
		((DifferentialPilot) robot).rotate(i);
	}

	public static void travel(int i) {
		robot.travel(i);
	}

	public static void forward() {
		travel(-8);
	}

	public static void backward() {
		travel(8);
	}

	public static boolean canMoveForward() {
		int row = currCell.getRow();
		int col = currCell.getCol();
		if (orientation == 0) {
			if (col + 1 >= 8)
				return false;

		} else if (orientation == 1) {
			if (row - 1 <= 0)
				return false;

		} else if (orientation == 2) {
			if (col - 1 <= 0)
				return false;
		} else if (orientation == 3) {
			if (row + 1 >= 5)
				return false;

		}
		return true;
	}

	public static int getOrientation() {
		return orientation;
	}

	public static Cell getCurrCell() {
		return currCell;
	}

	public static void setCurrCell(Cell c) {
		currCell = c;

	}

	/**
	 * light sensor method to detect goal cell
	 * 
	 * @return
	 */
	public static int getLightValue() {
		return lSensor.getLightValue();
	}

	public static void stop() {
		Robot.stop();
	}

	/**
	 * Pops new cells of the stack moveStack and moves the robot to those
	 * locations.
	 **/
	public static void returnToStart() {
		Cell currentCell = Robot.popCellFromPath();
		while (currentCell != null) {
			Cell c = Robot.popCellFromPath();
			int goalX = c.getRow();
			int goalY = c.getCol();
			// Figures out the direction the robot needs to move from the goal
			// state
			// and the current orientation of the robot.
			if (orientation == 0) {
				if (goalX < currentCell.getRow())
					turnLeft();
				else if (goalX > currentCell.getRow())
					turnRight();
				else {
					if (goalY > currentCell.getCol())
						forward();
					else
						backward();
				}
			} else if (orientation == 1) {
				if (goalY < currentCell.getCol())
					turnLeft();
				else if (goalY > currentCell.getCol())
					turnRight();
				else {
					if (goalX < currentCell.getRow())
						forward();
					else
						backward();
				}
			} else if (orientation == 2) {
				if (goalX > currentCell.getRow())
					turnLeft();
				else if (goalX < currentCell.getRow())
					turnRight();
				else {
					if (goalY < currentCell.getCol())
						forward();
					else
						backward();
				}
			} else if (orientation == 3) {
				if (goalY > currentCell.getCol())
					turnLeft();
				else if (goalY < currentCell.getCol())
					turnRight();
				else {
					if (goalX > currentCell.getRow())
						forward();
					else
						backward();
				}
			}
		}

	}

	/**
	 * ultra sensor method to detect wall
	 * 
	 * @return
	 */
	public static int getDistance() {
		return uSensor.getDistance();
	}

	// array is here
	public static void main(String[] args) {

		// the default behavior
		Behavior b1 = new MoveForward();
		Behavior b2 = new SenseUltrasonic();
		Behavior b3 = new SenseLight();
		Behavior b4 = new Turn();

		Behavior[] bArray = { b3, b2, b4, b1 };

		// create the arbitrator
		Arbitrator arby = new Arbitrator(bArray);

		arby.start();

		// TODO: how to stop the arbitrator and start to return?
		returnToStart();
	}
}
