package flappyBird;

import java.awt.Graphics;

import javax.swing.JPanel;

public class renderer extends JPanel {
	
	private static final long serialVersionUID=1L;

   protected void paintComponent(Graphics g) 
   {
	   super.paintComponent(g);
	   flappybird.flappybird.repaint(g);
   }

}
