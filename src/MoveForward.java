import lejos.robotics.subsumption.Behavior;

/**
 * MoveForward is the default behavior that is not suppressed unless the robot
 * is out of the edge or run into wall or get to goal.
 * 
 * @author yuhu
 *
 */
public class MoveForward implements Behavior {

	@Override
	public boolean takeControl() {
		// Stop moving forward if it's going to move out of bound.
		return Robot.canMoveForward();
	}

	@Override
	public void action() {
		Robot.forward();
		Robot.updateCellPath();
		while (Robot.isMoving()) {
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		Robot.stop();
	}

}
