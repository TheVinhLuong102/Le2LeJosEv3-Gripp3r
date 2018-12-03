/**
 * LeJOS Implementation of LEGO Mindstorms Programming Blocks
 */
package lego2lejosev3.pblocks;

import lejos.hardware.Button;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;

/**
 * Infrared Sensor Block.
 * 
 * @author Roland Blochberger
 * @see https://ev3-help-online.api.education.lego.com/Education/en-us/page.html?Path=blocks%2FLEGO%2FInfraredSensor.html
 * @see https://ev3-help-online.api.education.lego.com/Education/en-us/page.html?Path=editor%2FUsingSensors_Remote.html
 */
public class InfraredSensor {

	/** the remote control button codes */
	public static final int NONE = 0; // 0 = No button (and Beacon Mode is off)
	public static final int TOP_LEFT = 1; // Button 1
	public static final int BOTTOM_LEFT = 2; // Button 2
	public static final int TOP_RIGHT = 3; // Button 3
	public static final int BOTTOM_RIGHT = 4; // Button 4
	public static final int TOP_BOTH = 5; // Both Button 1 and Button 3
	public static final int TOP_LEFT_BOTTOM_RIGHT = 6; // Both Button 1 and Button 4
	public static final int TOP_RIGHT_BOTTOM_LEFT = 7; // Both Button 2 and Button 3
	public static final int BOTTOM_BOTH = 8; // Both Button 2 and Button 4
	public static final int BEACON = 9; // Beacon Mode is on
	public static final int LEFT_BOTH = 10; // Both Button 1 and Button 2
	public static final int RIGHT_BOTH = 11; // Both Button 3 and Button 4
	/*
	 * LeJOS Button codes:
	 * 1 TOP-LEFT
	 * 2 BOTTOM-LEFT
	 * 3 TOP-RIGHT
	 * 4 BOTTOM-RIGHT
	 * 5 TOP-LEFT + TOP-RIGHT
	 * 6 TOP-LEFT + BOTTOM-RIGHT
	 * 7 BOTTOM-LEFT + TOP-RIGHT
	 * 8 BOTTOM-LEFT + BOTTOM-RIGHT
	 * 9 CENTRE/BEACON
	 * 10 BOTTOM-LEFT + TOP-LEFT
	 * 11 TOP-RIGHT + BOTTOM-RIGHT
	 */

	private Port sensorPort;
	private EV3IRSensor sensor;
	private SampleProvider sp = null;
	private float[] sample = null;

	/**
	 * Constructor.
	 * 
	 * @param sensorPort
	 */
	public InfraredSensor(Port sensorPort) {
		this.sensorPort = sensorPort;
		sensor = new EV3IRSensor(this.sensorPort);
	}

	/**
	 * return the remote control button code.
	 * 
	 * @param channel the remote control channel (1..4).
	 * @return remote control button code, one of TOP_LEFT .. RIGHT_BOTH.
	 */
	public int getRemoteCommand(int channel) {
		if ((channel > 0) && (channel <= 4)) {
			return sensor.getRemoteCommand(channel - 1);
		} else {
			throw new RuntimeException("Invalid Channel value: " + channel);
		}
	}

	/**
	 * Setup the sensor to proximity mode.
	 */
	public void setProximityMode() {
		sp = sensor.getDistanceMode();
		sample = new float[sp.sampleSize()];
	}

	/**
	 * Fetch and return a sample from the sensor.
	 * In proximity mode this is the approximate distance in centimeters.
	 * 
	 * @return the sample
	 */
	public float measure() {
		sp.fetchSample(sample, 0);
		return sample[0];
	}

	/**
	 * wait until a sensor value compares to the specified threshold.
	 * 
	 * @param operator  compare operator; one of the CompareType values.
	 * @param threshold the sensor value to compare to.
	 * @return true if compare successful; false otherwise (e.g. on Escape button
	 *         pressed).
	 */
	public boolean waitCompare(int operator, float threshold) {
		float sample;
		boolean result = false;
		while (!result && Button.ESCAPE.isUp()) {
			sample = measure();
			switch (operator) {
			case CompareType.EQUAL:
				result = (sample == threshold);
				break;
			case CompareType.NOT_EQUAL:
				result = (sample != threshold);
				break;
			case CompareType.GREATER:
				result = (sample > threshold);
				break;
			case CompareType.GREATER_EQUAL:
				result = (sample >= threshold);
				break;
			case CompareType.LESS:
				result = (sample < threshold);
				break;
			case CompareType.LESS_EQUAL:
				result = (sample == threshold);
				break;
			default:
				throw new RuntimeException("Invalid Operator value: " + operator);
			}
		}
		return result;
	}
}
