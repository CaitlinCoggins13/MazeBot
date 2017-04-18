import lejos.robotics.navigation.ArcMoveController;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;


public class Turn implements Behavior
{
	private ArcMoveController robot;
	private boolean suppressed = false;
	
	public Turn(ArcMoveController r)
	{
		robot = r;
	}
	
	@Override
	public boolean takeControl() {
		//if there's a wall directly in front of the robot on the map, true
		return false;
	}

	@Override
	public void action() {
		suppressed = false;
		((DifferentialPilot)robot).rotate(130);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	
}
