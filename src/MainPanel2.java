import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainPanel2 extends JPanel implements KeyListener {

	private JLayeredPane layeredPane;
	
	ArrayList<Block> blocks = new ArrayList<Block>();
	ArrayList<Block> waterBlocks = new ArrayList<Block>();
	ArrayList<Block> blocksHighlighted = new ArrayList<Block>();
	ArrayList<Block> redEnemies = new ArrayList<Block>();
	ArrayList<Block> blueEnemies = new ArrayList<Block>();
	ArrayList<Block> greenEnemies = new ArrayList<Block>();
	ArrayList<Block> hearts = new ArrayList<Block>();
	ArrayList<Block> powerUps = new ArrayList<Block>();
	ArrayList<Block> dots = new ArrayList<Block>();
	ArrayList<Block> bonusPoints = new ArrayList<Block>();
	ArrayList<Block> bonusNegativePoints = new ArrayList<Block>();
	
	int counterForBonus = 0;
	boolean gameStarted = false;
	
	Integer[] rowsYenemies = { 70, 70 + 80, 70 + 80 + 80 };
	Integer[] rowsYpwrups = { 80, 80 + 80, 80 + 80 + 80 };
	Integer[] columnsXpwrups = { 20, 125, 230, 325, 430 };
	String[] pwrupImages = { "images/resized/gem-blue.png", "images/resized/gem-green.png",
			"images/resized/gem-orange.png" };
	File[] soundFiles = {new File("sounds/crunch.wav"), new File("sounds/footstep.wav"), new File("sounds/water-splash.wav")};
	Block player;
	Animate animate;
	Thread thread;
	JLabel labelPoints, labelStartGame;
	int score = 0;
	int lives = 5;
	Random random = new Random();

	// 101 x 171

	public MainPanel2() {
		for (int i = 0; i < 8; i++) {
			waterBlocks.add(new Block((i * 101 + 1), -60, 101, 171, "images/water-block.png")); // TODO: CUT THIS IMAGE
		}
		for (int i = 0; i < 8; i++) {
			blocks.add(new Block((i * 101 + 1), 20, 101, 171, "images/stone-block.png"));
		}
		for (int i = 0; i < 8; i++) {
			blocks.add(new Block((i * 101 + 1), 100, 101, 171, "images/stone-block.png"));
		}
		for (int i = 0; i < 8; i++) {
			blocks.add(new Block((i * 101 + 1), 180, 101, 171, "images/stone-block.png"));
		}
		for (int i = 0; i < 8; i++) {
			blocks.add(new Block((i * 101 + 1), 260, 101, 171, "images/grass-block.png"));
		}
		for (int i = 0; i < 8; i++) {
			blocks.add(new Block((i * 101 + 1), 340, 101, 171, "images/grass-block.png"));
		}
		for (int i = 0; i < 5; i++) {
			hearts.add(new Block((i * 30 + 1), -10, 30, 51, "images/Heart.png"));
		}
		
		
		player = new Block(220, 390, 66, 75, "images/resized/char-boy.png");
		redEnemies.add(new Block(-100, 70, 96, 65, "images/resized/enemy-bug-red.png"));
		blueEnemies.add(new Block(-100, 150, 96, 65, "images/resized/enemy-bug-blue.png"));
		greenEnemies.add(new Block(-100, 230, 96, 65, "images/resized/enemy-bug-green.png"));
		createDots();

		labelPoints = new JLabel();
		labelPoints.setFont(new Font("Serif", Font.BOLD, 20));
		add(labelPoints);
		
//		labelStartGame = new JLabel("PRESS ENTER TO START GAME");
//		labelStartGame.setFont(new Font("Serif", Font.BOLD, 30));
//		labelStartGame.setHorizontalAlignment(JLabel.CENTER);
//		add(labelStartGame);

		add(supersweetpanel());
		
		addKeyListener(this);
		setFocusable(true);
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g); // this will erase the screen and then reprint
		labelPoints.setText("POINTS: " + score);
		for (Block water : waterBlocks) {
			water.draw(g, this);
		}
		for (Block b : blocks) {
			b.draw(g, this);
		}
		for (Block bh : blocksHighlighted) {
			bh.draw(g, this);
		}
		for (Block pwrup : powerUps) {
			pwrup.draw(g, this);
		}
		for (Block red : redEnemies) {
			red.draw(g, this);
		}
		for (Block blue : blueEnemies) {
			blue.draw(g, this);
		}
		for (Block green : greenEnemies) {
			green.draw(g, this);
		}
		for (Block heart : hearts) {
			heart.draw(g, this);
		}
//	ACTIVATE IT TO SHOW THE DOTS (STARS)		
//		for (Block dot : dots) {
//			dot.draw(g, this);
//		}
		for (Block bonus : bonusPoints) {
			bonus.draw(g, this);
		}
		for (Block negative : bonusNegativePoints) {
			negative.draw(g, this);
		}

		player.draw(g, this);
	}

	public void update() {
		for (Block red : redEnemies) {
			red.x += 2;
			if (red.intersects(player)) {
				removeLive();
			}
		}
		for (Block blue : blueEnemies) {
			blue.x += 3;
			if (blue.intersects(player)) {
				removeLive();
			}
		}
		for (Block green : greenEnemies) {
			green.x += 4;
			if (green.intersects(player)) {
				removeLive();
			}
		}
		createRandomEnemies();
		createRandomPowerUps();
		destroyPowerUp();
		createHighlightedBlocks();
		bonusAllHighlightedBlocks();
		removePointsIfTouchWater();			
		
		repaint();
	}


	private void removePointsIfTouchWater() {
		for (Block water : waterBlocks) {
			if (water.intersects(player)) {
				score -= 30; 
				bonusNegativePoints.add(new Block(player.x, player.y, 66, 29, "images/resized/number-50.png")); //TODO: change this picture for red minus 30
				playsound2(soundFiles[2]);
				player.x = 220;
				player.y = 390;
				
				//TODO: add some effect to the negative points
			}
		}
		
		for (Block bonus : bonusNegativePoints) {
			bonus.y--;
			counterForBonus++;
		}
		
		if(counterForBonus==70) {
			bonusNegativePoints.removeAll(bonusNegativePoints);
			counterForBonus =0;
		}
	}


	private void bonusAllHighlightedBlocks() {
		if(blocksHighlighted.size() == 15) {
			createDots();
			blocksHighlighted.removeAll(blocksHighlighted);
			score += 50;
			bonusPoints.add(new Block(player.x, player.y, 66, 29, "images/resized/number-50.png")); 
				
		}
		
		for (Block bonus : bonusPoints) {
			bonus.y--;
			counterForBonus++;
		}
		
		if(counterForBonus==70) {
			bonusPoints.removeAll(bonusPoints);
			counterForBonus =0;
		}
	}


	private void destroyPowerUp() {
		int indexPowerUp = -1;
		for (Block pwrup : powerUps) {
			if (pwrup.intersects(player) && !pwrup.destroyed) {
				indexPowerUp = powerUps.indexOf(pwrup);
				playsound2(new File("sounds/ding.wav"));
				showScoreAnimation();
				this.score+=10;
				bonusPoints.add(new Block(player.x, player.y, 66, 29, "images/resized/number-10.png"));
			}
		}
		if (indexPowerUp != -1) powerUps.remove(indexPowerUp);
		indexPowerUp = -1;
		
		// old method. This just mark the powerup as destroyed but it doesnt remove it from arraylist
//		for (Block pwrup : powerUps) {
//			if (pwrup.intersects(player) && !pwrup.destroyed) {
//				pwrup.destroyed = true;
//				playsound2(new File("sounds/ding.wav"));
//				showScoreAnimation();
//				this.score+=10;
//			}
//		}
		
	}


	private void createHighlightedBlocks() {
		int indexOfDotToRemove = -1;
		 for(Block dot : dots) {
			 if(dot.intersects(player)) {
				 blocksHighlighted.add(new Block(dot.x-24, dot.y-10, 101, 81, "images/resized/stone-block-highlight.png"));
//				 bonusPoints.add(new Block(player.x, player.y, 66, 29, "images/resized/number-10.png"));
				 indexOfDotToRemove = dots.indexOf(dot);
			 }
		 }
		 if (indexOfDotToRemove != -1) dots.remove(indexOfDotToRemove);
		 indexOfDotToRemove = -1;
	}


	@Override
	public void keyPressed(KeyEvent e) {
if(gameStarted==false) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			gameStarted = true;
			System.out.println("Enter key pressed");
//			playsound2(new File("sounds/ding.wav"));
			playsound2(new File(""));
//			animate = new Animate(this);
			thread = new Thread(animate);
			thread.start();
		}
}
if(gameStarted==true) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT && player.x > 20) {
			player.x -= 101;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && player.x < (getWidth() - player.width) -20) {
			player.x += 101;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP && player.y > 0) {
			player.y -= 80;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN && player.y < 350) {
			player.y += 80;
		}
		if (e.getKeyCode() == KeyEvent.VK_1) {
		}
}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void createRandomEnemies() {
		int randomNumber = random.nextInt(200); // lowering this number make the game more difficult
		int randomRow;
		if (randomNumber == 1) {
			randomRow = random.nextInt(3);
			// System.out.println("The random number is: " + randomNumber);
			redEnemies.add(new Block(-100, rowsYenemies[randomRow], 96, 65, "images/resized/enemy-bug-red.png"));
		}
		if (randomNumber == 2) {
			randomRow = random.nextInt(3);
			// System.out.println("The random number is: " + randomNumber);
			blueEnemies.add(new Block(-100, rowsYenemies[randomRow], 96, 65, "images/resized/enemy-bug-blue.png"));
		}
		if (randomNumber == 3) {
			randomRow = random.nextInt(3);
			// System.out.println("The random number is: " + randomNumber);
			greenEnemies.add(new Block(-100, rowsYenemies[randomRow], 96, 65, "images/resized/enemy-bug-green.png"));
		}
	}

	public void createRandomPowerUps() {
		int randomNumber = random.nextInt(500); // lowering this number and it will appear powerups more frequently
		int randomRow;
		int randomColumn;
		int randomPowerUp;
		if (randomNumber == 4 && powerUps.size() < 1) {
			randomRow = random.nextInt(3);
			randomColumn = random.nextInt(5);
			randomPowerUp = random.nextInt(3);
			System.out.println(randomRow + " " + randomColumn);
			System.out.println("POWERUP CREATED");
			powerUps.add(new Block(columnsXpwrups[randomColumn], rowsYpwrups[randomRow], 54, 58,
					pwrupImages[randomPowerUp]));
		}

	}

	private void removeLive() {
		playsound2(soundFiles[0]);
		player.x = 220;
		player.y = 390;
		hearts.remove(hearts.size() - 1);
		lives--;
		if (lives == 0) {
			update();
			// TODO: message finish game
			String endGameText = "The game has finished!" + System.lineSeparator() + "Score = " + score;
			JOptionPane.showMessageDialog(this, endGameText, "THE END", JOptionPane.INFORMATION_MESSAGE);
			try {
				restartApplication();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void restartApplication() throws URISyntaxException, IOException {
		final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		final File currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

		/* is it a jar file? */
		if (!currentJar.getName().endsWith(".jar"))
			return;

		/* Build command: java -jar application.jar */
		final ArrayList<String> command = new ArrayList<String>();
		command.add(javaBin);
		command.add("-jar");
		command.add(currentJar.getPath());

		final ProcessBuilder builder = new ProcessBuilder(command);
		builder.start();
		System.exit(0);
	}

	static void playsound2(File soundFile) {
//	File Clap = new File(soundPath);
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(soundFile));
			clip.start();
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private void showScoreAnimation() {
		// TODO Auto-generated method stub
		
	}
	
	private void createDots() {
		for (int i = 0; i < 8; i++) {
			dots.add(new Block((i * 101 + 25), 80, 50, 50, "images/star.png"));
		}
		for (int i = 0; i < 8; i++) {
			dots.add(new Block((i * 101 + 25), 160, 50, 50, "images/star.png"));
		}
		for (int i = 0; i < 8; i++) {
			dots.add(new Block((i * 101 + 25), 240, 50, 50, "images/star.png"));
		}
	}
	
	public JPanel supersweetpanel() {
        JPanel sswp = new JPanel();
        Button testButton = new Button("TEST BUTTON");
        sswp.setLayout(new BorderLayout());
        sswp.add(testButton, BorderLayout.SOUTH);
//        sswp.add(this, BorderLayout.NORTH);
        return sswp;
    }
	
	
}
