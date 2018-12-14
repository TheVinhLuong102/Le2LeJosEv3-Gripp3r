/**
 * LeJOS Implementation of LEGO Mindstorms Programming Blocks
 */
package lego2lejosev3.pblocks;

//import java.util.logging.Logger;

import lejos.hardware.Button;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.Port;

/**
 * Medium Motor and Motor Rotation Blocks.
 * 
 * @author Roland Blochberger
 * @see https://ev3-help-online.api.education.lego.com/Education/en-us/page.html?Path=blocks%2FLEGO%2FMediumMotor.html
 */
public class MediumMotor {

	// private static final Logger log =
	// Logger.getLogger(MediumMotor.class.getName());

	private Port motorPort = null;
	private UnregulatedMotor motor = null;

	/**
	 * Constructor.
	 * 
	 * @param motorPort
	 */
	public MediumMotor(Port motorPort) {
		this.motorPort = motorPort;
		// instantiate the motor object
		motor = new UnregulatedMotor(this.motorPort);
		if (motor != null) {
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
	 * Motor Rotation Block: reset the motor's rotation to zero.
	 */
	public void rotationReset() {
		motor.resetTachoCount();
	}

	/**
	 * Motor Rotation Block: measure the current degrees turned since the last
	 * reset.
	 * 
	 * @return the degrees.
	 */
	public int measureDegrees() {
		return motor.getTachoCount();
	}

	/**
	 * Motor Rotation Block: measure the number of rotations turned since the last
	 * reset.
	 * 
	 * @return the rotations.
	 */
	public float measureRotations() {
		return (motor.getTachoCount() / 360F);
	}

	/**
	 * Motor Rotation Block: measure the current power level of the motor.
	 * 
	 * @return the current power level.
	 */
	public int measureCurrentPower() {
		return motor.getPower();
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
			// calculate the degrees to turn to
			int metc = (power > 0) ? (mstc + degrs) : (mstc - degrs);
			// log.fine("mstc: " + mstc + ", degrs: " + degrs + ", metc: " + metc);

			// start motor
			motor.forward();

			int mtc = 0; // newest sample
			int pdg = degrs; // pending degrees

			while (Button.ESCAPE.isUp()) {
				// get current degrees
				mtc = motor.getTachoCount();
				// get pending degrees to rotate
				pdg = (power > 0) ? (metc - mtc) : (mtc - metc);
				// log.fine("mtc: " + mtc + "; pdg: " + pdg);

				// check degrees reached
				if (pdg <= 0) {
					break;
				}
				// wait until motors have reached their number of degrees
				if (pdg < 10) {
					// nearly done: just wait it out
					Thread.yield();

				} else {
					// wait by sleeping between samples
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
