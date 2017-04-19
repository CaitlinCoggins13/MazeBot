import lejos.robotics.subsumption.Behavior;

/**
 * Sense light behavior detect the goal and call returnToStart method.
 * 
 * @author yuhu
 *
 */
public class SenseLight implements Behavior {

	private boolean suppressed = false;

	@Override
	public boolean takeControl() {
		return Robot.getLightValue() > 70;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		suppressed = false;
		// we are at the goal
		// call whatever has plotted the path back to the start
		Robot.returnToStart();
		System.exit(0);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}