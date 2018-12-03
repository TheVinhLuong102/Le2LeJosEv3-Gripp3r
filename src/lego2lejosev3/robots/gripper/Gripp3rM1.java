/**
 * 
 */
package lego2lejosev3.robots.gripper;

import java.util.logging.Logger;

import lego2lejosev3.logging.Setup;
import lego2lejosev3.pblocks.MediumMotor;
import lego2lejosev3.pblocks.Sound;
import lego2lejosev3.pblocks.Utl;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;

/**
 * Gripp3r Mission 1:
 * grip the load and then release the load.
 * 
 * @author Roland Blochberger
 */
public class Gripp3rM1 {

	private static Class<?> clazz = Gripp3rM1.class;
	private static final Logger log = Logger.getLogger(clazz.getName());

	static final Port gripMotorPort = MotorPort.A;

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
		MediumMotor mot = new MediumMotor(gripMotorPort);

		// init the gripper: run motor backward for 0.9 seconds and brake afterward
		mot.motorOnForSeconds(-75, 0.9F, true);

		// play sound file once in background
		Sound.playFile("airhiss1.wav", 100, Sound.ONCE);
		// close the gripper: run motor forward for 0.9 seconds and brake afterward
		mot.motorOnForSeconds(100, 0.9F, true);

		// Wait 1 second
		Utl.waitTime(1F);

		// play sound file once in background
		Sound.playFile("airhiss2.wav", 100, Sound.ONCE);
		// open the gripper: run motor backward for 0.9 seconds and brake afterward
		mot.motorOnForSeconds(-75, 0.9F, true);

		log.fine("The End");
	}

}
