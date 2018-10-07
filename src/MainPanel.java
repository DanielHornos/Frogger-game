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
import java.nio.channels.SelectableChannel;
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
import javax.swing.SwingUtilities;

public class MainPanel extends JPanel implements KeyListener {

	ArrayList<Block> blocks = new ArrayList<Block>();
	ArrayList<Block> waterBlocks = new ArrayList<Block>();
	ArrayList<Block> blocksHighlighted = new ArrayList<Block>();
	ArrayList<Block> redEnemies = new ArrayList<Block>();
	ArrayList<Block> blueEnemies = new ArrayList<Block>();
	ArrayList<Block> greenEnemies = new ArrayList<Block>();
	ArrayList<Block> hearts = new ArrayList<Block>();
	ArrayList<Block> powerUps = new ArrayList<Block>();
	ArrayList<Block> dots = new ArrayList<Block>();
	ArrayList<Block> waterDots = new ArrayList<Block>();
	ArrayList<Block> bonusPoints = new ArrayList<Block>();
	ArrayList<Block> bonusNegativePoints = new ArrayList<Block>();

	int counterForBonus = 0;
	boolean gameStarted = false;
	int gamestrt = 0;

	Integer[] rowsYenemies = { 70, 70 + 80, 70 + 80 + 80 };
	Integer[] rowsYpwrups = { 80, 80 + 80, 80 + 80 + 80 };
	Integer[] columnsXpwrups = { 20, 125, 230, 325, 430 };
	String[] pwrupImages = { "images/resized/gem-blue.png", "images/resized/gem-green.png",
			"images/resized/gem-orange.png" };
	File[] soundFiles = { new File("sounds/crunch.wav"), new File("sounds/footstep.wav"),
			new File("sounds/water-splash.wav") };
	Block player;
	Animate animate;
	Thread thread;
	JLabel labelPoints, labelStartGame;
	int score = 0;
	int lives = 5;
	Random random = new Random();

	String characterSkin;
	int lvl;

	// public MainPanel() {
	// for (int i = 0; i < 8; i++) {
	// waterBlocks.add(new Block((i * 101 + 1), -60, 101, 171,
	// "images/water-block.png"));
	// }
	// for (int i = 0; i < 8; i++) {
	// blocks.add(new Block((i * 101 + 1), 20, 101, 171, "images/stone-block.png"));
	// }
	// for (int i = 0; i < 8; i++) {
	// blocks.add(new Block((i * 101 + 1), 100, 101, 171,
	// "images/stone-block.png"));
	// }
	// for (int i = 0; i < 8; i++) {
	// blocks.add(new Block((i * 101 + 1), 180, 101, 171,
	// "images/stone-block.png"));
	// }
	// for (int i = 0; i < 8; i++) {
	// blocks.add(new Block((i * 101 + 1), 260, 101, 171,
	// "images/grass-block.png"));
	// }
	// for (int i = 0; i < 8; i++) {
	// blocks.add(new Block((i * 101 + 1), 340, 101, 171,
	// "images/grass-block.png"));
	// }
	// for (int i = 0; i < 5; i++) {
	// hearts.add(new Block((i * 30 + 1), -10, 30, 51, "images/Heart.png"));
	// }
	//
	//
	// player = new Block(220, 390, 66, 75, "images/resized/char-boy.png");
	// redEnemies.add(new Block(-100, 70, 96, 65,
	// "images/resized/enemy-bug-red.png"));
	// blueEnemies.add(new Block(-100, 150, 96, 65,
	// "images/resized/enemy-bug-blue.png"));
	// greenEnemies.add(new Block(-100, 230, 96, 65,
	// "images/resized/enemy-bug-green.png"));
	// createDots();
	// createWaterDots();
	//
	// labelPoints = new JLabel();
	// labelPoints.setFont(new Font("Serif", Font.BOLD, 20));
	// add(labelPoints);
	//
	// addKeyListener(this);
	// setFocusable(true);
	//
	// }

	public MainPanel(String character, String level) {
		selectedLevel(level);
		for (int i = 0; i < 8; i++) {
			waterBlocks.add(new Block((i * 101 + 1), -60, 101, 171, "images/water-block.png"));
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

		player = new Block(220, 390, 66, 75, character);
		redEnemies.add(new Block(-100, 70, 96, 65, "images/resized/enemy-bug-red.png"));
		blueEnemies.add(new Block(-100, 150, 96, 65, "images/resized/enemy-bug-blue.png"));
		greenEnemies.add(new Block(-100, 230, 96, 65, "images/resized/enemy-bug-green.png"));
		createDots();
		createWaterDots();

		labelPoints = new JLabel();
		labelPoints.setFont(new Font("Serif", Font.BOLD, 20));
		add(labelPoints);

		addKeyListener(this);
		setFocusable(true);
		// requestFocusInWindow();

	}

	private void selectedLevel(String level) {
		System.out.println("The selected level is:" + level);
		switch (level) {
		case "Easy":
			lvl = 1;
			break;
		case "Medium":
			lvl = 2;
			break;
		case "Difficult":
			lvl = 3;
			break;
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); // this will erase the screen and then reprint

		if (gamestrt == 0) {
			playsound2(new File(""));
			animate = new Animate(this);
			thread = new Thread(animate);
			thread.start();
			gamestrt = 1;
		}

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
		// ACTIVATE IT TO SHOW THE DOTS (STARS)
		// for (Block dot : dots) {
		// dot.draw(g, this);
		// }
		// for (Block dot : waterDots) {
		// dot.draw(g, this);
		// }
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
			red.x += lvl;
			if (red.intersects(player)) {
				removeLive();
			}
		}
		for (Block blue : blueEnemies) {
			blue.x += 1 + lvl;
			if (blue.intersects(player)) {
				removeLive();
			}
		}
		for (Block green : greenEnemies) {
			green.x += 2 + lvl;
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
		for (Block water : waterDots) {
			if (water.intersects(player)) {
				score -= 30;
				bonusNegativePoints.add(new Block(player.x, player.y, 66, 29, "images/resized/number-minus30.png")); // TODO:
																														// change
																														// this
																														// picture
																														// for
																														// red
																														// minus
																														// 30
				playsound2(soundFiles[2]);
				player.x = 220;
				player.y = 390;

				// TODO: add some effect to the negative points
			}
		}

		for (Block bonus : bonusNegativePoints) {
			bonus.y++;
			counterForBonus++;
		}

		if (counterForBonus == 70) {
			bonusNegativePoints.removeAll(bonusNegativePoints);
			counterForBonus = 0;
		}
	}

	private void bonusAllHighlightedBlocks() {
		if (blocksHighlighted.size() == 15) {
			createDots();
			blocksHighlighted.removeAll(blocksHighlighted);
			score += 50;
			bonusPoints.add(new Block(player.x, player.y, 66, 29, "images/resized/number-50.png"));

		}

		for (Block bonus : bonusPoints) {
			bonus.y--;
			counterForBonus++;
		}

		if (counterForBonus == 70) {
			bonusPoints.removeAll(bonusPoints);
			counterForBonus = 0;
		}
	}

	private void destroyPowerUp() {
		int indexPowerUp = -1;
		for (Block pwrup : powerUps) {
			if (pwrup.intersects(player) && !pwrup.destroyed) {
				indexPowerUp = powerUps.indexOf(pwrup);
				playsound2(new File("sounds/ding.wav"));
				showScoreAnimation();
				this.score += 10;
				bonusPoints.add(new Block(player.x, player.y, 66, 29, "images/resized/number-10.png"));
			}
		}
		if (indexPowerUp != -1)
			powerUps.remove(indexPowerUp);
		indexPowerUp = -1;
	}

	private void createHighlightedBlocks() {
		int indexOfDotToRemove = -1;
		for (Block dot : dots) {
			if (dot.intersects(player)) {
				blocksHighlighted
						.add(new Block(dot.x - 24, dot.y - 10, 101, 81, "images/resized/stone-block-highlight.png"));
				// bonusPoints.add(new Block(player.x, player.y, 66, 29,
				// "images/resized/number-10.png"));
				indexOfDotToRemove = dots.indexOf(dot);
			}
		}
		if (indexOfDotToRemove != -1)
			dots.remove(indexOfDotToRemove);
		indexOfDotToRemove = -1;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// if(gameStarted==false) {
		// if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		// gameStarted = true;
		// System.out.println("Enter key pressed");
		// animate = new Animate(this);
		// thread = new Thread(animate);
		// thread.start();
		// }
		// }

		// if(gameStarted==false) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT && player.x > 20) {
			player.x -= 101;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && player.x < (getWidth() - player.width) - 20) {
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
		// }
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

		int randomNumber = random.nextInt(200 / lvl); // lowering this number make the game more difficult
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
			String endGameText = "The game has finished!" + System.lineSeparator() + "Score = " + score
					+ System.lineSeparator() + "Do you want to play again??";

			// TODO: check in readFromFile
			CheckPositionOfScore obj = new CheckPositionOfScore(score);
			int userPositionInTable = obj.userPositionInTable;
			System.out.println("===================================the user position in table is:" + userPositionInTable);
			CheckPositionOfScore objNames = new CheckPositionOfScore();

			if (userPositionInTable <= 10) {
				System.out.println("Estas en el top 10");

				pleaseIntroduceYourName(userPositionInTable, objNames.names);
				// pleaseIntroduceYourName(userPositionInTable);
			}

			// default icon, custom title
			int dialogResult = JOptionPane.showConfirmDialog(this, endGameText, "THE END", JOptionPane.YES_NO_OPTION);
			if (dialogResult == JOptionPane.YES_OPTION) {
				System.out.println("User pressed YES");
				// RESTART THE GAME = CLOSE CURRENT FRAME AND OPEN A NEW FRAME WITH
				// SELECTCHARACTERPANEL
				JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
				mainFrame.dispose();
				JFrame frame = new JFrame("Frogger");
				SelectCharacterPanel panel = new SelectCharacterPanel();
				frame.getContentPane().add(panel);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				frame.setSize(505, 550);
				frame.setResizable(false);
			} else {
				System.out.println("User pressed NO");
				System.exit(0);
			}
		}
	}

	private void pleaseIntroduceYourName(int position, ArrayList<String> names) {
		// private void pleaseIntroduceYourName(int position) {
		// TODO Auto-generated method stub
		System.out.println("=============================YOUR POSITION IS: " + position);
		String dialogText = "Congratulations";
		switch (position) {
		case 1:
			dialogText = "New Record!!" + System.lineSeparator() + "Please introduce your name:";
			// thuglifeAnimation();
			break;
		case 2:
			dialogText = "Congratulations! You were in the 2nd position." + System.lineSeparator()
					+ "Please introduce your name:";
			break;
		case 3:
			dialogText = "Congratulations! You were in the 3rd position." + System.lineSeparator()
					+ "Please introduce your name:";
			break;
		case 4:
			dialogText = "Congratulations! You were in the 4th position." + System.lineSeparator()
					+ "Please introduce your name:";
			break;
		case 5:
			dialogText = "Congratulations! You were in the 5th position!" + System.lineSeparator()
					+ "Please introduce your name:";
			break;
		case 6:
			dialogText = "Congratulations! You were in the 6th position!" + System.lineSeparator()
					+ "Please introduce your name:";
			break;
		case 7:
			dialogText = "Congratulations! You were in the 7th position!" + System.lineSeparator()
					+ "Please introduce your name:";
			break;
		case 8:
			dialogText = "Congratulations! You were in the 8th position!" + System.lineSeparator()
					+ "Please introduce your name:";
			break;
		case 9:
			dialogText = "Congratulations! You were in the 9th position!" + System.lineSeparator()
					+ "Please introduce your name:";
			break;
		case 10:
			dialogText = "Congratulations! You were in the 10th position!" + System.lineSeparator()
					+ "Please introduce your name:";
			break;
		default:
			break;
		}
		String input = JOptionPane.showInputDialog(dialogText);
		checkNames: 
		for (int i = 0; i < names.size(); i++) {
			if (names.get(i).equals(input) || input.isEmpty()) {
				String input2 = JOptionPane.showInputDialog("The name introduced is already taken or blank."
						+ System.lineSeparator() + "Please introduce another name:");
				input = input2;
				i = 0;
				continue checkNames;
			}
		}
		System.out.println(input);

		CheckPositionOfScore obj = new CheckPositionOfScore(input, score);

	}

	private void thuglifeAnimation() {
		// TODO Auto-generated method stub

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
		// File Clap = new File(soundPath);
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(soundFile));
			clip.start();
		} catch (Exception e) {
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

	private void createWaterDots() {
		for (int i = 0; i < 8; i++) {
			waterDots.add(new Block((i * 101 + 25), 0, 50, 50, "images/star.png"));
		}

	}

	public JPanel supersweetpanel() {
		JPanel sswp = new JPanel();
		Button testButton = new Button("TEST BUTTON");
		sswp.setLayout(new BorderLayout());
		sswp.add(testButton, BorderLayout.SOUTH);
		// sswp.add(this, BorderLayout.NORTH);
		return sswp;
	}
}
