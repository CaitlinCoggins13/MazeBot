import lejos.robotics.subsumption.Behavior;

/**
 * Sense ultrasonic is activated when wall is detected.
 * 
 * @author yuhu
 *
 */
public class SenseUltrasonic implements Behavior {

	private boolean suppressed = false;

	@Override
	public boolean takeControl() {
		return Robot.touchWall();
	}

	@Override
	public void action() {
		suppressed = false;
		Robot.turnLeft();
		Robot.forward();
		while (Robot.isMoving() || !suppressed) {
			Thread.yield();
		}

	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
