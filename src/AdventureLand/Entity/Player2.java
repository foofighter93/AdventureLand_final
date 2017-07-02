
package AdventureLand.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import AdventureLand.Manager.Content;
import AdventureLand.TileMap.TileMap;


public class Player2 extends Player {
	
	// sprites
	private BufferedImage[] downSprites;
	private BufferedImage[] leftSprites;
	private BufferedImage[] rightSprites;
	private BufferedImage[] upSprites;
	
	public static boolean rightfacing = false;
	public static boolean leftfacing = false;
	public static boolean downfacing = false;
	public static boolean upfacing = false;
	

	
	// animation
	protected final static int DOWN = 0;
	protected final static int LEFT = 1;
	protected final static int RIGHT = 2;
	protected final static int UP = 3;
	
	// gameplay
	public int numGoldCoins;
	private int totalGoldCoins;
	public int numSilverCoins;

	
	private boolean hasBoat;
	private boolean hasKey;
	public static boolean hasWeapon;
	private boolean hasVPotion;
	private boolean hasHPotion;
	private boolean onWater;
	private long ticks;
	public static int life2 = 100;
	
	private static ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	
	public Player2(TileMap tm) {
		
		super(tm);
		
		width = 24;
		height = 24;
		cwidth = 16;
		cheight = 16;
		
		moveSpeed = 3;
		
		numGoldCoins = 0;
		numSilverCoins = 0;
		
		downSprites = Content.PLAYER2[2];
		leftSprites = Content.PLAYER2[3];
		rightSprites = Content.PLAYER2[0];
		upSprites = Content.PLAYER2[1];
		
		animation.setFrames(rightSprites);
		animation.setDelay(5);
		
		bullets = new ArrayList();
	}
	
	private void setAnimation(int i, BufferedImage[] bi, int d) {
		currentAnimation = i;
		animation.setFrames(bi);
		animation.setDelay(d);
	}
	
	
	public void collectedGoldCoin() { numGoldCoins++; }
	public int numGoldCoins() { return numGoldCoins; }
	public int getTotalGoldCoins() { return totalGoldCoins; }
	public void setTotalGoldCoins(int i) { totalGoldCoins = i; }
	
	public void collectedSilverCoin() { numSilverCoins++; }
	public int numSilverCoins() { return numSilverCoins; }
	
	
	
	public void gotBoat() { hasBoat = true; tileMap.replace(22, 4); }
	public void gotWeapon() { hasWeapon = true; }
	public void gotKey() { hasKey = true; }
	public void gotVPotion() { hasVPotion = true; moveSpeed = moveSpeed+2; }
	public void gotHPotion() { hasHPotion = true; if (life2<= 50) life2= life2+50; }
	public boolean hasBoat() { return hasBoat; }
	public boolean hasVPotion() { return hasVPotion; }
	public boolean hasKey() { return hasKey; }
	public boolean hasWeapon() { return hasWeapon; }
	
	
	
	// Used to update time.
	public long getTicks() { return ticks; }
	
	// Keyboard input. Moves the player.
	public void setDown() {
		super.setDown();
	}
	public void setLeft() {
		super.setLeft();
	}
	public void setRight() {
		super.setRight();
	}
	public void setUp() {
		super.setUp();
	}
	
	public void shoot() {
		Bullet b = new Bullet(this.x + 5, this.y );
		bullets.add(b);
	}
	
	public static ArrayList getBullets() {
		return bullets;
	}

	
	public void update() {
		
	
		ticks++;
		
		
		if(tileMap.getIndex(ydest / tileSize, xdest / tileSize) == 4) {
			onWater = true;
		}
		else {
			onWater = false;
		}
		
		
		
		// set animation
		if(down) {
			 if(currentAnimation != DOWN) {
				setAnimation(DOWN, downSprites, 10);
				downfacing = true;
				upfacing = false;
				leftfacing = false;
				rightfacing = false;
			}
		}
		
		if(left) {
			 if(currentAnimation != LEFT) {
				setAnimation(LEFT, leftSprites, 10);
				downfacing = false;
				upfacing = false;
				leftfacing = true;
				rightfacing = false;
			}
		}
		
		if(right) {
			if(currentAnimation != RIGHT) {
				setAnimation(RIGHT, rightSprites, 10);
				downfacing = false;
				upfacing = false;
				leftfacing = false;
				rightfacing = true;
		} 
			}
			
		if(up) {
			 if(currentAnimation != UP) {
				setAnimation(UP, upSprites, 10);
				downfacing = false;
				upfacing = true;
				leftfacing = false;
				rightfacing = false;
			}
		}
		
		// update position
		super.update(); 
		
	}
	
	
	public void draw(Graphics2D g) {
		super.draw(g);	
	}

}