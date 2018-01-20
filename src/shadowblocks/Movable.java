package shadowblocks;


import java.util.Arrays;
import java.util.Stack;

public abstract class Movable extends Sprite{
	
	// Store the previous positions
	private Stack<Float> historyStackX= new Stack<>();
	private Stack<Float> historyStackY= new Stack<>();
	
	/** Generate a Movable
	 * @param image_src type of sprite
	 * @param x	x coordinate for generation	
	 * @param y	y coordinate for generation
	 */
	public Movable(String image_src, float x, float y) {
		super(image_src, x, y);		
		// add the initial value to stack
		addToHistory();
	}
	
	
	/**  Translate the direction to an x displacement
	 * @param dir the direction
	 * @return the displacement in x direction
	 */
	public float getDeltaX(int dir) {
		float speed = 32;
		float delta_x=0;
		switch (dir) {
		case DIR_LEFT:
			delta_x = -speed;
			break;
		case DIR_RIGHT:
			delta_x = speed;
			break;
		}
		return delta_x;
	}
	
	/**  Translate the direction to an y displacement
	 * @param dir the direction
	 * @return the displacement in y direction
	 */
	public float getDeltaY(int dir) {
		float speed = 32;
		float delta_y=0;
		switch (dir) {
			case DIR_UP:
				delta_y = -speed;
				break;
			case DIR_DOWN:
				delta_y = speed;
				break;
		}
		return delta_y;
	}
	
	
	/**
	 * Add the current position to the history stacks
	 */
	public void addToHistory() {
		historyStackX.push(this.getX());
		historyStackY.push(this.getY());
	}
	
	
	/**
	 * @param world The current instance of World class
	 * @param dir The direction in which the current movable object is trying to move
	 */
	public void moveToDest(World world, int dir)  {		
		
		
		// The x and y displacement
		float delta_x = getDeltaX(dir);
		float delta_y=  getDeltaY(dir);
			
		
		
		// Make sure the position isn't occupied!
		if (!onMove(world,dir)) {
			this.setX(getX()+delta_x);
			this.setY(getY()+delta_y);
		} 
	}		
	
	
	/**Check if moving to a valid position
	 * @param world The current instance of World class
	 * @param dir The direction in which the current movable object is trying to move
	 */
	public boolean onMove(World world, int dir) {
		return world.testCollision(this,dir);
	}

	/**
	 * Return to the previous position of Movable
	 */
	public void undo() {
		if(!historyStackX.isEmpty()) {
			float newx =  historyStackX.pop();
			float newy =  historyStackY.pop();
			this.setX(newx);
			this.setY(newy);
			snapToGrid();
		}
	}
	
	/**
	 * @return the x coordinate history stack of Movable
	 */
	public Stack<Float> getHistoryStackX() {
		return historyStackX;
	}
	
	/**
	 * @return the y coordinate history stack of Movable
	 */
	public Stack<Float> getHistoryStackY() {
		return historyStackY;
	}

	
	
	/**
	 * Print the history of current movable object
	 */
	public void printHistory() {
		System.out.println(Arrays.toString(historyStackX.toArray()));
		System.out.println(Arrays.toString(historyStackY.toArray()));
	}	

}
