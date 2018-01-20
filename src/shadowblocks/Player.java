package shadowblocks;



import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;


public class Player extends Movable{
	// keep count of moves made
	private int moves=0;
	
	/** generates a Player
	 * @param x xPosition of generation
	 * @param y yPosition of generation
	 */
	public Player(float x, float y) {
		super("res/player_left.png", x, y);
		
	}

	@Override
	public void update(World world,Input input, int delta) {
		int dir = DIR_NONE;
		
		// Every time a valid key is pressed, the history stacks are updated, playerMoved is set to true 
		// and an attempt to move to the new position is made, with move count going up
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			
			world.setPlayerMoved();
			dir = DIR_LEFT;
			world.updateHistory();
			moveToDest(world,dir);			
			moves++;
			
		} else if (input.isKeyPressed(Input.KEY_RIGHT)) {
			
			world.setPlayerMoved();
			dir = DIR_RIGHT;
			world.updateHistory();
			moveToDest(world,dir);
			moves++;
			
		} else if (input.isKeyPressed(Input.KEY_UP)) {
			world.setPlayerMoved();
			dir = DIR_UP;
			world.updateHistory();
			moveToDest(world,dir);
			moves++;
			
		} else if (input.isKeyPressed(Input.KEY_DOWN)) {
			
			world.setPlayerMoved();
			dir = DIR_DOWN;
			world.updateHistory();
			moveToDest(world,dir);
			moves++;
		
		// history is undone
		} else if(input.isKeyPressed(Input.KEY_Z)) {
			// Had a weird issue where pressing z at 0 moves would take 2 key presses for the player
			// to start moving, removeAllListeners would fix this issue
			if(moves<=0) {
				input.removeAllListeners();
			} else {
				// undo the history stacks for all sprites
				world.undoHistory();
				moves--;
			}
			
		// level is restarted
		} else if (input.isKeyPressed(Input.KEY_R)) {
			
			world.setRunning();
			moves=0;
		}
		
	}
	
	
	
	public void render(Graphics g) {
		super.render(g);
		// draw the player move count
		g.drawString("Moves: "+Integer.toString(moves), 0, 0);
	}
	
	public String toString() {
		return String.format("%s is at %f %f\n", this.getClass().getName(), this.getX(),this.getY());
	}

	
}
