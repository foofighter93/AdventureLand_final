// The main playing GameState.
// Contains everything you need for gameplay:
// Player, TileMap, Diamonds, etc.
// Updates and draws all game objects.

package AdventureLand.GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import AdventureLand.Manager.GameStateManager;
import AdventureLand.Entity.Boss;
import AdventureLand.Entity.Enemy;
import AdventureLand.Entity.GoldCoin;
import AdventureLand.Entity.HPotion;
import AdventureLand.Entity.Item;
import AdventureLand.Entity.Player;
import AdventureLand.Entity.Player2;
import AdventureLand.Entity.SilverCoin;
import AdventureLand.Entity.Sparkle;
import AdventureLand.Entity.Switch;
import AdventureLand.Entity.Teleport;
import AdventureLand.Entity.VPotion;
import AdventureLand.HUD.HudMulti;
import AdventureLand.Main.GameLoop;
import AdventureLand.Manager.AudioPlayer;
import AdventureLand.Manager.Data;
import AdventureLand.Manager.KeyControl;
import AdventureLand.TileMap.TileMap;
import AdventureLand.Entity.Bullet;

public class MultiPlayState extends GameState {
	
	// entities declaration
	private Player player;
	private Player2 player2;
	private Switch sw, sw2;
	private Teleport tp1, tp2;
	private ArrayList<Enemy> enemies;
	private ArrayList<Boss> boss;
	
	private boolean player1round = false;
	private boolean player2round = false;
	private boolean transfer = false;
	
	private AudioPlayer bgmusic, collect, tilechange, gunshot, teleport;
	
	// transfer options
	public Object[] options = {"Silver coins","Gold coins"};
	
	// tilemap
	private TileMap tileMap;
	
	// goldcoins
	private ArrayList<GoldCoin> goldcoins;
	
	// silvercoins
	private ArrayList<SilverCoin> silvercoins;
	
	// potions
	private ArrayList<VPotion> vpotions;
	private ArrayList<HPotion> hpotions;
	
	// items
	private ArrayList<Item> items;
	
	// sparkles
	private ArrayList<Sparkle> sparkles;
	
	// camera position
	private int xsector;
	private int ysector;
	private int sectorSize; 
	
	// hud
	private HudMulti hud;
	
	// events
	private boolean blockInput;
	private boolean eventStart;
	private boolean eventFinish;
	
	private int eventTick, musicloop, cnt;
	
	public static String player1Name;
	public static String player2Name;
	
	// transition box
	private ArrayList<Rectangle> boxes;
	
	//message window frame
	private JFrame frame = new JFrame();
	
	public MultiPlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		//player names input
		player1Name = JOptionPane.showInputDialog("Enter Player 1 name");
		player2Name = JOptionPane.showInputDialog("Enter Player 2 name");
		
		// create lists
		goldcoins = new ArrayList<GoldCoin>();
		silvercoins = new ArrayList<SilverCoin>();
		vpotions = new ArrayList<VPotion>();
		hpotions = new ArrayList<HPotion>();
		sparkles = new ArrayList<Sparkle>();
		items = new ArrayList<Item>();
		
		//load background music
		bgmusic = new AudioPlayer("/Music/bgmusic.mp3");
		bgmusic.play();
	
		cnt = 0;
		musicloop = 0;
		
		// load map
		tileMap = new TileMap(16);
		tileMap.loadTiles("/Tilesets/tilesets_new.png");
		tileMap.loadMap("/Maps/Terkep.map");
		
		// create entities
		player = new Player(tileMap);
		player2 = new Player2(tileMap);
        enemies = new ArrayList<Enemy>();
        boss = new ArrayList<Boss>();
        
        // create additional items on the map
        sw = new Switch(tileMap);
		sw2 = new Switch(tileMap);
		tp1 = new Teleport(tileMap);
		tp2 = new Teleport(tileMap);
		
		// fill lists
		populateGoldCoins();
		populateSilverCoins();
		populateItems();
		populateEnemies();
		
		// initialize player
		player.setTilePosition(1, 1);
		player.setTotalGoldCoins(goldcoins.size());
		
		player2.setTilePosition(1, 1);
		player2.setTotalGoldCoins(goldcoins.size());
		
		sw.setTilePosition(3, 38);
		sw2.setTilePosition(3, 4);
		tp1.setTilePosition(20, 1); 
		tp2.setTilePosition(20, 41);
		//initialize enemies

		// set up camera position
		sectorSize = GameLoop.WIDTH;
		xsector = player.getx() / sectorSize;
		ysector = player.gety() / sectorSize;
		xsector = player2.getx() / sectorSize;
		ysector = player2.gety() / sectorSize;
		tileMap.setPositionImmediately(-xsector * sectorSize, -ysector * sectorSize);
		
		// load hud
		hud = new HudMulti(player, player2, goldcoins);
		
		// start event
		boxes = new ArrayList<Rectangle>();
		eventStart = true;
		eventStart();
			
	}
	

	private void populateGoldCoins() {
		
		GoldCoin gc;
		
	    //1
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(1, 37);
		goldcoins.add(gc);
		
		//2
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(12, 36);
		goldcoins.add(gc);
		
		//3
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(5, 37);
		goldcoins.add(gc);
		
		//5
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(20, 38);
		goldcoins.add(gc);

		//6
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(5, 4);
		goldcoins.add(gc);
		
		//7
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(4, 1);
		goldcoins.add(gc);
		
		//8
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(6, 13);
		goldcoins.add(gc);
		
		//9
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(7, 13);
		goldcoins.add(gc);
		
		//10
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(3, 8);
		goldcoins.add(gc);
		
		//11
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(5, 8);
		goldcoins.add(gc);
		
		//12
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(7, 8);
		goldcoins.add(gc);
		
		//13
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(9, 8);
		goldcoins.add(gc);
		
		//14
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(11, 8);
		goldcoins.add(gc);
		
		//15
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(3, 11);
		goldcoins.add(gc);
		
		
		
		//level 2
		
		//1
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(3, 60);
		goldcoins.add(gc);
		
		//3
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(1, 42);
		goldcoins.add(gc);
		
		//4
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(5, 42);
		goldcoins.add(gc);
		
		//5
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(7, 42);
		goldcoins.add(gc);

		//6
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(9, 42);
		goldcoins.add(gc);
		
		//7
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(13, 41);
		goldcoins.add(gc);
		
		//8
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(15, 41);
		goldcoins.add(gc);
		
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(1, 77);
		goldcoins.add(gc);
		
		//9
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(17, 41);
		goldcoins.add(gc);
		
		//10
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(15, 78);
		goldcoins.add(gc);
		
		//13
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(3, 42);
		goldcoins.add(gc);
		
		//14
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(17, 78);
		goldcoins.add(gc);
		
		//15
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(13, 78);
		goldcoins.add(gc);
		
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(11, 78);
		goldcoins.add(gc);
		
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(3, 60);
		goldcoins.add(gc);
		
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(4, 54);
		goldcoins.add(gc);
		
		
		
		
	}
	
private void populateSilverCoins() {
		
		SilverCoin sc;
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(5, 5);
		silvercoins.add(sc);
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(7, 7);
		silvercoins.add(sc);
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(7, 9);
		silvercoins.add(sc);
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(7, 11);
		silvercoins.add(sc);
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(13, 13);
		silvercoins.add(sc);
		
		//level 2
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(4, 60);
		silvercoins.add(sc);
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(5, 60);
		silvercoins.add(sc);
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(6, 60);
		silvercoins.add(sc);
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(7, 60);
		silvercoins.add(sc);
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(8, 60);
		silvercoins.add(sc);
		
		
	
	}

	
private void populateEnemies() {
		
		Enemy en;
		Boss b;
	
		b = new Boss(tileMap);
		b.setTilePosition(5, 57);
		boss.add(b);
		
		en = new Enemy(tileMap);
		en.setTilePosition(3, 25);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(5, 23);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(7, 21);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(12, 25);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(13, 23);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(14, 21);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(12, 27);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(5, 29);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(5, 31);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(5, 33);
		enemies.add(en);
		
		
		en = new Enemy(tileMap);
		en.setTilePosition(9, 27);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(6, 1);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(12, 3);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(9, 3);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(6, 5);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(10, 7);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(6, 9);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(6, 11);
		enemies.add(en);
		
		//level2
		en = new Enemy(tileMap);
		en.setTilePosition(10, 49);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(10, 51);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(10, 53);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(10, 55);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(10, 57);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(10, 59);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(10, 61);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 42);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 44);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 46);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 48);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 50);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 65);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 67);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 69);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 71);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 73);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 78);
		enemies.add(en);
}
	
	private void populateItems() {
		
		Item item;
		VPotion vpotion;
		HPotion hpotion;
				
		item = new Item(tileMap);
		item.setType(Item.BOAT);
		item.setTilePosition(3, 1); 
		items.add(item);
		
		item = new Item(tileMap);
		item.setType(Item.WEAPON);
		item.setTilePosition(8,27);
		items.add(item);
		
		item = new Item(tileMap);
		item.setType(Item.KEY);
		item.setTilePosition(1,38);
		items.add(item);
		
		vpotion = new VPotion(tileMap);
		vpotion.setTilePosition(5, 38);
		vpotions.add(vpotion);
		
		hpotion = new HPotion(tileMap);
		hpotion.setTilePosition(20,37);
		hpotions.add(hpotion);

		
	}
	
	public void update() {
		eventTick++;
		
		cnt++;
		musicloop = cnt / 100;
		
		//looping the background music
		if (musicloop > 14 ) {
			bgmusic = new AudioPlayer("Music/bgmusic.mp3");
			bgmusic.play();
			cnt = 0;
		}
		
		

		//player1 moves
		if (eventTick == 1) JOptionPane.showMessageDialog(frame, player1Name+"'s round!");
		if (eventTick > 1 && eventTick<=500) {
			handleInput();
			//transfer coins to player1
			if ((player.getRow() == player2.getRow()) && (player.getCol() == player2.getCol()) && transfer) {
				collect = new AudioPlayer("/SFX/collectitem.wav");
				collect.play();
				int n = JOptionPane.showOptionDialog(null,
			            "Choose what do you want to transfer: ",
			            "Transfer",
			            JOptionPane.YES_NO_CANCEL_OPTION,
			            JOptionPane.DEFAULT_OPTION,
			            null,
			            options,
			            options[1]);  
			if (n == 0) 
				if (player2.numSilverCoins !=0) {
					player.numSilverCoins = player.numSilverCoins + player2.numSilverCoins;
					player2.numSilverCoins = 0;
				}
			if (n == 1) 
				if (player2.numGoldCoins !=0) {
					player.numGoldCoins = player.numGoldCoins + player2.numGoldCoins;
					player2.numGoldCoins = 0;
				}
			} 
			player1round = true;
			player2round = false;
			transfer = false;
		}
		
		//player2 moves
		if (eventTick == 500) JOptionPane.showMessageDialog(frame, player2Name+"'s round!");
		if (eventTick > 500 && eventTick<=1000) {
			handleInput2();
			//transfer coins to player1
			if ((player.getRow() == player2.getRow()) && (player.getCol() == player2.getCol()) && transfer) {
				collect = new AudioPlayer("/SFX/collectitem.wav");
				collect.play();
				int n = JOptionPane.showOptionDialog(null,
			            "Choose what do you want to transfer: ",
			            "Transfer",
			            JOptionPane.YES_NO_CANCEL_OPTION,
			            JOptionPane.DEFAULT_OPTION,
			            null,
			            options,
			            options[1]);  
			if (n == 0) 
				if (player2.numSilverCoins !=0) {
					player.numSilverCoins = player.numSilverCoins + player2.numSilverCoins;
					player2.numSilverCoins = 0;
				}
			if (n == 1) 
				if (player2.numGoldCoins !=0) {
					player.numGoldCoins = player.numGoldCoins + player2.numGoldCoins;
					player2.numGoldCoins = 0;
				}
					
			
			} 
			player1round = false;
			player2round = true;
			transfer = false;
		}
		
		//loop
		if (eventTick > 1000) eventTick = 0;
		
		// check events
		if(eventStart) eventStart();
		if(eventFinish || Player.life==0 || Player2.life2==0) eventFinish();
		
		ArrayList bullets = Player.getBullets();
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = (Bullet) bullets.get(i);
			if (Bullet.isVisible() == true) {
				Bullet.update();
			} else {
				bullets.remove(i);
			}
		}
		
		ArrayList bullets2 = Player2.getBullets();
		for (int i = 0; i < bullets2.size(); i++) {
			Bullet b2 = (Bullet) bullets2.get(i);
			if (Bullet.isVisible() == true) {
				Bullet.update();
			} else {
				bullets2.remove(i);
			}
		}
		
		if(player.numGoldCoins() == player.getTotalGoldCoins() || player2.numGoldCoins() == player2.getTotalGoldCoins()) {
			eventFinish = blockInput = true;
		}
		
		
		
		//if the player collects 5 silver coins he gets 1 gold coin
		if(player.numSilverCoins()==5) {
			player.collectedGoldCoin();
			player.numSilverCoins = 0;
		}
		
		if(player2.numSilverCoins()==5) {
			player2.collectedGoldCoin();
			player2.numSilverCoins = 0;
		}
		
		// update camera
		int oldxs = xsector;
		int oldys = ysector;
		xsector = player.getx() / sectorSize;
		ysector = player.gety() / sectorSize;
		xsector = player2.getx() / sectorSize;
		ysector = player2.gety() / sectorSize;
		tileMap.setPosition(-xsector * sectorSize, -ysector * sectorSize);
		tileMap.update();
		
		if(oldxs != xsector || oldys != ysector) {
			//mapmove
		}
		
		if(tileMap.isMoving()) return;
		
		// update player
		player.update();
		player2.update();
		
		
		if (player.hasKey() || player2.hasKey()) {
			tileMap.setTile(11, 39, 1);
		}
		
		if (player.intersects(tp1)){
			teleport = new AudioPlayer("/SFX/teleport.wav");
			teleport.play();
			player.setTilePosition(1, 37);
			tileMap.setTile(1, 35, 1);
			tp1.update();
			}
		
		if (player2.intersects(tp1)){
			teleport = new AudioPlayer("/SFX/teleport.wav");
			teleport.play();
			player2.setTilePosition(1, 37);
			tp1.update();
			}
		
		if (player.intersects(tp2)){
			teleport = new AudioPlayer("/SFX/teleport.wav");
			teleport.play();
			player.setTilePosition(1, 48);
			tp2.update();
			}
		
		if (player2.intersects(tp1)){
			teleport = new AudioPlayer("/SFX/teleport.wav");
			teleport.play();
			player2.setTilePosition(1, 48);
			tp2.update();
			}
		
		
        //when the switch is turned on
		if (player.intersects(sw) || player2.intersects(sw)){
			Switch.turned_on = true;
			tileMap.setTile(14, 15, 1);
			sw.update();
			if (sw.active == false) {
				tilechange = new AudioPlayer("/SFX/tilechange.wav");
			    tilechange.play(); }
			
		}
		
		if (player.intersects(sw2) || player2.intersects(sw2)){
			Switch.turned_on = true;
			tileMap.setTile(18, 5, 1);
			sw2.update();
			if (sw2.active == false) {
				tilechange = new AudioPlayer("/SFX/tilechange.wav");
			    tilechange.play(); }
		}
		
		for(int i = 0; i < enemies.size(); i++) {
			
			Enemy en = enemies.get(i);
			en.update();
			if (en.getRectangle().intersects(Bullet.getRectangle())) {
				enemies.remove(i);
				i--;
			}
			
			//if player has collision with enemies
			if(player.intersects(en))  {
				
				enemies.remove(i);
				Player.life = Player.life-50;
				i--; }
		 
			if(player2.intersects(en))  {
				enemies.remove(i);
				Player2.life2 = Player2.life2-50;
				i--; }
		}
		
			
		for(int i = 0; i < boss.size(); i++) {
			
			Boss b = boss.get(i);
			b.update();
			
			// player collects goldcoin
			if(player.intersects(b)) 
				if (player.hasWeapon()) {
				boss.remove(i);
				Player.life = 0;
				i--; 
				}
			
			if(player2.intersects(b)) 
				if (player2.hasWeapon()) {
				boss.remove(i);
				Player2.life2 = 0;
				i--; 
				}
		} 
		
		
		// update goldcoins
	for(int i = 0; i < goldcoins.size(); i++) {
			
			GoldCoin gc = goldcoins.get(i);
			gc.update();
			
			// player collects goldcoin
			if(player.intersects(gc)) {
				
				// remove from list
				goldcoins.remove(i);
				i--;
				
				// increment amount of collected goldcoins
				player.collectedGoldCoin();
				
				// play collect sound
				collect = new AudioPlayer("/SFX/collectitem.wav");
				collect.play();
				
				// add new sparkle
				Sparkle s = new Sparkle(tileMap);
				s.setPosition(gc.getx(), gc.gety());
				sparkles.add(s);		
			}
			
			if(player2.intersects(gc)) {
				
				// remove from list
				goldcoins.remove(i);
				i--;
				
				// increment amount of collected goldcoins
				player2.collectedGoldCoin();
				
				// play collect sound
				collect = new AudioPlayer("/SFX/collectitem.wav");
				collect.play();
				
				// add new sparkle
				Sparkle s = new Sparkle(tileMap);
				s.setPosition(gc.getx(), gc.gety());
				sparkles.add(s);		
			}
		}
		
	// update silvercoins
	for(int i = 0; i < silvercoins.size(); i++) {
			
			SilverCoin sc = silvercoins.get(i);
			sc.update();
			
			// player collects silvercoin
			if(player.intersects(sc)) {
				
				// remove from list
				silvercoins.remove(i);
				i--;
				
				// increment amount of collected silvercoins
				player.collectedSilverCoin();
				
				// play collect sound
				collect = new AudioPlayer("/SFX/collectitem.wav");
				collect.play();
				
				// add new sparkle
				Sparkle s = new Sparkle(tileMap);
				s.setPosition(sc.getx(), sc.gety());
				sparkles.add(s);
			}
			
			if(player2.intersects(sc)) {
				
				// remove from list
				silvercoins.remove(i);
				i--;
				
				// increment amount of collected silvercoins
				player2.collectedSilverCoin();
				
				// play collect sound
				collect = new AudioPlayer("/SFX/collectitem.wav");
				collect.play();
				
				// add new sparkle
				Sparkle s = new Sparkle(tileMap);
				s.setPosition(sc.getx(), sc.gety());
				sparkles.add(s);
			}
		}
	
for(int i = 0; i < vpotions.size(); i++) {
			
			VPotion vp = vpotions.get(i);
			vp.update();
			
			// player collects vpotion
			if(player.intersects(vp)) {
				
				// remove from list
				vpotions.remove(i);
				i--;
				player.gotVPotion();
				
				
				
				// play collect sound
				collect = new AudioPlayer("/SFX/collectitem.wav");
				collect.play();
				
				// add new sparkle
				Sparkle s = new Sparkle(tileMap);
				s.setPosition(vp.getx(), vp.gety());
				sparkles.add(s);	
			}
			
			if(player2.intersects(vp)) {
				
				// remove from list
				vpotions.remove(i);
				i--;
				player2.gotVPotion();
				
				
				
				// play collect sound
				collect = new AudioPlayer("/SFX/collectitem.wav");
				collect.play();
				
				// add new sparkle
				Sparkle s = new Sparkle(tileMap);
				s.setPosition(vp.getx(), vp.gety());
				sparkles.add(s);	
			}
		}

		

      for(int i = 0; i < hpotions.size(); i++) {
	
      HPotion hp = hpotions.get(i);
      hp.update();
	
	  // player collects hpotion
      if(player.intersects(hp)) {
	    hpotions.remove(i);
	    i--;
	    player.gotHPotion();
	    
	    collect = new AudioPlayer("/SFX/collectitem.wav");
		collect.play();

        // add new sparkle
		Sparkle s = new Sparkle(tileMap);
		s.setPosition(hp.getx(), hp.gety());
		sparkles.add(s);	
       }
      
      if(player2.intersects(hp)) {
  	    hpotions.remove(i);
  	    i--;
  	    player2.gotHPotion();
  	    
  	  collect = new AudioPlayer("/SFX/collectitem.wav");
		collect.play();

          // add new sparkle
  		Sparkle s = new Sparkle(tileMap);
  		s.setPosition(hp.getx(), hp.gety());
  		sparkles.add(s);	
         }
      }
		
		// update sparkles
		for(int i = 0; i < sparkles.size(); i++) {
			Sparkle s = sparkles.get(i);
			s.update();
			if(s.shouldRemove()) {
				sparkles.remove(i);
				i--;
			}
		}
		
		// update items
		for(int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			if(player.intersects(item)) {
				items.remove(i);
				i--;
				item.collected(player);
				collect = new AudioPlayer("/SFX/collectitem.wav");
				collect.play();
				Sparkle s = new Sparkle(tileMap);
				s.setPosition(item.getx(), item.gety());
				sparkles.add(s);
			}
			
			if(player2.intersects(item)) {
				items.remove(i);
				i--;
				item.collected(player2);
				collect = new AudioPlayer("/SFX/collectitem.wav");
				collect.play();
				Sparkle s = new Sparkle(tileMap);
				s.setPosition(item.getx(), item.gety());
				sparkles.add(s);
			}
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		player2.draw(g);
	
		ArrayList bullets = Player.getBullets();
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b= (Bullet) bullets.get(i);
			g.setColor(Color.GRAY);
			g.fillOval(Bullet.getXb()-6, Bullet.getYb(), 3, 3);
		}
		
		ArrayList bullets2 = Player2.getBullets();
		for (int i = 0; i < bullets2.size(); i++) {
			Bullet b2= (Bullet) bullets2.get(i);
			g.setColor(Color.GRAY);
			g.fillOval(Bullet.getXb()-6, Bullet.getYb(), 3, 3);
		}
	
		
		//draw enemies
		for(Enemy e : enemies) {
			e.draw(g);
		}
		
		for(Boss b : boss) {
			b.draw(g);
		}
		
		// draw goldcoins
		for(GoldCoin gc : goldcoins) {
			gc.draw(g);
		}
		
		// draw silvercoins
		for(SilverCoin sc : silvercoins) {  
			sc.draw(g); 
			}
		
		for(VPotion vp : vpotions) { 
			vp.draw(g); 
			}
		
		for(HPotion hp : hpotions) {
			hp.draw(g);
			}
		
		// draw sparkles
		for(Sparkle s : sparkles) {
			s.draw(g);
		}
		
		// draw items
		for(Item i : items) {
			i.draw(g);
		}
		
		sw.draw(g);
		sw2.draw(g);
		tp1.draw(g);
		tp2.draw(g);
		// draw hud
		hud.draw(g);
		
		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for(int i = 0; i < boxes.size(); i++) {
			g.fill(boxes.get(i));
		}
		
	}
	
	public void handleInput() {
		if(KeyControl.isPressed(KeyControl.ESCAPE)) {
			gsm.setPaused(true);
		}
		if(blockInput) return;
		if(KeyControl.isDown(KeyControl.LEFT)) player.setLeft(); 
		if(KeyControl.isDown(KeyControl.RIGHT)) player.setRight();
		if(KeyControl.isDown(KeyControl.UP)) player.setUp();
		if(KeyControl.isDown(KeyControl.DOWN)) player.setDown();
		if(KeyControl.isPressed(KeyControl.SPACE))  if (player.hasWeapon()) { 
			player.shoot(); 
			gunshot = new AudioPlayer("/SFX/gunshot.wav");
			gunshot.play(); 
			}
		if(KeyControl.isPressed(KeyControl.TRANSFER))  transfer = true;  
		
	}
	   
	public void handleInput2() {
		if(KeyControl.isPressed(KeyControl.ESCAPE)) {
			gsm.setPaused(true);
		}
		if(blockInput) return;
		
		if(KeyControl.isDown(KeyControl.LEFT2)) player2.setLeft(); 
		if(KeyControl.isDown(KeyControl.RIGHT2)) player2.setRight();
		if(KeyControl.isDown(KeyControl.UP2)) player2.setUp();
		if(KeyControl.isDown(KeyControl.DOWN2)) player2.setDown();
		if(KeyControl.isPressed(KeyControl.SHOOT2))  if (player2.hasWeapon()) { 
			player2.shoot(); 
			gunshot = new AudioPlayer("/SFX/gunshot.wav");
			gunshot.play(); 
			
	}
		if(KeyControl.isPressed(KeyControl.TRANSFER))  transfer = true; 
		
	}
	
	//===============================================
	
	private void eventStart() {
		eventTick++;
		if(eventTick == 1) {
			boxes.clear();
			for(int i = 0; i < 22; i++) {
				boxes.add(new Rectangle(0, i * 16, GameLoop.WIDTH, 16));
			}
		}
		if(eventTick > 1 && eventTick < 32) {
			for(int i = 0; i < boxes.size(); i++) {
				Rectangle r = boxes.get(i);
				if(i % 2 == 0) {
					r.x -= 4;
				}
				else {
					r.x += 4;
				}
			}
		}
		if(eventTick == 33) {
			boxes.clear();
			eventStart = false;
			eventTick = 0;
		}
	}
	
	private void eventFinish() {
		eventTick++;	
		
		if(eventTick == 1) {
			boxes.clear();
			for(int i = 0; i < 9; i++) {
				if(i % 2 == 0) boxes.add(new Rectangle(-128, i * 16, GameLoop.WIDTH, 16));
				else boxes.add(new Rectangle(128, i * 16, GameLoop.WIDTH, 16));
			}
				
		}
		
		if(eventTick > 1) {
			for(int i = 0; i < boxes.size(); i++) {
				Rectangle r = boxes.get(i);
				if(i % 2 == 0) {
					if(r.x < 0) r.x += 4;
				}
				else {
					if(r.x > 0) r.x -= 4;
				}
			}
		
		}
		if(eventTick > 33) {
				Data.setTime(player.getTicks());
				gsm.setState(GameStateManager.GAMEOVER);
		}
	}

}
