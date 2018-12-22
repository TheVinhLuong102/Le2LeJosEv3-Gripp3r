/**
 * 
 */
package lego2lejosev3.robots.gripper;

import java.util.logging.Logger;

import lego2lejosev3.logging.Setup;
import lego2lejosev3.pblocks.MoveSteering;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;

/**
 * Gripp3r Mission 2:
 * drive forward, grip load, turn right, drive forward, and release load.
 * 
 * @author Roland Blochberger
 */
public class Gripp3rM2 {

	private static Class<?> clazz = Gripp3rM2.class;
	private static final Logger log = Logger.getLogger(clazz.getName());

	static final Port gripMotorPort = MotorPort.A;
	static final Port leftMotorPort = MotorPort.B;
	static final Port rightMotorPort = MotorPort.C;

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

		// init the gripper
		grp.initGripper();

		// Run left & right motors forward for 3 rotations then brake
		mst.motorsOnForRotations(0, 75, 3, true);
		// Run left & right motors forward for 2 seconds then brake
		// mst.motorsOnForSeconds(0, 75, 2.0F, true);

		// close the gripper
		grp.closeGripper();

		// Run left & right motors forward for 850 degrees with steering +100 (right)
		// then brake
		mst.motorsOnForDegrees(100, 75, 850, true);

		// Run left & right motors forward for 2 seconds and turn right
		// mst.motorsOnForSeconds(100, 75, 2.0F, true);

		// Run left & right motors forward for 3 rotations then brake
		mst.motorsOnForRotations(0, 75, 3, true);

		// open the gripper
		grp.openGripper();

		// Run left & right motors backward for 2 seconds then brake
		mst.motorsOnForSeconds(0, -75, 2.0F, true);

		log.fine("The End");
	}

}
