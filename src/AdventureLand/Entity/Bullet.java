package AdventureLand.Entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import AdventureLand.Main.GameLoop;
import AdventureLand.Manager.Content;
import AdventureLand.Manager.KeyControl;
import AdventureLand.TileMap.Tile;
import AdventureLand.TileMap.TileMap;


public class Bullet{

	protected static int cwidth = 16;
	protected static int cheight = 16;
	protected static int xb;
	protected static int yb;

	protected static int dx=4;
	protected static int dy=4;
	private static long ticks;
	private static boolean visible = false;

	public static BufferedImage[] sprites;
	
	public Bullet(int startX, int startY) {
		
		Bullet.xb = startX;
		Bullet.yb = startY;
		visible = true;
		sprites = Content.BULLET[0];
		
	}

	public long getTicks() { return ticks; }
	
	public static void update(){
		
		ticks++;

		if ((Player.hasWeapon && Player.rightfacing) || (Player2.hasWeapon && Player2.rightfacing)) 
		  {
			xb = xb+dx;	
			
			if (xb > GameLoop.WIDTH-20 || ticks > 30) { visible = false; ticks = 0; }
		}
		
		if ((Player.hasWeapon && Player.leftfacing) || (Player2.hasWeapon && Player2.leftfacing))
		  {
			xb = xb-dx;	
			if (xb < 16 || ticks > 30) { visible = false; ticks = 0; }
		}
		
		if ((Player.hasWeapon && Player.downfacing) || (Player2.hasWeapon && Player2.downfacing))
		  {
			yb = yb+dy;	
			if (yb > GameLoop.HEIGHT-20 || ticks > 30) { visible = false; ticks = 0; }
		}
	
		if ((Player.hasWeapon && Player.upfacing) || (Player2.hasWeapon && Player2.upfacing))
		  {
			yb = yb-dy;	
			if (yb < 20 || ticks > 30) { visible = false; ticks = 0; }
		}
	}
	
	public static Rectangle getRectangle() {
		return new Rectangle(xb, yb, cwidth, cheight);
	}
	
	public static int getXb() {
		return xb;
	}

	public static void setXb(int xb) {
		Bullet.xb = xb;
	}

	public static int getYb() {
		return yb;
	}

	public static void setYb(int yb) {
		Bullet.yb = yb;
	}

	public static int getDx() {
		return dx;
	}

	public static void setDx(int dx) {
		Bullet.dx = dx;
	}

	public static int getDy() {
		return dy;
	}

	public static void setDy(int dy) {
		Bullet.dy = dy;
	}

	public static boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		Bullet.visible = visible;
	}

	public static void drawBullet(Graphics2D g){
		g.fillOval(xb, yb, 3, 3);
	}
}
