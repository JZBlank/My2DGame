package main;

import javax.swing.JTextField;

public class textField extends JTextField{
	GamePanel gp;
	public String s = "";
	
	public boolean visible = false;
	public int x,y, width, height;
	
	public textField() {

		JTextField jtf = new JTextField();
    	jtf.setVisible(visible);
    	System.out.println("called");
		
	}
	
	public void addActionListener() {
		s = this.getText();
		System.out.println(s);
	}
	
}
