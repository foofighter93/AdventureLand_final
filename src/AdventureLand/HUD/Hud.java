// Contains a reference to the Player.
// Draws all relevant information at the
// bottom of the screen.

package AdventureLand.HUD;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import AdventureLand.Entity.GoldCoin;
import AdventureLand.Entity.Player;
import AdventureLand.GameState.PlayState;
import AdventureLand.Main.GameLoop;
import AdventureLand.Manager.Content;

public class Hud {
	
	private int yoffset;
	
	private BufferedImage bar;
	private BufferedImage goldcoin;
	private BufferedImage boat;
	private BufferedImage weapon;
	private BufferedImage key;
	
	private Player player;
	
	private int numGoldCoins;
	
	private Font font;
	private Color textColor; 
	
	public Hud(Player p, ArrayList<GoldCoin> gc) {
		
		player = p;
		numGoldCoins = gc.size();
		yoffset = GameLoop.HEIGHT;
		
		bar = Content.BAR[0][0];
		goldcoin = Content.GOLDCOIN[0][0];
		boat = Content.ITEMS[0][0];
		weapon = Content.ITEMS[0][1];
		key = Content.ITEMS[0][2];
		
		
		
		font = new Font("Arial", Font.PLAIN, 10);
		textColor = new Color(47, 64, 126);
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw hud
		g.drawImage(bar, 64, 0, null);
		
		//draw player name
		Content.drawString(g, PlayState.player1Name, 74, 4);
		
		// draw coin bar
		g.setColor(Color.YELLOW);
		g.fillRect(136, 6, (int)(28.0 * player.numGoldCoins() / numGoldCoins), 4);
		
		// draw health bar
		
		if (Player.life == 100) {
		g.setColor(Color.RED);
		g.fillRect(231, 6, 28, 4); }
		
		if (Player.life==75) {
			g.setColor(Color.RED);
			g.fillRect(231, 6, 21, 4); }
		
		if (Player.life==50) {
			g.setColor(Color.RED);
			g.fillRect(231, 6, 14, 4); }
		
		if (Player.life==25) {
			g.setColor(Color.RED);
			g.fillRect(231, 6, 7, 4); }
		
		// draw goldcoin amount
		g.setColor(textColor);
		g.setFont(font);
		String s = player.numGoldCoins() + "/" + numGoldCoins;
		Content.drawString(g, s, 169, 4);
		if(player.numGoldCoins() >= 10) g.drawImage(goldcoin, 80, yoffset, null);
		else g.drawImage(goldcoin, 72, yoffset, null);
		
		// draw items
		if(player.hasBoat()) g.drawImage(boat, 279, 0, null);
		if(player.hasWeapon()) g.drawImage(weapon, 291, 0, null);
		if(player.hasKey()) g.drawImage(key, 303, 0, null);


		
		// draw time
		int minutes = (int) (player.getTicks() / 1800);
		int seconds = (int) ((player.getTicks() / 30) % 60);
		
		if(minutes < 10) {
			if(seconds < 10) Content.drawString(g, "0" + minutes + ":0" + seconds, 15, 3);
			else Content.drawString(g, "0" + minutes + ":" + seconds, 15, 3);
		}
		else {
			if(seconds < 10) Content.drawString(g, minutes + ":0" + seconds, 15, 3);
			else Content.drawString(g, minutes + ":" + seconds, 15, 3);
		}
			
	}
	
}
