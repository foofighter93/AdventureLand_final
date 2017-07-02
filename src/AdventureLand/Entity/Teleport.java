package AdventureLand.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;



import AdventureLand.Manager.Content;
import AdventureLand.TileMap.TileMap;


public class Teleport extends Entity {
	
	private BufferedImage[] sprite;

	
	public Teleport(TileMap tm) {
		
		super(tm);
		
		width = 16;
		height = 16;
		cwidth = 16;
		cheight = 16;
		
		sprite = Content.TELEPORT[0];
		
		animation.setFrames(sprite);
		animation.setDelay(10);
		
	}

	

	public void update() {

	
	    animation.update();
		// update position
		super.update(); 
		
	}
	
	

	
	public void draw(Graphics2D g) {
		super.draw(g);	
	}

}