/**
 * 
 */
package lego2lejosev3.robots.gripper;

import java.util.logging.Logger;

import lego2lejosev3.logging.Setup;
import lego2lejosev3.pblocks.InfraredSensor;
import lego2lejosev3.pblocks.LargeMotor;
import lejos.hardware.Button;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

/**
 * Gripp3r Mission 4:
 * use infrared remote control.
 * 
 * @author Roland Blochberger
 */
public class Gripp3rM4 {

	private static Class<?> clazz = Gripp3rM4.class;
	private static final Logger log = Logger.getLogger(clazz.getName());

	static final Port gripMotorPort = MotorPort.A;
	static final Port leftMotorPort = MotorPort.B;
	static final Port rightMotorPort = MotorPort.C;
	static final Port irSensorPort = SensorPort.S4;

	static final int REMOTE_CHANNEL = 1;

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
		LargeMotor left = new LargeMotor(leftMotorPort);
		LargeMotor right = new LargeMotor(rightMotorPort);
		InfraredSensor irs = new InfraredSensor(irSensorPort);

		int remoteCode = 0;
		boolean beacon = false;
		// main loop
		while (Button.ESCAPE.isUp()) {
			// get the remote command code
			remoteCode = irs.measureRemote(REMOTE_CHANNEL);
			// check beacon on or off
			if (remoteCode == InfraredSensor.BEACON) {
				beacon = true;
			}
			if (remoteCode == InfraredSensor.NONE) {
				beacon = false;
			}
			log.fine("remoteCode: " + remoteCode + ", beacon: " + beacon);
			if (!beacon) {
				// beacon mode not activated: move the robot
				if (remoteCode == InfraredSensor.TOP_LEFT || remoteCode == InfraredSensor.TOP_BOTH
						|| remoteCode == InfraredSensor.TOP_LEFT_BOTTOM_RIGHT) {
					// left motor forward
					left.motorOn(75);

				} else {
					if (remoteCode == InfraredSensor.BOTTOM_LEFT || remoteCode == InfraredSensor.TOP_RIGHT_BOTTOM_LEFT
							|| remoteCode == InfraredSensor.BOTTOM_BOTH) {
						// left motor backward
						left.motorOn(-75);

					} else {
						// left motor stop without brake
						left.motorOff(false);
					}
				}
				if (remoteCode == InfraredSensor.TOP_RIGHT || remoteCode == InfraredSensor.TOP_BOTH
						|| remoteCode == InfraredSensor.TOP_RIGHT_BOTTOM_LEFT) {
					// right motor forward
					right.motorOn(75);

				} else {
					if (remoteCode == InfraredSensor.BOTTOM_RIGHT || remoteCode == InfraredSensor.TOP_LEFT_BOTTOM_RIGHT
							|| remoteCode == InfraredSensor.BOTTOM_BOTH) {
						// right motor backward
						right.motorOn(-75);

					} else {
						// right motor stop without brake
						right.motorOff(false);
					}
				}
			} else {
				// beacon mode activated: move the gripper
				if (remoteCode == InfraredSensor.TOP_LEFT) {
					grp.closeGripper();
				}
				if (remoteCode == InfraredSensor.TOP_RIGHT) {
					grp.openGripper();
				}
			}
		}

		log.fine("The End");
	}
}
