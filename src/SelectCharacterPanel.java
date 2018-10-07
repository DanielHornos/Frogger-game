import java.awt.Button;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SelectCharacterPanel extends JPanel implements KeyListener {

	ArrayList<Block> blocks = new ArrayList<Block>();
	ArrayList<Block> players = new ArrayList<Block>();
	ArrayList<Block> text = new ArrayList<Block>();
	
	String[] characters = {"images/resized/char-boy.png","images/resized/char-cat-girl.png","images/resized/char-horn-girl.png","images/resized/char-pink-girl.png","images/resized/char-princess-girl.png"};
	String selectedPlayer = "images/resized/char-boy.png";
	String level = "Easy";
	
	Animate animate;
	Block player;
	Block selectorOne;
	Block selectorTwo;
	Block background;
	Thread thread;
	
	JLabel label;
	
	Boolean gameStarted = false;


	public SelectCharacterPanel() {

		background = new Block(0, 0, 505, 550, "images/resized/FroggerBackGround2.png");
		
		selectorOne = new Block(28, 90, 71, 120, "images/resized/Selector.png");
		selectorTwo = new Block(350, 295, 50, 50, "images/resized/Star.png");
		
		
		
		for (int i = 0; i < 5; i++) {
			players.add(new Block((i * 66 + (i * 30) + 30), 120, 66, 75, characters[i])); 
		}
		
		
		text.add(new Block(20, 30, 450, 75, "images/resized/Select-Player-red.png")); 
		text.add(new Block(20, 220, 450, 75, "images/resized/select-level-red.png")); 
		text.add(new Block(150, 300, 200, 50, "images/resized/level-easy.png")); 
		text.add(new Block(150, 350, 200, 50, "images/resized/level-medium.png")); 
		text.add(new Block(150, 405, 200, 50, "images/resized/level-difficult.png")); 
		
		label = new JLabel();
		
//		player = new Block(220, 390, 66, 75, "images/resized/char-boy.png");
		
		addKeyListener(this);
		setFocusable(true);
		
		gameStarted = true;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // this will erase the screen and then reprint
		
		if(gameStarted = true) {
			animate = new Animate(this);
			thread = new Thread(animate);
			thread.start();
			gameStarted = false;
		}
		
		background.draw(g, this);
		selectorOne.draw(g, this);
		selectorTwo.draw(g, this);
		for (Block p : players) {
			p.draw(g, this);
		}
		for (Block b : blocks) {
			b.draw(g, this);
		}
		for (Block t : text) {
			t.draw(g, this);
		}
//		player.draw(g, this);
		
	}
	
	public void update() {
		repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_1) {

		}		
		if (e.getKeyCode() == KeyEvent.VK_2) {

		}
		if (e.getKeyCode() == KeyEvent.VK_3) {

		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			  setVisible(false);

			  selectPlayerSkin();
			  selectLevel();
			  
			  JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
			  mainFrame.dispose();
			  
			  
			  JFrame frame = new JFrame("Frogger");
			  MainPanel panel = new MainPanel(selectedPlayer, level);
				
			  frame.getContentPane().add(panel);
				
			  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			  frame.setVisible(true);
			  frame.setSize(505, 550);	  	
			  
			  frame.setResizable(false);
			  frame.validate();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT && selectorOne.x > 40) {
			selectorOne.x -= 96;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && selectorOne.x < (getWidth() - selectorOne.width) -20) {
			selectorOne.x += 96;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP && selectorTwo.y > 300) {
			selectorTwo.y -= 55;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN && selectorTwo.y < 400) {
			selectorTwo.y += 55;
		}
		
	}

	private void selectLevel() {
		switch(selectorTwo.y) {
		case 295:
			level = "Easy";
			break;
		case 350:
			level = "Medium";
			break;
		case 405:
			level = "Difficult";
			break;
		}
		
	}

	private void selectPlayerSkin() {
		switch(selectorOne.x) {
		case 28:
			selectedPlayer = characters[0];
			break;
		case 124:
			selectedPlayer = characters[1];
			break;
		case 220:
			selectedPlayer = characters[2];
			break;
		case 316:
			selectedPlayer = characters[3];
			break;
		case 412:
			selectedPlayer = characters[4];
			break;
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

	
}
