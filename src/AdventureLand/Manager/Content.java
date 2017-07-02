// Loads and splits all sprites on start up.
// The sprites can easily be accessed as they are public and static

package AdventureLand.Manager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Content {
	
	public static BufferedImage[][] MENU_TITLE = load("/HUD/menutitle.png", 320, 64);
	public static BufferedImage[][] MENUBG = load("/HUD/bg_gray.jpg", 1000, 600);
	public static BufferedImage[][] PLAYER = load("/Sprites/player1.png", 24, 24);
	public static BufferedImage[][] PLAYER2 = load("/Sprites/player2.png", 24, 24);
	public static BufferedImage[][] ENEMY = load("/Sprites/spider_16p.png", 16, 16);
	public static BufferedImage[][] BOSS = load("/Sprites/boss.png", 64, 96);
	public static BufferedImage[][] BAR = load("/HUD/itembar.png", 256, 16);
	
	
	public static BufferedImage[][] GOLDCOIN = load("/Sprites/goldcoin.png", 16, 16);
	public static BufferedImage[][] SILVERCOIN = load("/Sprites/silvercoin.png", 16, 16);
	public static BufferedImage[][] SPARKLE = load("/Sprites/sparkle.gif", 16, 16);
	public static BufferedImage[][] ITEMS = load("/Sprites/items.png", 16, 16);
	public static BufferedImage[][] WEAPON = load("/Sprites/weapon20p.png", 20, 20);
	public static BufferedImage[][] BULLET = load("/Sprites/bullets.png", 16, 16);
	public static BufferedImage[][] TRAP = load("/Sprites/trap.png", 16, 64);
	public static BufferedImage[][] TELEPORT = load("/Sprites/teleportal.png", 16, 16);
	public static BufferedImage[][] SWITCH = load("/Sprites/switch.png", 16, 16);
	public static BufferedImage[][] V_POTION = load("/Sprites/vpotion16p.png", 16, 16);
	public static BufferedImage[][] H_POTION = load("/Sprites/hpotion16p.png", 16, 16);
	public static BufferedImage[][] font = load("/HUD/fontset.png", 8, 8);
	
	//load spritesheets
	public static BufferedImage[][] load(String s, int w, int h) {
		BufferedImage[][] ret;
		try {
			BufferedImage spritesheet = ImageIO.read(Content.class.getResourceAsStream(s));
			int width = spritesheet.getWidth() / w;
			int height = spritesheet.getHeight() / h;
			ret = new BufferedImage[height][width];
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
				}
			}
			return ret;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics.");
			System.exit(0);
		}
		return null;
	}
	
	//handle fontset
	public static void drawString(Graphics2D g, String s, int x, int y) {
		s = s.toUpperCase();
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == 47) c = 36; // slash
			if(c == 58) c = 37; // colon
			if(c == 32) c = 38; // space
			if(c >= 65 && c <= 90) c -= 65; // letters
			if(c >= 48 && c <= 57) c -= 22; // numbers
			int row = c / font[0].length;
			int col = c % font[0].length;
			g.drawImage(font[row][col], x + 8 * i, y, null);
		}
	}
	
}
