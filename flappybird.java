package flappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class flappybird implements ActionListener,KeyListener
{
	
	public static flappybird flappybird;
	
	public final int WIDTH=600,HEIGHT=600;
	
	public renderer renderer;
	
	public Rectangle bird;
	
	public ArrayList<Rectangle>columns;
	
	public Random rand;
	

	public int ticks, yMotion, score;

	public boolean gameOver, started;
	
	
	public flappybird() {
	
		JFrame jframe=new JFrame();
		Timer timer=new Timer(20,this);
		
		renderer=new renderer();
		rand= new Random();
		
		jframe.setTitle("Flappy Bird");
		jframe.add(renderer);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.addKeyListener(this);
		jframe.setVisible(true);
		jframe.setResizable(false);
		
		bird=new Rectangle(WIDTH/2 -10,HEIGHT/2 -10, 10, 10);
		columns=new ArrayList<Rectangle>();
		
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		timer.start();
		
		
		
	}
	

	public void addColumn(boolean b) {
		
		int space=250;
		int width=100;
		int height=50+rand.nextInt(300);
		
		if (b)
		{
			columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
		}
		else
		{
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
		}
		
	}
	
	public void paintcolumn(Graphics g,Rectangle column)
	{
		g.setColor(Color.MAGENTA);
		g.fillRect(column.x, column.y, column.width, column.height);
	}
   
	public void jump()
	{
		if (gameOver)
		{
			bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 10, 10);
			columns.clear();
			yMotion = 0;
			score = 0;

			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);

			gameOver = false;
		}

		if (!started)
		{
			started = true;
		}
		else if (!gameOver)
		{
			if (yMotion > 0)
			{
				yMotion = 0;
			}

			yMotion -= 5;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
if(!gameOver) {
		int speed = 3;

		ticks++;

		if (started)
		{
			for (int i = 0; i < columns.size(); i++)
			{
				Rectangle column = columns.get(i);

				column.x -= speed;
			}

			if (ticks % 2 == 0 && yMotion < 15)
			{
				yMotion += 1;
			}

			for (int i = 0; i < columns.size(); i++)
			{
				Rectangle column = columns.get(i);

				if (column.x + column.width < 0)
				{
					columns.remove(column);

					if (column.y == 0)
					{
						addColumn(false);
					}
				}
			}

			bird.y += yMotion;

			for (Rectangle column : columns)
			{
				if (column.y == 0 && bird.x + bird.width / 2 > column.x + column.width / 2 - 10 && bird.x + bird.width / 2 < column.x + column.width / 2 + 10)
				{
					score++;
				}

				if (column.intersects(bird))
				{
					gameOver = true;

					if (bird.x <= column.x)
					{
						bird.x = column.x - bird.width;

					}
					else
					{
						if (column.y != 0)
						{
							bird.y = column.y - bird.height;
						}
						else if (bird.y < column.height)
						{
							bird.y = column.height;
						}
					}
				}
			}

			if (bird.y > HEIGHT - 120 || bird.y < 0)
			{
				gameOver = true;
			}

			if (bird.y + yMotion >= HEIGHT - 120)
			{
				bird.y = HEIGHT - 120 - bird.height;
				gameOver = true;
			}
		}
}
		renderer.repaint();
	}
	
	
	
	public void repaint(Graphics g) {
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.lightGray);
		g.fillRect(0,HEIGHT-100, WIDTH-5, 100);
		
		g.setColor(Color.orange);
		g.fillRect(0, HEIGHT-120, WIDTH-5, 20);
		
		g.setColor(Color.WHITE);
		g.fillRect(bird.x,bird.y, bird.width, bird.height);
		
		for (Rectangle column:columns) {
			paintcolumn(g,column);
		}
		
		g.setColor(Color.RED);
		g.setFont(new Font("Arial",1,40));
		
		if(!started)
		{
			g.drawString("Press Enter to Start", 75, HEIGHT/2 -50);
		}
		
		if (gameOver)
		{
			g.drawString("Game Over!", 100, HEIGHT / 2 - 50);
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
		}
		
		if (!gameOver && started)
		{
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
		}
	}

	public static void main(String[] args) {
		
		flappybird=new flappybird();

	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			jump();
		}
		if(!gameOver) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE);
		{
			jump();
		}
		}
		
	}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}





	




}
