package shadowblocks;

import org.newdawn.slick.Input;

public class Mage extends Units{

	/** Generates a mage
	 * @param x xPosition of generation
	 * @param y yPosition of generation
	 */
	public Mage(float x, float y) {
		super("res/mage.png", x, y);
		
	}
	
	/** Updates the mage position based on the algorithm provided in the specification
	 * @param world	The current instance of the world class
	 * @param input	The Slick game Input object
	 * @param delta	Time passed since last frame
	 */
	public void update(World world, Input input, int delta) {
		
		// Mage only moves when the player makes a move
		if(world.getPlayerMoved()) {
			float playerPixelX = Loader.xPixel(world.getPlayer().getX());
			float playerPixelY = Loader.yPixel(world.getPlayer().getY());
			float tilePixelX = Loader.xPixel(this.getX());
			float tilePixelY = Loader.yPixel(this.getY());
			
			float distanceX=playerPixelX-tilePixelX;
			float distanceY= playerPixelY-tilePixelY;
			
			// Algorithm for mage movement while checking for validity
			if(Math.abs(distanceX)>Math.abs(distanceY)) {
				
				if(world.mageCollision(this.getX()+sgn(distanceX)*App.TILE_SIZE, this.getY())==false){
					this.setX(this.getX()+sgn(distanceX)*App.TILE_SIZE);
				}
				
			} else {
				if(world.mageCollision(this.getX(),this.getY()+sgn(distanceY)*App.TILE_SIZE)==false){
					this.setY(this.getY()+sgn(distanceY)*App.TILE_SIZE);
				} 
				
			}
			world.setPlayerMoved();
		}
			
	}
	
	/** Implemented as outlined in the specification
	 * @param x distance float
	 * @return 
	 */
	public int sgn(float x) {
		if(x<0) {
			return -1;
		} 
		return 1;
	}
	public void undo() {
		
	}

}
