// The main menu GameState.

package AdventureLand.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import AdventureLand.Main.GameLoop;
import AdventureLand.Manager.AudioPlayer;
import AdventureLand.Manager.Content;
import AdventureLand.Manager.GameStateManager;
import AdventureLand.Manager.KeyControl;

public class MenuState extends GameState {
	
	private BufferedImage bg;
	private BufferedImage weapon;
	private BufferedImage menutitle;
	
	private AudioPlayer collect, menuoption;
	
	private int currentOption = 0;
	
	private String[] options = {"SINGLE PLAYER", "MULTIPLAYER", "EXIT"};
	
	int style = Font.PLAIN;
	Font font = new Font ("Garamond", style , 11);
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
	
		bg = Content.MENUBG[0][0];
		weapon = Content.WEAPON[0][0];
		menutitle = Content.MENU_TITLE[0][0];
		
		collect = new AudioPlayer("/SFX/collectitem.wav");
		menuoption = new AudioPlayer("/SFX/menuoption.wav");
		
	
	}
	
	public void update() {
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		
		//draw menu background & game title
		g.drawImage(bg, 0, 0, null);
		g.drawImage(menutitle, 200, 0, null);
	    
		//draw mmenu options
		Content.drawString(g, options[0], GameLoop.WIDTH/2-30, 90);
		Content.drawString(g, options[1], GameLoop.WIDTH/2-30, 110);
		Content.drawString(g, options[2], GameLoop.WIDTH/2-30, 130);
	  
		//copyright :)
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("© 2017 Team20", 525, 330);
		g.drawString("ALL RIGHTS RESERVED ", 500, 345);
		
		//selection cursor (weapon)
		if(currentOption == 0) g.drawImage(weapon, GameLoop.WIDTH/2-55, 86, null);
		else if(currentOption == 1) g.drawImage(weapon, GameLoop.WIDTH/2-55, 106, null);
		else if(currentOption == 2) g.drawImage(weapon, GameLoop.WIDTH/2-55, 126, null);
		
	}
	
	public void handleInput() {
		if(KeyControl.isPressed(KeyControl.DOWN) && currentOption < options.length - 1) {
			menuoption.play();
			currentOption++;
		}
		if(KeyControl.isPressed(KeyControl.UP) && currentOption > 0) {
			menuoption.play();
			currentOption--;
		}
		if(KeyControl.isPressed(KeyControl.ENTER)) {
			collect.play();
			selectOption();
		}
	}
	
	private void selectOption() {
		
		if(currentOption == 0) {
			gsm.setState(GameStateManager.PLAY);

		}
		if (currentOption ==1) {
			gsm.setState(GameStateManager.MULTIPLAYER);
		}

		if(currentOption == 2) {
			System.exit(0);;
		}
	}

}
