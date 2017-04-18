import lejos.robotics.navigation.ArcMoveController;
import lejos.robotics.subsumption.Behavior;


public class MoveForward implements Behavior
{
	private ArcMoveController robot;
	
	public MoveForward(ArcMoveController r)
	{
		robot = r;
	}
	
	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		robot.travel(-8);
	}

	@Override
	public void suppress() {
		robot.stop();
	}

}
