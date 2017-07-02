package AdventureLand.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import AdventureLand.Manager.Content;
import AdventureLand.TileMap.TileMap;


public class Switch extends Entity {
	
	// sprites
	private BufferedImage[] onSprite;
	private BufferedImage[] offSprite;

	
	public static boolean turned_on = false;
	public static boolean active = true;
	

	public Switch(TileMap tm) {
		
		super(tm);
		
		width = 16;
		height = 16;
		cwidth = 16;
		cheight = 16;
		
		offSprite = Content.SWITCH[0];
		onSprite = Content.SWITCH[1];
		
		animation.setFrames(offSprite);
		
	}
	
	public void update() {

		if (turned_on) {
			animation.setFrames(onSprite);
			active = false;
		}
		
		super.update(); 	
	}
	
	
	
	public void draw(Graphics2D g) {
		super.draw(g);	
	}

}