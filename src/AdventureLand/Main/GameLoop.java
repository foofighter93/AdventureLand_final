package AdventureLand.Main;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import AdventureLand.Manager.GameStateManager;
import AdventureLand.Manager.KeyControl;

public class GameLoop extends JPanel implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	
	//dimensions
	public static final int WIDTH = 640;
	public static final int HEIGHT = 352;
	public static final int SCALE = 2;
	
	//loop variables
	private Thread thread;
	private boolean running;
	private final int FPS = 30;
	private final int TARGET_TIME = 1000 / FPS;
	
	
	//graphics
	public BufferedImage image, background;
	Graphics2D g;
	
	// game state manager
	private GameStateManager gsm;
	
	
	
	//constructor
	public GameLoop(){
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setFocusable(true);
		requestFocus();
	    }
	
	// ready to display
	public synchronized void addNotify() {
		super.addNotify();
		if(thread == null) {
			addKeyListener(this);
			
			
			
			new Thread(this).start();
			
		}
	}
	
		public void run() {
		
		init();
		long start;
		long elapsed;
		long wait;
		
		// game loop
		while(running) {
			
			start = System.nanoTime();
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = TARGET_TIME - elapsed / 1000000;
			if(wait < 0) wait = TARGET_TIME;
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}

    //initializes fields
	private void init() {
		running = true;
		image = new BufferedImage(WIDTH, HEIGHT, 1);
		g = (Graphics2D) image.getGraphics();
		gsm = new GameStateManager();
	}
		
	// updating the game
	private void update() {
		gsm.update();
		KeyControl.update();	
	}
	
	
	// draws game
	private void draw() {
		gsm.draw(g);
	
	
	}
	 
	
	// copy buffer to screen
		private void drawToScreen() {
			Graphics g2 = getGraphics();
			g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
			g2.dispose();
		}
		
		// key event
		public void keyTyped(KeyEvent key) {}
		public void keyPressed(KeyEvent key) {
			KeyControl.keySet(key.getKeyCode(), true);  
			int keyCode = key.getKeyCode();
			if (keyCode == KeyEvent.VK_SPACE) {
			
			}
		}
		
	
		public void keyReleased(KeyEvent key) {
			KeyControl.keySet(key.getKeyCode(), false);
			int keyCode = key.getKeyCode();
			if (keyCode == KeyEvent.VK_SPACE) {
				
			}
		}
}		
		
		
