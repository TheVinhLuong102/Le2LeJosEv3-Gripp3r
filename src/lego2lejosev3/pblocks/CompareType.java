/**
 * LeJOS Implementation of LEGO Mindstorms Programming Blocks
 */
package lego2lejosev3.pblocks;

/**
 * Compare type for sensor wait blocks.
 * 
 * @author Roland Blochberger
 */
public interface CompareType {

	public static final int EQUAL = 0; // equal to
	public static final int NOT_EQUAL = 1; // not equal to 
	public static final int GREATER = 2; // greater than
	public static final int GREATER_EQUAL = 3; // greater than orn equal to
	public static final int LESS = 4; // less than
	public static final int LESS_EQUAL = 5; // less than or equal to
}
