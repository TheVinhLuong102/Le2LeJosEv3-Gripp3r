/**
 * LeJOS Implementation of LEGO Mindstorms Programming Blocks
 */
package lego2lejosev3.pblocks;

//import java.util.logging.Logger;

import lejos.hardware.Button;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.Port;

/**
 * Medium Motor Block.
 * 
 * @author Roland Blochberger
 * @see https://ev3-help-online.api.education.lego.com/Education/en-us/page.html?Path=blocks%2FLEGO%2FMediumMotor.html
 */
public class MediumMotor {

	// private static final Logger log =
	// Logger.getLogger(MediumMotor.class.getName());

	private Port motorPort;
	private UnregulatedMotor motor;

	/**
	 * Constructor.
	 * 
	 * @param motorPort
	 */
	public MediumMotor(Port motorPort) {
		this.motorPort = motorPort;
		// instantiate the motor object
		motor = new UnregulatedMotor(this.motorPort);
		// handle resources correctly before exiting
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				// stop the motor and wait until done
				motor.stop();
				// close resources
				motor.close();
			}
		}));
	}

	/**
	 * let motor run indefinitely and return immediately.
	 * 
	 * @param power set power percentage (0..100); + forward; - backward.
	 */
	public void motorOn(int power) {
		// setup motor and start it
		motor.setPower(power);
		motor.forward();
	}

	/**
	 * let motor run the specified period in seconds.
	 * 
	 * @param power  set power percentage (0..100); + forward; - backward.
	 * @param period the waiting time in seconds (> 0).
	 * @param brake  set true to brake at the end of movement; set false to
	 *               remove power but do not brake.
	 */
	public void motorOnForSeconds(int power, float period, boolean brake) {
		// setup motor and start it
		motor.setPower(power);
		motor.forward();
		// wait time in seconds
		Utl.waitTime(period);
		// switch motor off
		motorOff(brake);
	}

	/**
	 * let motor run the specified number of degrees.
	 * 
	 * @param power   set power percentage (0..100); + forward; - backward.
	 * @param degrees number of degrees (> 0).
	 * @param brake   set true to brake at the end of movement; set false to
	 *                remove power but do not brake.
	 */
	public void motorOnForDegrees(int power, int degrees, boolean brake) {
		motorOnForRotationsDegrees(power, 0, degrees, brake);
	}

	/**
	 * let motor run the specified number of rotations.
	 * 
	 * @param power     set power percentage (0..100); + forward; - backward.
	 * @param rotations number of rotations (> 0).
	 * @param brake     set true to brake at the end of movement; set false to
	 *                  remove power but do not brake.
	 */
	public void motorOnForRotations(int power, int rotations, boolean brake) {
		motorOnForRotationsDegrees(power, rotations, 0, brake);
	}

	/**
	 * let motor run the specified number of rotations and degrees.
	 * 
	 * @param power     set power percentage (0..100); + forward; - backward.
	 * @param rotations number of rotations (> 0).
	 * @param degrees   number of degrees (> 0).
	 * @param brake     set true to brake at the end of movement; set false to
	 *                  remove power but do not brake.
	 */
	public void motorOnForRotationsDegrees(int power, int rotations, int degrees, boolean brake) {
		if ((rotations > 0) || (degrees > 0)) {
			// setup motor and start it
			motor.setPower(power);
			// get start tacho count of the motor
			int mstc = motor.getTachoCount();
			// calculate the degrees to turn
			int degrs = (rotations * 360) + degrees;
			//log.fine("mstc: " + mstc + ", degrs: " + degrs);

			// start motor
			motor.forward();
			int mtc = 0;
			int mtcd = 0;
			int pdg = degrs;

			while (Button.ESCAPE.isUp()) {
				// get pending degrees
				mtc = motor.getTachoCount();
				mtcd = Math.abs(mtc - mstc);
				pdg = mtcd - degrs;
				//log.fine("mtc: " + mtc + ", mtcd: " + mtcd + "; pdg: " + pdg);

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
			// switch motor off
			motorOff(brake);
		}
	}

	/**
	 * stop left and right motors.
	 * 
	 * @param brake set true to brake at the end of movement; set false to
	 *              remove power but do not brake.
	 */
	public void motorOff(boolean brake) {
		if (brake) {
			motor.stop();
		} else {
			motor.flt();
		}
	}

}
