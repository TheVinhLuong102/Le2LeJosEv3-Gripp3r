/**
 * 
 */
package le2lejosev3.robots.gripper;

import java.util.logging.Logger;

import le2lejosev3.logging.Setup;
import le2lejosev3.pblocks.InfraredSensor;
import le2lejosev3.pblocks.MoveSteering;
import le2lejosev3.pblocks.Wait;
import lejos.hardware.Button;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

/**
 * Gripp3r Mission 3:
 * drive forward until infrared sensor detects an object, grip load, turn right,
 * drive forward until infrared sensor detects an object, and release load.
 * 
 * @author Roland Blochberger
 */
public class Gripp3rM3 {

	private static Class<?> clazz = Gripp3rM3.class;
	private static final Logger log = Logger.getLogger(clazz.getName());

	static final Port gripMotorPort = MotorPort.A;
	static final Port leftMotorPort = MotorPort.B;
	static final Port rightMotorPort = MotorPort.C;
	static final Port irSensorPort = SensorPort.S4;

	/**
	 * Main program entry point.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// setup logging to file
		Setup.log2File(clazz);
		log.fine("Starting ...");

		// instantiate the programming blocks used
		Grip grp = new Grip(gripMotorPort);
		MoveSteering mst = new MoveSteering(leftMotorPort, rightMotorPort);
		InfraredSensor irs = new InfraredSensor(irSensorPort);

		// init the gripper
		grp.initGripper();

		// Run left & right motors forward
		mst.motorsOn(0, 75);

		// wait until infrared sensor detects proximity < 25
		while (Button.ESCAPE.isUp()) {
			if (irs.measureProximity() < 25F) {
				break;
			}
			// wait between proximity measurements
			Wait.time(0.1F);
		}

		// stop left & right motors
		mst.motorsOff(true);

		// close the gripper
		grp.closeGripper();

		// Run left & right motors forward for 850 degrees with steering +100 (right)
		// then brake
		mst.motorsOnForDegrees(100, 75, 850, true);

		// Run left & right motors forward
		mst.motorsOn(0, 75);

		// wait for infrared sensor detects proximity < 25
		while (Button.ESCAPE.isUp()) {
			if (irs.measureProximity() < 25F) {
				break;
			}
			// wait between proximity measurements
			Wait.time(0.1F);
		}

		// stop left & right motors
		mst.motorsOff(true);

		// open the gripper
		grp.openGripper();

		log.fine("The End");
	}

}
