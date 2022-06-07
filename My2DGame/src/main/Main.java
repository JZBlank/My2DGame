package main;

import javax.swing.JFrame;
import javax.swing.JTextField;

class Main{
    public static void main(String[] args){
    	JFrame window = new JFrame();
    	JTextField jtf = new JTextField(30);
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //stops program when 'x' is clicked on
        window.setResizable(false);
        window.setTitle("Cat Wars");
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); //causes window to be sized to fit preferred size and layouts of components

        window.setLocationRelativeTo(null);
       
        window.setVisible(true);
        
        gamePanel.setupGame();
        gamePanel.startGameThread();
        
       }
}