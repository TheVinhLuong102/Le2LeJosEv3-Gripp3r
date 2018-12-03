/**
 * LeJOS Implementation of LEGO Mindstorms Programming Blocks
 */
package lego2lejosev3.pblocks;

import java.io.File;
import java.util.logging.Logger;

import lejos.hardware.Button;

/**
 * Sound Block.
 * 
 * @author Roland Blochberger
 * @see https://ev3-help-online.api.education.lego.com/Education/en-us/page.html?Path=blocks%2FLEGO%2FSound.html
 */
public class Sound {

	private static final Logger log = Logger.getLogger(Sound.class.getName());
	
	/** the play types */
	public static final int WAIT = 0; // play once and wait for completion.
	public static final int ONCE = 1; // play once and return immediately.
	public static final int REPEAT = 2; // play continuously and return immediately.

	/** the base directory for sound files on the EV3 brick. */
	public static final String SOUND_DIR = "/home/lejos/lib/";
	
	// create and start the background sound thread
	private static SoundThread soundthread = new SoundThread();
	static {
		soundthread.start();
	}

	/**
	 * Play a sound file.
	 * Note: The sound file must already reside on the EV3 brick. Please upload it
	 * (by SCP) to the SOUND_DIR directory before using this programming block.
	 * 
	 * @param filename the file name only.
	 * @param volume   the volume (0..100).
	 * @param playType the play type, one of WAIT, ONCE, or REPEAT.
	 */
	public static void playFile(String filename, int volume, int playType) {
		File soundFile = new File(SOUND_DIR + filename);
		if (soundFile.canRead()) {
			switch (playType) {
			case WAIT:
				// Play sound and wait until done
				int r = lejos.hardware.Sound.playSample(soundFile, volume);
				if (r < 0) {
					log.warning("playSample(" + soundFile.getAbsolutePath() + "): Error " + r);
				}
				break;
			case ONCE:
				soundthread.setFile(soundFile, volume, false);
				break;
			case REPEAT:
				soundthread.setFile(soundFile, volume, true);
				break;
			default:
				throw new RuntimeException("Invalid play type value: " + playType);
			}

		} else {
			log.warning("Cannot read sound file " + soundFile.getAbsolutePath());
		}
	}

	public static void playTone() {

	}
	
	static class SoundThread extends Thread {

		private File soundFile = null;
		private int volume = 0;
		private boolean repeat = false; 

		/**
		 * Constructor
		 */
		public SoundThread() {
			this.setDaemon(true);
			this.setPriority(MIN_PRIORITY);
		}
		
		/**
		 * Set the parameter to play a sound file.
		 * 
		 * @param soundFile
		 * @param volume
		 * @param repeat
		 */
		public void setFile(File soundFile, int volume, boolean repeat) {
			synchronized (this) {
				this.soundFile = soundFile;
				this.volume = volume;
				this.repeat = repeat;
			}
		}

		/**
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			File soundFile = null;
			int volume = 0;
			boolean repeat = false; 
			while (Button.ESCAPE.isUp()) {
				Thread.yield();
				synchronized (this) {
					soundFile = this.soundFile;
					volume = this.volume;
					repeat = this.repeat;
				}
				if (soundFile != null) {
					// Play sound and wait until done
					int r = lejos.hardware.Sound.playSample(soundFile, volume);
					if (r < 0) {
						log.warning("playSample(" + soundFile.getAbsolutePath() + "): Error " + r);
					}
					if (!repeat) {
						synchronized (this) {
							this.soundFile = null;
						}
					}
				}
			}
		}
	}
}
