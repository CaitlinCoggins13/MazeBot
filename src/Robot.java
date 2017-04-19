import java.util.Stack;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.ArcMoveController;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * A robot with a light sensor and an Ultrasonic sensor.
 * 
 * @author yuhu
 *
 */
public class Robot {

	private static ArcMoveController robot = new DifferentialPilot(5.6f, 11.0f, Motor.A, Motor.C, true);
	// TODO: do we know the starting location? If not, how do we find out the
	// row and col of starting cell
	private static Cell currCell;
	private static int orientation = 0;
	public static UltrasonicSensor uSensor = new UltrasonicSensor(SensorPort.S1);
	public static LightSensor lSensor = new LightSensor(SensorPort.S3);

	private static Stack<Cell> botPath = new Stack<Cell>();

	private static final int EAST = 0;
	private static final int NORTH = 1;
	private static final int WEST = 2;
	private static final int SOUTH = 3;

	/**
	 * Check if the bot is moving.
	 * 
	 * @return return true if the bot is moving.
	 */
	public static boolean isMoving() {
		return robot.isMoving();
	}

	/**
	 * Bot turn left and update orientation.
	 * 
	 */
	public static void turnLeft() {
		orientation = (orientation + 1) % 4;
		((DifferentialPilot) robot).rotate(130);
	}

	/**
	 * Bot turn right and update orientation.
	 */
	public static void turnRight() {
		orientation = (orientation - 1 + 4) % 4;
		((DifferentialPilot) robot).rotate(-130);
	}

	/**
	 * Bot go forward.
	 */
	public static void forward() {
		robot.travel(-8);
	}

	/**
	 * Bot go backward.
	 */
	public static void backward() {
		robot.travel(8);
	}

	/**
	 * Check if the robot is walking within the maze
	 * 
	 * @return return false if the robot is going to fall out of the edge.
	 */
	public static boolean canMoveForward() {
		int row = currCell.getRow();
		int col = currCell.getCol();
		if (orientation == EAST) {
			if (col + 1 >= 8)
				return false;

		} else if (orientation == NORTH) {
			if (row - 1 <= 0)
				return false;

		} else if (orientation == WEST) {
			if (col - 1 <= 0)
				return false;
		} else if (orientation == SOUTH) {
			if (row + 1 >= 5)
				return false;

		}
		return true;
	}

	/**
	 * Update the botPath.
	 */
	public static void updateCellPath() {
		int row = currCell.getRow();
		int col = currCell.getCol();
		if (orientation == EAST) {
			currCell = new Cell(row, col + 1);
		} else if (orientation == NORTH) {
			currCell = new Cell(row - 1, col);
		} else if (orientation == WEST) {
			currCell = new Cell(row, col - 1);
		} else if (orientation == SOUTH) {
			currCell = new Cell(row + 1, col);
		}
		botPath.push(currCell);
	}

	/**
	 * light sensor method to detect goal cell
	 * 
	 * @return return light value.
	 */
	public static int getLightValue() {
		return lSensor.getLightValue();
	}

	/**
	 * stop the robot
	 */
	public static void stop() {
		Robot.stop();
	}

	/**
	 * Pops new cells of the stack moveStack and moves the robot to those
	 * locations.
	 **/
	public static void returnToStart() {
		Cell currentCell = botPath.pop();
		while (currentCell != null && !botPath.isEmpty()) {
			Cell c = botPath.pop();
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
	 * ultra sensor detect wall
	 * 
	 * @return return distance to the wall.
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

	}
}
