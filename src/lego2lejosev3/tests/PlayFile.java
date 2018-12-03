/**
 * 
 */
package lego2lejosev3.tests;

import java.util.logging.Logger;

import lego2lejosev3.logging.Setup;
import lego2lejosev3.pblocks.Sound;
import lego2lejosev3.pblocks.Utl;

/**
 * Test for playing a file.
 * 
 * @author Roland Blochberger
 */
public class PlayFile {

	private static Class<?> clazz = PlayFile.class;
	private static final Logger log = Logger.getLogger(clazz.getName());

	// sound files to play
	private static final String[] soundFiles = new String[] { "Air release.mp3", "Airbrake.mp3", "airbrake.wav",
			"airbrake2.wav", "airhiss1.wav", "airhiss2.wav" };

	/**
	 * Main program entry point.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// setup logging to file
		Setup.log2File(clazz);
		log.fine("Starting ...");

		for (String soundFile : soundFiles) {
			log.fine("Playing " + soundFile);
			// play sound file once and wait
			Sound.playFile(soundFile, 100, Sound.WAIT);
			// Wait 1 second
			Utl.waitTime(1F);
		}

		log.fine("The End");
	}

}
