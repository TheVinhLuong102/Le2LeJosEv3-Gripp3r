/**
 * 
 */
package lego2lejosev3.tests;

import java.util.logging.Logger;

import lego2lejosev3.logging.Setup;
import lego2lejosev3.pblocks.Display;
import lego2lejosev3.pblocks.Utl;

/**
 * Test for playing a file.
 * 
 * @author Roland Blochberger
 */
public class DisplayImage {

	private static Class<?> clazz = DisplayImage.class;
	private static final Logger log = Logger.getLogger(clazz.getName());

	// sound files to play
	private static final String[] imageFiles = new String[] { "IR beacon.bmp" };

	/**
	 * Main program entry point.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// setup logging to file
		Setup.log2File(clazz);
		log.fine("Starting ...");

		for (String imageFile : imageFiles) {
			log.fine("Displaying " + imageFile);
			// display image file on top left corner
			Display.image(imageFile, true, 0, 0);
			// Wait 10 seconds
			Utl.waitTime(10F);
		}

		log.fine("The End");
	}

}
