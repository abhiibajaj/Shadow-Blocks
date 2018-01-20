package shadowblocks;

import org.newdawn.slick.Input;

public class Rogue extends Units {
	
	// directs which way the Rogue will move
	private boolean direction = true;
	
	/** generates a Rogue
	 * @param x xPosition of generation
	 * @param y yPosition of generation
	 */
	public Rogue(float x, float y) {
		super("res/rogue.png", x, y);
	}
	
	/** Updates the Rogue based on the validity of position and player input
	 * @param world	The current instance of the world class
	 * @param input	The Slick game Input object
	 * @param delta	Time passed since last frame
	 */
	public void update(World world, Input input, int delta) {
		
		if(world.getPlayerMoved()) {
			if(direction) {
				moveToDest(world,DIR_LEFT);
			} else {
				moveToDest(world,DIR_RIGHT);
			}
			world.setPlayerMoved();
		}	
		
	}
	/** 
	 * Rogue cannot be undo'd
	 */
	public void undo() {
		
	}
	
	/**	
	 * Oppose direction
	 */
	public void setDirection() {
		direction=!direction;		
	}
	
}
