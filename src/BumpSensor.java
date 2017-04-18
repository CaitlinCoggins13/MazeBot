import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.ArcMoveController;
import lejos.robotics.subsumption.Behavior;


public class BumpSensor implements Behavior {
	private	ArcMoveController robot;
	private UltrasonicSensor sensor;
	private	boolean	suppressed = false;
	
	// array passed in here
	public BumpSensor(ArcMoveController r, UltrasonicSensor s)
	{
		robot = r;
		sensor = s;
	}
	
	@Override
	public boolean takeControl() {
		/**if(!robot.isMoving()||!suppressed)
		{
			suppressed = false;
			return true;
		}
		return false;**/
		//if current cell is unvisited, suppressed = false, return true
	}

	@Override
	public void action() {
		//cm??
		if(suppressed == false)
		{
			if(sensor.getDistance()<10)
			{
				//update map to show wall
			}
		}
		
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
}
