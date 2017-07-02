// Gives you a rank based on how long it took
// you to finish the game

package AdventureLand.GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import AdventureLand.Main.GameLoop;
import AdventureLand.Manager.AudioPlayer;
import AdventureLand.Manager.Content;
import AdventureLand.Manager.Data;
//import com.neet.DiamondHunter.Manager.JukeBox;
import AdventureLand.Manager.KeyControl;
import AdventureLand.Manager.GameStateManager;

public class GameOverState extends GameState {
	
	private Color color;
	
	private int rank;
	private long ticks;
	private AudioPlayer finishmusic;
	
	public GameOverState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		finishmusic = new AudioPlayer("/Music/finish.mp3");
		finishmusic.play();
		color = new Color(120, 100, 150);
		ticks = Data.getTime();
		if(ticks < 3600) rank = 1;
		else if(ticks < 5400) rank = 2;
		else if(ticks < 7200) rank = 3;
		else rank = 4;
	}
	
	public void update() { handleInput();}
	
	public void draw(Graphics2D g) {
		
		g.setColor(color);
		g.fillRect(0, 0, GameLoop.WIDTH, GameLoop.HEIGHT);
		
		Content.drawString(g, "finish time", 20, 36);
		
		int minutes = (int) (ticks / 1800);
		int seconds = (int) ((ticks / 30) % 60);
		if(minutes < 10) {
			if(seconds < 10) Content.drawString(g, "0" + minutes + ":0" + seconds, 44, 48);
			else Content.drawString(g, "0" + minutes + ":" + seconds, 44, 48);
		}
		else {
			if(seconds < 10) Content.drawString(g, minutes + ":0" + seconds, 44, 48);
			else Content.drawString(g, minutes + ":" + seconds, 44, 48);
		}
		
		Content.drawString(g, "rank", 48, 66);
		if(rank == 1) Content.drawString(g, "indiana jones", 20, 78);
		else if(rank == 2) Content.drawString(g, "adventurer", 24, 78);
		else if(rank == 3) Content.drawString(g, "beginner", 32, 78);
		else if(rank == 4) Content.drawString(g, "slow idiot ", 8, 78);
		
		Content.drawString(g, "press any key ", 16, 110);
		
	}
	
	public void handleInput() {
		if(KeyControl.anyKeyPress()) {
			gsm.setState(GameStateManager.MENU);
			
		}
	}
	
}