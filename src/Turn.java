import lejos.robotics.subsumption.Behavior;

public class Turn implements Behavior {

	private boolean suppressed = false;

	@Override
	public boolean takeControl() {
		// if there's a wall directly in front of the robot on the map, true
		return false;
	}

	@Override
	public void action() {
		suppressed = false;
		Robot.turnLeft();
		while (Robot.isMoving() || !suppressed) {
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
