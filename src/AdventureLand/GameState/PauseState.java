// The pause GameState can only be activated

package AdventureLand.GameState;

import java.awt.Graphics2D;

import AdventureLand.Manager.Content;
import AdventureLand.Manager.GameStateManager;
import AdventureLand.Manager.KeyControl;

public class PauseState extends GameState {
	
	
	public PauseState(GameStateManager gsm) {
		super(gsm);
		
	}
	
	public void init() { 
	}
	
	public void update() {
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		
		Content.drawString(g, "paused", 40, 30);
		
		Content.drawString(g, "arrow", 12, 76);
		Content.drawString(g, "keys", 16, 84);
		Content.drawString(g, ": move", 52, 80);
		
		Content.drawString(g, "space", 12, 96);
		Content.drawString(g, ": fire", 52, 96);
		
		Content.drawString(g, "F1:", 36, 112);
		Content.drawString(g, "return", 68, 108);
		Content.drawString(g, "to menu", 68, 116);
		
		Content.drawString(g, "transfer coins: T", 12, 130);
		
	}
	public void handleInput() {
		if(KeyControl.isPressed(KeyControl.ESCAPE)) {
			gsm.setPaused(false);
			
			
		}
		if(KeyControl.isPressed(KeyControl.F1)) {
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENU);
		}
	}
	
}
