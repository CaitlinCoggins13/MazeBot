import lejos.robotics.subsumption.Behavior;

public class SenseUltrasonic implements Behavior {

	private boolean suppressed = false;

	@Override
	public boolean takeControl() {
		return Robot.getDistance() < 10;
	}

	@Override
	public void action() {
		// cm??
		suppressed = false;
		// update map to show wall
		// turn????
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
