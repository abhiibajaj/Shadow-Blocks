package shadowblocks;
/* This class was primarily adapted from the sample solution provided by Eleanor Mcmurty */
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;

public abstract class Sprite {
	
	public static final int DIR_NONE = 0;
	public static final int DIR_LEFT = 1;
	public static final int DIR_RIGHT = 2;
	public static final int DIR_UP = 3;
	public static final int DIR_DOWN = 4;
	
	private Image image = null;
	private float x;
	private float y;
	
	/** generates a Sprite
	 * @param x xPosition of generation
	 * @param y yPosition of generation
	 */
	public Sprite(String image_src, float x, float y) {
		try {
			image = new Image(image_src);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		
		this.setX(x);
		this.y = y;
		snapToGrid();
	}
	
	
	public void update(World world, Input input, int delta) {
		
	}
	
	/** Draw the sprites
	 * @param g The Slick Graphics object
	 */
	public void render(Graphics g) {
		image.drawCentered(getX(), y);
	}
	
	
	/**
	 *  Forces this sprite to align to the grid
	 */
	public void snapToGrid() {
		setX(getX() / App.TILE_SIZE);
		y /= App.TILE_SIZE;
		setX(Math.round(getX()));
		y = Math.round(y);
		setX(getX() * App.TILE_SIZE);
		y *= App.TILE_SIZE;
	}
	
	public void undo() {
		
	}


	/**
	 * @return current x coordinate
	 */
	public float getX() {
		return this.x;
	}

	/** Change current x to new x
	 * @param x new x coordinate
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * @return current y coordinate
	 */
	public float getY() {
		return this.y;
	}
	
	/** Change current y to new y
	 * @param y new y coordinate
	 */
	public void setY(float y) {
		this.y=y;
	}
	
}
