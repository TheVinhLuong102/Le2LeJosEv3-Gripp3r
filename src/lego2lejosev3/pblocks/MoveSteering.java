/**
 * LeJOS Implementation of LEGO Mindstorms Programming Blocks
 */
package lego2lejosev3.pblocks;

//import java.util.logging.Logger;

import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.Port;

/**
 * Move Steering Block
 * 
 * @author Roland Blochberger
 * @see https://ev3-help-online.api.education.lego.com/Education/en-us/page.html?Path=blocks%2FLEGO%2FMove.html
 * @see EV3 Move Steering Block Explained,
 *      https://communities.theiet.org/blogs/698/1706
 */
public class MoveSteering {

	// private static final Logger log =
	// Logger.getLogger(MoveSteering.class.getName());

	private Port leftMotorPort;
	private Port rightMotorPort;

	private UnregulatedMotor leftMotor;
	private UnregulatedMotor rightMotor;

	/**
	 * Constructor.
	 * 
	 * @param leftMotorPort
	 * @param rightMotorPort
	 */
	public MoveSteering(Port leftMotorPort, Port rightMotorPort) {
		this.leftMotorPort = leftMotorPort;
		this.rightMotorPort = rightMotorPort;
		// instantiate the motor objects
		leftMotor = new UnregulatedMotor(this.leftMotorPort);
		rightMotor = new UnregulatedMotor(this.rightMotorPort);
		// handle resources correctly before exiting
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				// stop the motors and wait until done
				leftMotor.stop();
				rightMotor.stop();
				// close resources
				leftMotor.close();
				rightMotor.close();
			}
		}));
	}

	/**
	 * let left and right motors run indefinitely and return immediately.
	 * 
	 * @param steering set amount of steering (0..100); + for right; - for left; 100
	 *                 means turn on the spot.
	 * @param power    set power percentage (0..100); + forward; - backward.
	 */
	public void motorsOn(int steering, int power) {
		setupMotors(steering, power);
		leftMotor.forward();
		rightMotor.forward();
	}

	/**
	 * let left and right motors run the specified period in seconds.
	 * 
	 * @param steering set amount of steering (0..100); + for right; - for left; 100
	 *                 means turn on the spot.
	 * @param power    set power percentage (0..100); + forward; - backward.
	 * @param period   the waiting time in seconds (> 0).
	 * @param brake    set true to brake at the end of movement; set false to
	 *                 remove power but do not brake.
	 */
	public void motorsOnForSeconds(int steering, int power, float period, boolean brake) {
		if (period > 0) {
			// setup motors and start them
			setupMotors(steering, power);
			leftMotor.forward();
			rightMotor.forward();
			// wait time in seconds
			Utl.waitTime(period);
			// switch motors off
			motorsOff(brake);
		}
	}

	/**
	 * let left and right motors run for the specified number of rotations.
	 * 
	 * @param steering  set amount of steering (0..100); + for right; - for left;
	 *                  100 means turn on the spot.
	 * @param power     set power percentage (0..100); + forward; - backward.
	 * @param rotations number of rotations (> 0).
	 * @param brake     set true to brake at the end of movement; set false to
	 *                  remove power but do not brake.
	 */
	public void motorsOnForRotations(int steering, int power, int rotations, boolean brake) {
		motorsOnForRotationsDegrees(steering, power, rotations, 0, brake);
	}

	/**
	 * let left and right motors run for the specified number of degrees.
	 * 
	 * @param steering set amount of steering (0..100); + for right; - for left;
	 *                 100 means turn on the spot.
	 * @param power    set power percentage (0..100); + forward; - backward.
	 * @param degrees  number of degrees (> 0).
	 * @param brake    set true to brake at the end of movement; set false to
	 *                 remove power but do not brake.
	 */
	public void motorsOnForDegrees(int steering, int power, int degrees, boolean brake) {
		motorsOnForRotationsDegrees(steering, power, 0, degrees, brake);
	}

	/**
	 * let left and right motors run the specified number of rotations and degrees.
	 * 
	 * @param steering  set amount of steering (0..100); + for right; - for left;
	 *                  100 means turn on the spot.
	 * @param power     set power percentage (0..100); + forward; - backward.
	 * @param rotations number of rotations of the motor (> 0).
	 * @param degrees   number of degrees (> 0).
	 * @param brake     set true to brake at the end of movement; set false to
	 *                  remove power but do not brake.
	 */
	public void motorsOnForRotationsDegrees(int steering, int power, int rotations, int degrees, boolean brake) {
		if ((rotations > 0) || (degrees > 0)) {
			// setup motors
			setupMotors(steering, power);

			// get start tacho count of the motors
			int lmstc = leftMotor.getTachoCount();
			int rmstc = rightMotor.getTachoCount();

			// determine which motor has the bigger power and thus should be monitored for
			// the degrees
			boolean lal = (leftMotor.getPower() >= rightMotor.getPower());
			// calculate the degrees to turn
			int degrs = (rotations * 360) + degrees;
			// log.fine("lmstc: " + lmstc + ", rmstc: " + rmstc + ", lal: " + (lal ? "L" :
			// "R") + ", degrs: " + degrs);

			// start motors
			leftMotor.forward();
			rightMotor.forward();
			int lmtc = 0;
			int rmtc = 0;
			int lmtcd = 0;
			int rmtcd = 0;
			int pdg = degrs;

			while (Button.ESCAPE.isUp()) {
				// get pending degrees
				if (lal) {
					lmtc = leftMotor.getTachoCount();
					lmtcd = Math.abs(lmtc - lmstc);
					pdg = lmtcd - degrs;
				} else {
					rmtc = rightMotor.getTachoCount();
					rmtcd = Math.abs(rmtc - rmstc);
					pdg = rmtcd - degrs;
				}
				// log.fine("lmtc: " + lmtc + ", lmtcd: " + lmtcd + "; rmtc: " + rmtc + ",
				// rmtcd: " + rmtcd + "; pdg: " + pdg);

				if (pdg >= 0) {
					break;
				}
				// wait until motors have reached their number of degrees
				if (pdg < 3) {
					// nearly done: just wait it out
					Thread.yield();

				} else {
					// wait by sleeping
					try {
						Thread.sleep(1L);
					} catch (InterruptedException e) {
						// leave loop
						break;
					}
				}
			}
			// switch motors off
			motorsOff(brake);
		}
	}

	/**
	 * stop left and right motors.
	 * 
	 * @param brake set true to brake at the end of movement; set false to
	 *              remove power but do not brake.
	 */
	public void motorsOff(boolean brake) {
		if (brake) {
			leftMotor.stop();
			rightMotor.stop();
		} else {
			leftMotor.flt();
			rightMotor.flt();
		}
	}

	/**
	 * setup motor power.
	 * 
	 * @param steering set amount of steering (0..100); + for right; - for left; 100
	 *                 means turn on the spot.
	 * @param power    set power percentage (0..100); + forward; - backward.
	 */
	protected void setupMotors(int steering, int power) {
		// limit the steering
		if (steering < -100) {
			steering = -100;
		} else if (steering > 100) {
			steering = 100;
		}
		// limit the power
		if (power < -100) {
			power = -100;
		} else if (power > 100) {
			power = 100;
		}
		// right turn (+0..+50): reduce power of the right motor; left motor full power.
		// right turn (+51..+100): reverse the power of the right motor; left motor full
		// power.
		// left turn (-0..-50): reduce power of the left motor; right motor full power.
		// left turn (-51..-100): reverse the power of the left motor; right motor full
		// power.
		int lmpwr = power;
		int rmpwr = power;
		if (steering > 0) {
			// right turn
			rmpwr = (int) (power * (1F - steering / 50F));
		} else if (steering < 0) {
			// left turn
			lmpwr = (int) (power * (1F + steering / 50F));
		}
		leftMotor.setPower(lmpwr);
		rightMotor.setPower(rmpwr);
	}
}
