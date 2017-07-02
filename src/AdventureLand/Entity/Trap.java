// GoldCoin class.
// May contain a list of tileChanges.
// These tileChanges are used to modify
// the tile map upon collection.

package AdventureLand.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import AdventureLand.Manager.Content;
import AdventureLand.TileMap.TileMap;
public class Trap extends Entity {
	
	BufferedImage[] sprites;
	
	private ArrayList<int[]> tileChanges;
	
	public Trap(TileMap tm) {
		
		super(tm);
		
		width = 16;
		height = 64;
		cwidth = 16;
		cheight = 64;
		
		sprites = Content.TRAP[0];
		animation.setFrames(sprites);
		animation.setDelay(10);
		
		tileChanges = new ArrayList<int[]>();
		
	}
	
	public void addChange(int[] i) {
		tileChanges.add(i);
	}
	public ArrayList<int[]> getChanges() {
		return tileChanges;
	}
	
	public void update() {
		animation.update();
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
}
