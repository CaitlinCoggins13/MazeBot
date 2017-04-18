import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.ArcMoveController;
import lejos.robotics.subsumption.Behavior;

public class SenseUltrasonic implements Behavior {
	private ArcMoveController robot;
	private UltrasonicSensor sensor;
	private boolean suppressed = false;

	// array passed in here
	public SenseUltrasonic(ArcMoveController r, UltrasonicSensor s) {
		robot = r;
		sensor = s;
	}

	@Override
	public boolean takeControl() {
		return sensor.getDistance()<10;
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
