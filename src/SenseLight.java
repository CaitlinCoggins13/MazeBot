import lejos.robotics.navigation.ArcMoveController;
import lejos.robotics.subsumption.Behavior;
import lejos.nxt.LightSensor;


public class SenseLight implements Behavior
{
	private ArcMoveController robot;
	private LightSensor light;
	private boolean suppressed = false;
	
	public SenseLight(ArcMoveController r, LightSensor l)
	{
		robot = r;
		light = l;
	}
	
	@Override
	public boolean takeControl() {
		return light.getLightValue() > 70;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		suppressed = false;
		// we are at the goal
		// call whatever has plotted the path back to the start
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}