package shadowblocks;

import org.newdawn.slick.Input;

public class Door extends Sprite {
	
	
	private boolean closed = true;
	// The old positions so the door can reappear if the switch is uncovered
	private float oldFloatX;
	private float oldFloatY;
	
	/** 	  generates a Door 
	 * @param x xPosition of generation
	 * @param y yPosition of generation
	 */
	public Door(float x, float y) {
		super("res/door.png", x, y);
		oldFloatX = x;
		oldFloatY = y;
	}
	
	/** Updates the door position depending on the boolean closed
	 * @param world	The current instance of the world class
	 * @param input	The Slick game Input object
	 * @param delta	Time passed since last frame
	 */
	public void update(World world, Input input, int delta) {
		
		// if door is not closed/open, move it off of the screen, if it is closed set it to its original position
		if(!closed) {
			// move door off the screen to get the "hidden effect"
			this.setX(this.getX()+App.SCREEN_WIDTH);
			this.setY(this.getY()+App.SCREEN_HEIGHT);
		} else {
			this.setX(oldFloatX);
			this.setY(oldFloatY);
			snapToGrid();
		}
	}
	
	/**
	 * Close the door
	 */
	public void setClosedTrue() {
		closed = true;
	}
	
	/**
	 * Open the door
	 */
	public void setClosedFalse() {
		closed = false;
	}

}
