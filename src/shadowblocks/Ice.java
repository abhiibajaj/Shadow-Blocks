package shadowblocks;

import org.newdawn.slick.Input;

public class Ice extends Pushable {
	
	private float time=0;
	private int moved=0;
	
	// store the last stationary position of Ice
	private float lastX;
	private float lastY;
	
	private static final int ICE_TIME_MILLI = 250;
	
	/** generates an Ice
	 * @param x xPosition of generation
	 * @param y yPosition of generation
	 */
	public Ice(float x, float y) {
		super("res/ice.png", x, y);
		lastX=x;
		lastY=y;
	}
	
	/** Update the Ice position based on its timer. Should move until hits an immovable object every 0.25 seconds
	 * @param  world	The current instance of World class
	 * @param  input	The Slick game Input object
	 * @param  delta	Time passed since last frame
	 */
	public void update(World world, Input input, int delta)   {
			
			if(this.getPushed()) {
				// on the first push move straight to the next tile instead of waiting 0.25 seconds
				// after the initial push, move every 0.25 seconds until it hots a non valid position
				if(moved==0) {
					
					push(world,getDir());
					moved++;
					
				} else {	
					
					time+=delta;
					if((time/ICE_TIME_MILLI)>1) {
						
						this.push(world, getDir());
						time=0;	
	
					}	
				}
			}
	}
	
	/** Add the last stationary position (lastX and lastY) to the history stacks.
	 */
	public void addToHistory() {
		getHistoryStackX().add(lastX);
		getHistoryStackY().add(lastY);
	}
	
	/** Update lastX
	 * @param float that the current lastX will be changed to
	 */
	public void setLastX(float lastX) {
		this.lastX=lastX;
	}
	
	/** Update lastY
	 * @param float that the current lastY will be changed to
	 */
	public void setLastY(float lastY) {
		this.lastY=lastY;
	}
	
	/** Change moved to 0
	 */
	public void setMovedZero() {
		moved=0;
	}
	
	/** Return the ice to its last stationary position
	 */
	public void undo() {
		
		if(!(getHistoryStackX().isEmpty())){
			float newx =  getHistoryStackX().pop();
			float newy =  getHistoryStackY().pop();			
			this.setX(newx);
			this.setY(newy);
			lastX=newx;
			lastY=newy;
			snapToGrid();
			
		} 
		// ensure the ice doesn't start moving after it comes back to the previous position
		setDir(DIR_NONE);
	}
	
}


