import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Input {
	JPanel panel;
	JFrame window;
	Container com;
	JTextField jtf;
	JLabel text;
	
	public static void main(String[] args) {
	
	}
	
	public Input(JPanel jp, JFrame window) {
		
		jtf = new JTextField();
		jp.add(jtf);
		
		window.setVisible(true);
		
	}
}
