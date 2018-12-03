/**
 * LeJOS Implementation of LEGO Mindstorms Programming Blocks
 */
package lego2lejosev3.pblocks;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.Image;
import lejos.hardware.lcd.LCD;

/**
 * Display Block.
 * 
 * @author Roland Blochberger
 * @see https://ev3-help-online.api.education.lego.com/Education/en-us/page.html?Path=blocks%2FLEGO%2FDisplay.html
 */
public class Display {

	private static final Logger log = Logger.getLogger(Display.class.getName());

	/** the base directory for sound files on the EV3 brick. */
	public static final String IMAGE_DIR = "/home/lejos/lib/";


	/**
	 * Draw a graphic image file.
	 * Note: The image file must already reside on the EV3 brick. Please upload it
	 * (by SCP) to the IMAGE_DIR directory before using this programming block.
	 * 
	 * @param filename the file name only.
	 * @param clearScreen set true to clear the screen before drawing; false to leave the screen as is.
	 * @param x the x coordinate of the upper left corner of the image.
	 * @param y the y coordinate of the upper left corner of the image.
	 */
	public static void image(String filename, boolean clearScreen, int x, int y) {
		File imageFile = new File(IMAGE_DIR + filename);
		if (imageFile.canRead()) {
			//throw new RuntimeException("Display Image is not yet implemented!");
			// read the file into an image
			InputStream fis = null;
			try {
				fis = new FileInputStream(imageFile);
				Image img = Image.createImage(fis);
				GraphicsLCD glcd = img.getGraphics();
				glcd.drawImage(img, x, y, 0);

			} catch (Exception e) {
				log.log(Level.WARNING, "Cannot read image file {0}: {1}", new Object[] { imageFile.getAbsolutePath(), e.toString()});

			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						// ignore
					}
				}
			}
		} else {
			log.log(Level.WARNING, "Cannot read image file {0}", imageFile.getAbsolutePath());
		}
		
	}

	/**
	 * Reset screen.
	 * implemented through clear screen.
	 */
	public static void resetScreen() {
		LCD.clear();
	}

}
