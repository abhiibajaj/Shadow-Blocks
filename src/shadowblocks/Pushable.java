package shadowblocks;



import org.newdawn.slick.Input;

public abstract class Pushable extends Movable {
	
	private boolean pushed = false;
	private int dir = DIR_NONE;
	
	/** Generate a Pushable
	 * @param image_src type of sprite
	 * @param x	x coordinate for generation	
	 * @param y	y coordinate for generation
	 */
	public Pushable(String image_src, float x, float y) {
		super(image_src, x, y);
		
	}
	
	/** Attempt for moving to new position
	 * @param world The current instance of World class
	 * @param dir The direction in which the current movable object is trying to move
	 */
	public void push(World world,int dir) {
		moveToDest(world,dir);
	}
	
	/** Updates the pushable object if it has been pushed (i.e boolean pushed)
	 * @param world	The current instance of the world class
	 * @param input	The Slick game Input object
	 * @param delta	Time passed since last frame
	 */
	public void update(World world, Input input, int delta){
		if(pushed) {
			push(world,dir);
			pushed=false;
		} 		
	}
	

	/**
	 * @return pushed boolean
	 */
	public boolean getPushed() {
		return pushed;
	}
	
	/**
	 * Set pushed to be true
	 */
	public void setPushed() {
		pushed = true;
	}
	
	/**
	 * Set pushed to be false
	 */
	public void setPushedFalse() {
		pushed = false;
	}
	
	/** Change dir to new dir
	 * @param dir the direction to be changed to
	 */
	public void setDir(int dir) {
		this.dir = dir;
	}

	/**
	 * Return current dir
	 */
	public int getDir() {
		return this.dir;
	}

}
