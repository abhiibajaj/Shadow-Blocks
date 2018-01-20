package shadowblocks;

import java.util.ArrayList;
import java.util.Arrays;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;


public class World {
	
	private ArrayList<Sprite> sprites;
	private ArrayList<Target> targets = new ArrayList<>();
	// Sprites that need to be removed (i.e the tnt and cracked wall if they make contact)
	private ArrayList<Sprite> toDestroy = new ArrayList<>();
	
	private Explosion explosion;
	private Door door;
	private static int level=App.START_LEVEL;
	private Player player;
	
	// boolean value that tells App to change/restart level
	private boolean running=true;
	
	private boolean playerMoved=false;
	
	/**
	 * Generates the current level 
	 */
	public World() {
		
		if(level<=App.MAX_LEVEL) {
			sprites = Loader.loadSprites("res/levels/" + level +".lvl");
			for(Sprite sprite:sprites) {
				if(sprite instanceof Target) {
					targets.add((Target)sprite);
				} else if (sprite instanceof Player) {
					player = (Player) sprite;
				} else if (sprite instanceof Door) {
					door = (Door) sprite;
				} 
			}
		}
		
	}
	
	
	
	
	/** Tests for collisions between Sprites and generates a response for different events
	 * @param input A Sprite object
	 * @param dir	The direction that input is trying to move
	 * @return		Response to a collision or a lack thereof; returns false 
	 * 					if input can move in dir, and returns true if it cannot
	 */
	public boolean testCollision(Sprite input, int dir) {
		
		float x= input.getX()+((Movable) input).getDeltaX(dir);
		float y = input.getY()+((Movable) input).getDeltaY(dir);
		
		
		
		for(Sprite sprite: sprites) {
			
			// restart the level if player hits a unit
			if(((input instanceof Player && sprite instanceof Units)||
					(input instanceof Units && sprite instanceof Player))
					&& (Float.compare(sprite.getX(),x)==0) && 
					(Float.compare(sprite.getY(),y)==0)) {
				
				running=false;
			}
			
			
			if((sprite instanceof Pushable) && (Float.compare(sprite.getX(),x)==0) && 
				(Float.compare(sprite.getY(),y)==0)) {
				
				if(!(input instanceof Ice)) {
					
					//check the pushable sprite can actually be pushed to the new position
					if(pushableCheck((Pushable)sprite,dir)==false) {
						
						// Success! This object is set to pushed
						((Pushable) sprite).setPushed();
						((Pushable) sprite).setDir(dir);
						
						return false;
						
					} else {
						
						
						// change unit direction if they hit a pushable that couldn't be pushed any further
						if(input instanceof Skeleton) {
							
							((Skeleton) input).setDirection();
							
						} else if(input instanceof Rogue) {
							
							((Rogue) input).setDirection();
						}
						
						return true;
					}
				// handles ice collision with pushable objects
				} else {
					
					return true;
				}
				
			// handles collisions with objects that are blocked	
			} else if((sprite instanceof Wall || sprite instanceof Cracked||sprite instanceof Door) 
					&& (Float.compare(sprite.getX(),x)==0) && 
				(Float.compare(sprite.getY(),y)==0)) {
				
				// when ice collides with an object, sets it to be new the lastX and lastY, which 
				// can then be added to the history stack
				if(input instanceof Ice) {
					
					((Ice) input).setPushedFalse();
					((Ice) input).setMovedZero();
					((Ice)input).setLastX(input.getX());
					((Ice)input).setLastY(input.getY());
					
				}
				
				// change the units direction as they hit a blocked tile
				if(input instanceof Skeleton) {
					((Skeleton) input).setDirection();
				} else if (input instanceof Rogue) {
					((Rogue) input).setDirection();
				}
				return true;
				
			}
		}
		
		
		return false;
	}
	
	
	
	/** Check if the object being pushed is being pushed to a valid location
	 * @param pushed Pushable object
	 * @param dir	The direction it is being pushed
	 * @return		Returns false if object can be pushed to the new position and returns true if it cannot
	 */
	public boolean pushableCheck(Pushable pushed,int dir) {
		
		
		float newX= pushed.getX()+pushed.getDeltaX(dir);
		float newY = pushed.getY()+pushed.getDeltaY(dir);
		
		
		for(Sprite sprite:sprites) {
			
			// Cannot be pushed into one of these Blocked Tiles
			if((sprite instanceof Wall || sprite instanceof Stone || sprite instanceof Cracked 
					|| sprite instanceof Door)
					&& (Float.compare(sprite.getX(),newX)==0) && 
					(Float.compare(sprite.getY(),newY)==0)) {
				// Handle the case where the pushed object is a Tnt and makes contact with a Cracked Wall
				if(pushed instanceof Tnt && sprite instanceof Cracked) {
					toDestroy.add(pushed);
					toDestroy.add(sprite);
					explosion = new Explosion(pushed.getX(),pushed.getY());
				} 
				return true;
			}
		}
		return false;
	}

	
	/** Checks if the mage is moving to a valid position
	 * @param x	The x position that the mage is trying to move to
	 * @param y The y position that the mage is trying to move to
	 * @return  Return true if invalid position, false otherwise
	 */
	public boolean mageCollision(float x,float y) {
		
		for(Sprite sprite:sprites) {
			
			if((sprite instanceof Wall || sprite instanceof Door)
				&& (Float.compare(sprite.getX(),x)==0) && 
				(Float.compare(sprite.getY(),y)==0)){
				
				return true;
				
			// Restart level if mage hits player
			} else if((sprite instanceof Player) && (Float.compare(sprite.getX(),x)==0) && 
				(Float.compare(sprite.getY(),y)==0)){
				
				running = false;
			}
		}
		return false;
		
	}
	
	
	/** Update the game state
	 * @param  input	The Slick game Input object
	 * @param  delta	Time passed since last frame
	 * @return Returns true if level is over/needs to be restarted
	 */
	public boolean update(Input input, int delta) {
		for (Sprite sprite : sprites) {
			
			if (sprite != null) {
				
				sprite.update(this, input, delta);
				if(!running) {
					return true;
				}
		
			}
			
		}
		
		// increases level and returns true, allowing App to move to the next level if level is complete
		if(checkWin()) {
			level++;
			return true;
		}
		
		// Remove sprites from main arraylist and cause an explosion 
		if(!(toDestroy).isEmpty()) {
			sprites.removeAll(toDestroy);
			toDestroy.clear();
			sprites.add(explosion);
			explosion.setActivated();
		}
		
		// If there was an explosion, remove it after its timer runs out
		if(explosion!=null && explosion.getActivated()==false) {
			sprites.remove(explosion);
			explosion=null;
			
		}
		System.out.println(player);
		return false;
		
	}
	
	/** Render the sprites
	 * @param The Slick Graphics object, used for drawing
	 */
	public void render(Graphics g) {
		for (Sprite sprite : sprites) {
			if (sprite != null) {
				sprite.render(g);
			}
		}
		
	}
	 
	
	/** Turns switch on if a Pushable object is at the same position 
	 * @param Switch 
	 */
	public void checkSwitch(Switch switches) {
		
		Float coord[]= {switches.getX(),switches.getY()};
		
		for(Sprite sprite: sprites) {
			
			if(sprite instanceof Pushable) {
				
				Float pos[]= {sprite.getX(),sprite.getY()};
				
				if(Arrays.equals(coord, pos)) {
					switches.setSwitchedOnTrue();
					break;
				} 
				else {
					switches.setSwitchedOnFalse();
				}
			} 
		}
		
	}
	
	/**
	 * Opens the door
	 */
	public void openDoor() {
		door.setClosedFalse();
	}
	
	
	/**
	 *  Closes the door
	 */
	public void closeDoor() {
		door.setClosedTrue();
	}
	
	/** Turns target on if target is covered by a Pushable object
	 * @param target
	 */
	public void checkTarget(Target target) {
		
		Float coord[]= {target.getX(),target.getY()};
		
		for(Sprite sprite:sprites) {
			
			if(sprite instanceof Pushable) {
				
				Float pos[]= {sprite.getX(), sprite.getY()};
				
				if(Arrays.equals(coord,pos)) {
					target.setActivatedTrue();
					break;
					
				} else {
					target.setActivatedFalse();
						
				}
			}
		}
	}
	
	/** Check if all targets are covered and the current level is over
	 * @return Returns true if all targets are activated, false otherwise
	 */
	public boolean checkWin() {
		int targetCount = 0;
		
		for(Target target: targets) {
			if(target.getActivated()==true) {
				targetCount++;
			}
		}
		return targetCount == targets.size();
	}
	
	/**
	 * Update the history stack for all Movable sprites
	 */
	public void updateHistory() {
		for(Sprite sprite: sprites) {
			if(sprite instanceof Movable) {
				((Movable) sprite).addToHistory();
			}
		}	
	}

	/** 
	 * Undoes every Movable sprite
	 */
	public void undoHistory() {
		for(Sprite sprite: sprites) {
			if(sprite instanceof Movable) {
				sprite.undo();
			}
		}
	}
	
	/**
	 * End the level
	 */
	public void setRunning() {
		running=false;
	}


	/**
	 * @return Player object
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * @return if the Player has moved
	 */
	public  boolean getPlayerMoved() {
		return playerMoved;
	}
	
	
	/**
	 * Set player moved to the opposite of what it is
	 */
	public  void setPlayerMoved() {
		playerMoved=!playerMoved;
	}


	
	
}
