package shadowblocks;

import org.newdawn.slick.Input;

public class Switch extends Sprite {
	
	// Current state of the switch
	private boolean switchedOn= false;
	
	/** generates a Switch
	 * @param x xPosition of generation
	 * @param y yPosition of generation
	 */
	public Switch(float x, float y) {
		super("res/switch.png", x, y);
	}
	
	/** Updates the Switch if it is covered by a pushable object, and closes or opens door
	 * based on boolean switchedOn
	 * @param world	The current instance of the world class
	 * @param input	The Slick game Input object
	 * @param delta	Time passed since last frame
	 */
	public void update(World world, Input input, int delta) {
		world.checkSwitch(this);
		if(switchedOn) {
			world.openDoor();
		} else {
			world.closeDoor();
		}
	}
	
	
	/**
	 * Set switchedOn to true
	 */
	public void setSwitchedOnTrue() {
		this.switchedOn=true;
	}
	
	/**
	 * Set switchedOn to false
	 */
	public void setSwitchedOnFalse() {
		this.switchedOn=false;
	}
	
	/**
	 * @return current switchedOn value
	 */
	public boolean getSwitchedOn() {
		return switchedOn;
	}
}
