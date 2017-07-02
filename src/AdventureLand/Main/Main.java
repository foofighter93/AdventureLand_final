package AdventureLand.Main;
//the main game

import javax.swing.JFrame;

import AdventureLand.Main.GameLoop;;


public class Main extends GameLoop {
	
	private static final long serialVersionUID = 1L;
	public static final String gameName= "ADVENTURE LAND";
	
	public static void main(String[] args){
	
		JFrame window = new JFrame(gameName);
		
		window.add(new GameLoop());
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		}
}
		
		
	

