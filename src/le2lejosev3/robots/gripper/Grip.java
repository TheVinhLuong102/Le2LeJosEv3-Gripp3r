/**
 * 
 */
package le2lejosev3.robots.gripper;

import lego2lejosev3.pblocks.MediumMotor;
import lego2lejosev3.pblocks.Sound;
import lejos.hardware.port.Port;

/**
 * Gripp3r utility functions.
 * 
 * @author Roland Blochberger
 */
public class Grip {

	// the motor that moves the gripper
	private Port motorPort;
	private MediumMotor motor;

	/**
	 * Constructor.
	 * 
	 * @param motorPort
	 */
	public Grip(Port motorPort) {
		this.motorPort = motorPort;
		motor = new MediumMotor(this.motorPort);
	}

	/**
	 * Initialize the gripper (open it without sound).
	 */
	public void initGripper() {
		motor.motorOnForSeconds(-75, 0.9F, true);
	}

	/**
	 * Open the gripper.
	 */
	public void openGripper() {
		//Sound.playFile("airbrake2.wav", 100, Sound.ONCE);
		Sound.playFile("airhiss2.wav", 100, Sound.ONCE);
		motor.motorOnForSeconds(-75, 0.9F, true);
	}

	/**
	 * Close the gripper.
	 */
	public void closeGripper() {
		//Sound.playFile("airbrake2.wav", 100, Sound.ONCE);
		Sound.playFile("airhiss1.wav", 100, Sound.ONCE);
		motor.motorOnForSeconds(100, 0.9F, true);
	}
}
