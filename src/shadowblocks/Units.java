package shadowblocks;

public abstract class Units extends Movable {
	
	/** generates Unit
	 * @param x xPosition of generation
	 * @param y yPosition of generation
	 */
	public Units(String image_src, float x, float y) {
		super(image_src, x, y);
	}

}
