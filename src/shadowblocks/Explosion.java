package shadowblocks;


import org.newdawn.slick.Input;


public class Explosion extends Sprite {
	
	private static final int EXPLOSION_TIME_MILLI = 400;
	private boolean activated=false;
	private int time = 0;
	
	/** generates an Explosion
	 * @param x xPosition of generation
	 * @param y yPosition of generation
	 */
	public Explosion(float x, float y) {
		super("res/explosion.png", x, y);
	}
	
	/** Updates the explosion based on boolean activated and its duration. Lasts for 0.4 seconds
	 * @param world	The current instance of the world class
	 * @param input	The Slick game Input object
	 * @param delta	Time passed since last frame
	 */
	public void update(World world, Input input, int delta) {
		
		if(activated) {
			time+=delta;
			if((time/EXPLOSION_TIME_MILLI)>1) {
				activated = false;
			}
		}
	}
	
	
	/**
	 * Activate the explosion
	 */
	public void setActivated() {
		activated = true;
	}
	
	/**
	 * @return boolean activated
	 */
	public boolean getActivated() {
		return activated;
	}
	
}
