//state which shows an animated logo

package AdventureLand.GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import AdventureLand.Main.GameLoop;
import AdventureLand.Manager.GameStateManager;
import AdventureLand.Manager.KeyControl;

public class IntroState extends GameState{

	private BufferedImage logo;
	
	private int alpha;
	private int ticks;
	
	private final int FADE_IN = 60;
	private final int LENGTH = 60;
	private final int FADE_OUT = 60;
	
	public IntroState(GameStateManager gsm) {
		super(gsm);
	}
	
	//loading the logo image
	public void init() {
		ticks = 0;
		try {
			logo = ImageIO.read(getClass().getResourceAsStream("/IntroLogo/intro_logo.gif"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		handleInput();
		ticks++;
		if(ticks < FADE_IN) {
			alpha = (int) (255 - 255 * (1.0 * ticks / FADE_IN));
			if(alpha < 0) alpha = 0;
		}
		if(ticks > FADE_IN + LENGTH) {
			alpha = (int) (255 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT);
			if(alpha > 255) alpha = 255;
		}
		if(ticks > FADE_IN + LENGTH + FADE_OUT) {
			gsm.setState(GameStateManager.MENU);
		}
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GameLoop.WIDTH, GameLoop.HEIGHT);
		g.drawImage(logo, 0, 0, GameLoop.WIDTH, GameLoop.HEIGHT, null);
		g.setColor(new Color(0, 0, 0, alpha));
		g.fillRect(0, 0, GameLoop.WIDTH, GameLoop.HEIGHT);
	}
	
	//if Enter key is pressed then go to the Menu State
	public void handleInput() {
		if(KeyControl.isPressed(KeyControl.ENTER)) {
			gsm.setState(GameStateManager.MENU);
		}
	}
}
