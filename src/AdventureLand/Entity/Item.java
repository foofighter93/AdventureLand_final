// Possibly redundant subclass of Entity.
// There are two types of items: Axe and boat.
// Upon collection, informs the Player
// that the Player does indeed have the item.

package AdventureLand.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import AdventureLand.Manager.Content;
import AdventureLand.TileMap.TileMap;

public class Item extends Entity{
	
	private BufferedImage sprite;
	private int type;
	public static final int BOAT = 0;
	public static final int WEAPON = 1;
	public static final int KEY = 2;
	public static boolean cBoat = false;
	public static boolean cWeapon = false;
	public static boolean cKey = false;
	
	private JFrame frame = new JFrame();
	
	public Item(TileMap tm) {
		super(tm);
		type = -1;
		width = height = 16;
		cwidth = cheight = 16;
	}
	
	public void setType(int i) {
		type = i;
		if(type == BOAT) {
			sprite = Content.ITEMS[1][0];
		}
		else if(type == WEAPON) {
			sprite = Content.WEAPON[0][0];
		}
		else if(type == KEY) {
			sprite = Content.ITEMS[1][2];
		}
		
	}
	
	public void collected(Player p) {
		if(type == BOAT) {
			p.gotBoat();
			//messages
			/*cBoat = true;
			if(cBoat) {
				JOptionPane.showMessageDialog(frame,
					    "Passing through water is not a problem anymore!",
					    " ",
					    JOptionPane.PLAIN_MESSAGE);
			}
			cBoat = false;*/
		}
		if(type == WEAPON) {
			p.gotWeapon();
			/*cWeapon = true;
			if(cWeapon) {
				JOptionPane.showMessageDialog(frame,
					    "You got the power!",
					    " ",
					    JOptionPane.PLAIN_MESSAGE);
		}
			cWeapon = false; 
			p.setTilePosition(8, 26);*/
		}
		if(type == KEY) {
			p.gotKey();
			tileMap.setTile(1, 36, 1);
			/*cKey = true;
			if(cKey) {
				JOptionPane.showMessageDialog(frame,
					    "You may need this for some reason...",
					    " ",
					    JOptionPane.PLAIN_MESSAGE);
		}
			cKey = false;*/
		}
	}
	
	public void draw(Graphics2D g) {
		setMapPosition();
		g.drawImage(sprite, x + xmap - width / 2, y + ymap - height / 2, null);
	}
	
}
