import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.ArcMoveController;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.navigation.DifferentialPilot;

public class Main {
	// array is here
	public static void main(String[] args)
	{
		ArcMoveController robot	= new DifferentialPilot(5.6f, 11.0f, Motor.A, Motor.C, true);

		UltrasonicSensor uSensor = new UltrasonicSensor(SensorPort.S1);
		LightSensor lSensor = new LightSensor(SensorPort.S3);
		
		//the default behavior
		Behavior b1	= new MoveForward(robot);
		Behavior b2	= new SenseUltrasonic(robot, uSensor);
		Behavior b3 = new SenseLight(robot, lSensor);
		Behavior b4 = new Turn(robot);

		Behavior[] bArray = {b3, b2, b4, b1};
		
		//create the arbitrator
		Arbitrator arby = new Arbitrator(bArray);

		arby.start();
	}
}
