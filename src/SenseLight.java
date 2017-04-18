import lejos.robotics.navigation.ArcMoveController;
import lejos.robotics.subsumption.Behavior;
import lejos.nxt.LightSensor;


public class SenseLight implements Behavior
{
	private ArcMoveController robot;
	private LightSensor light;
	
	public SenseLight(ArcMoveController r, LightSensor l)
	{
		robot = r;
		light = l;
	}
	
	@Override
	public boolean takeControl() {
		
		// TODO Auto-generated method stub
		// if the robot is in the area where the goal could be, then return true
		return false;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		if(light.getLightValue() > 70) //number???
		{
			// if the ground is above some percentage white, we are at the goal
			// call whatever has plotted the path back to the start
		}
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		//do not
	}

}