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
	static Cell currCell;
	static int orientation = 0;
	static UltrasonicSensor uSensor = new UltrasonicSensor(SensorPort.S1);
	static LightSensor lSensor = new LightSensor(SensorPort.S3);

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
		Cell currentCell = Path.popCell();
		while (currentCell != null) {
			Cell c = Path.popCell();
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
