package AdventureLand.Entity;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.Random;

import AdventureLand.Manager.Content;
import AdventureLand.TileMap.TileMap;


public class Enemy extends Entity {
	
	// sprites
	private BufferedImage[] sprites;

	private long ticks;
	
	public Enemy(TileMap tm) {
		
		super(tm);
		
		width = 16;
		height = 16;
		cwidth = 16;
		cheight = 16;
		
		sprites = Content.ENEMY[1];
	
		animation.setFrames(sprites);
		animation.setDelay(5);
	}
	
	
	/*
	private void setAnimation(int i, BufferedImage[] bi, int d) {
		currentAnimation = i;
		animation.setFrames(bi);
		animation.setDelay(d);
	} */
	

	// Used to update time.
	public long getTicks() { return ticks; }
	
	public void setDown() {
		super.setDown();
		

	}
	public void setLeft() {
		super.setLeft();

	}
	public void setRight() {
	
	}
	public void setUp() {
		super.setUp();
	}
	
	public void update() {
		
		ticks++;
		
		//generate random numbers for moving speed
		Random rn = new Random();

		for(int i =7; i < 20; i++) {
		    moveSpeed = rn.nextInt(20) + 1;
		}
		
		if (ticks <= 80) {
			
			setDown();  
			}
		
		if (ticks>80 && ticks<160) {
			setUp();
		}
			
		if (ticks == 160) ticks = 0;
	   
		//update position
		super.update();
	
	}
	
	
	public void draw(Graphics2D g) {
		super.draw(g);	
	}

}