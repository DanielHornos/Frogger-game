import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Frogger");
//		MainPanel panel = new MainPanel();
		SelectCharacterPanel panel = new SelectCharacterPanel();
		
		frame.getContentPane().add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(505, 550);
		
		
		frame.setResizable(false);

	}

}
