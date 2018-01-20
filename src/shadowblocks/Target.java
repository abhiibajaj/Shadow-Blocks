package shadowblocks;

import org.newdawn.slick.Input;

public class Target extends Sprite {
	
	// Shows current state of target
	private boolean activated = false;
	
	/** generates a Target
	 * @param x xPosition of generation
	 * @param y yPosition of generation
	 */
	public Target(float x, float y) {
		super("res/Target.png", x, y);
	}
	
	/** Check and update activated if target is covered
	 * @param world	The current instance of the world class
	 * @param input	The Slick game Input object
	 * @param delta	Time passed since last frame
	 */
	public void update(World world, Input input, int delta) {
		world.checkTarget(this);
	}
	
	/**
	 * Set activated to true
	 */
	public void setActivatedTrue() {
		this.activated=true;
	}
	
	
	/**
	 * Set activated to false
	 */
	public void setActivatedFalse() {
		this.activated=false;
	}
	
	
	/**
	 * @return current value of activated
	 */
	public boolean getActivated() {
		return this.activated;
	}
	
}
