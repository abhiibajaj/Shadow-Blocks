package shadowblocks;

import org.newdawn.slick.Input;

public class Skeleton extends Units {
	
	private int time=0;
	
	// directs which way the Skeleton will move
	private boolean direction=true;
	
	/** generates a Skeleton
	 * @param x xPosition of generation
	 * @param y yPosition of generation
	 */
	public Skeleton(float x, float y) {
		super("res/skull.png", x, y);
		
	}
	
	/** Updates the Skeleton based on the validity of position and timer. Moves every second
	 * @param world	The current instance of the world class
	 * @param input	The Slick game Input object
	 * @param delta	Time passed since last frame
	 */
	public void update(World world, Input input, int delta) {
		time+=delta;
		if(time/1000>1) {
			if(direction) {
				moveToDest(world,DIR_UP);
			} else {
				moveToDest(world,DIR_DOWN);
			}
			time=0;
		}
	}
	
	/** 
	 * Oppose direction
	 */
	public void setDirection() {
		direction=!direction;
	}
	
	/** 
	 * Skeleton cannot be undo'd
	 */
	public void undo() {
		
	}
}
