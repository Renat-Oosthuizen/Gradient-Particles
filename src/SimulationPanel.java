import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SimulationPanel extends JPanel implements ActionListener {

	int UNIT_SIZE = Storage.getStorage().getUnitSize(); //Size of each cell in the game.
	static final int DELAY = 75; //Used for timer.
	boolean running = false;
	Timer timer; //Will hold an instance of the Timer class. Timer will repaint the panel every tick.
	int particleNumber = Storage.getStorage().getParticleNumber();
	LinkedList<Particle> particleList = new LinkedList<Particle>(); //List containing all the simulated particles.
	HashMap<String, BackgroundCell> backgroundMap = new HashMap<String, BackgroundCell>();
	Scanner in = new Scanner(System.in);
	int counter = 10; //Counts down when follow behaviour should be implemented.
	
	SimulationPanel(){
		this.setPreferredSize(new Dimension(Storage.getStorage().getScreenWidth(), Storage.getStorage().getScreenHeight()));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		startGame();
	}
	
	public void startGame() {
		
		//Create instances of background cells and add them to backgroundMap.
		for (int x = 0; x <= Storage.getStorage().getScreenWidth(); x = x + UNIT_SIZE) //For each set of x and y coordinates that are a UNIT_WIDTH apart create a new instance of background cell. Store in HashMap.
		{
			for (int y = 0; y <= Storage.getStorage().getScreenHeight(); y = y + UNIT_SIZE)
			{
				BackgroundCell backgroundCell = new BackgroundCell(UNIT_SIZE, x, y);
				backgroundMap.put(Integer.toString(x) + Integer.toString(y), backgroundCell);
				
				System.out.println("X = " + x);
				System.out.println("Y = " + y);
			}
		}
		
		//Create instances of particle and add them to particleList.
		for (int i = 0; i < particleNumber; i++)
		{
			Particle particle = new Particle(UNIT_SIZE, Storage.getStorage().getScreenWidth(), Storage.getStorage().getScreenHeight());
			particleList.add(particle);
		}
		
		//Print number of background cells and particles.
		System.out.println("Number of background cells: " + backgroundMap.size());
		System.out.println("Number of particles: " + particleNumber);
		
		running = true; //Set variable running to true as the game is now starting;
		timer = new Timer(DELAY, this); //Create a new instance of timer with a delay of 75 milliseconds between each tick. 
		timer.start(); //Start the timer.
	}
	
	//This function called by the system to draw the screen. The screen is drawn in layers so the previous position of the particles still exists below the black background.
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		drawScreen(g);
	}

	//Used for drawing the graphics of the game each tick.
	public void drawScreen(Graphics g) {
		
		if (running) 
		{
			//Iterate through HashMap (backgroundMap) and draw background cells.
			for (String i : backgroundMap.keySet()) 
			{
				backgroundMap.get(i).draw(g);
			}
			
			//Draw particles
			for (int i = 0; i < particleNumber; i++)
			{
				particleList.get(i).draw(g);
			}
		}
		else
		{
			simulationOver(g);
		}
		
	}
	
	//This happens every tick
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) 
		{
			
			counter--;
			
			//Reduce cell signal.
			for (String i : backgroundMap.keySet()) 
			{
				backgroundMap.get(i).reduceSignal();
			}

			
			//Move particles and update signal of background cells.
			for (int i = 0; i < particleNumber; i++)
			{
				
				if (counter > 0)
				{
					particleList.get(i).randomMove(); //Move particles randomly. Can change to move().
				}
				else 
				{
					//Particles will follow each other if 
					int northSignal = 0;
					int northEastSignal = 0;
					int eastSignal = 0;
					int southEastSignal = 0;
					int southSignal = 0;
					int southWestSignal = 0;
					int westSignal = 0;
					int northWestSignal = 0;
					
					try {
						northSignal = backgroundMap.get(Integer.toString(particleList.get(i).getX()) + Integer.toString(particleList.get(i).getY() - UNIT_SIZE)).getSignal();
						}
					catch(Exception e1){}
					try {
						northEastSignal = backgroundMap.get(Integer.toString(particleList.get(i).getX() + UNIT_SIZE) + Integer.toString(particleList.get(i).getY() - UNIT_SIZE)).getSignal();
						}
					catch(Exception e1){}
					try {
						eastSignal = backgroundMap.get(Integer.toString(particleList.get(i).getX() + UNIT_SIZE) + Integer.toString(particleList.get(i).getY())).getSignal();
						}
					catch(Exception e1){}
					try {
						southEastSignal = backgroundMap.get(Integer.toString(particleList.get(i).getX() + UNIT_SIZE) + Integer.toString(particleList.get(i).getY() + UNIT_SIZE)).getSignal();
						}
					catch(Exception e1){}
					try {
						southSignal = backgroundMap.get(Integer.toString(particleList.get(i).getX()) + Integer.toString(particleList.get(i).getY() + UNIT_SIZE)).getSignal();
						}
					catch(Exception e1){}
					try {
						southWestSignal = backgroundMap.get(Integer.toString(particleList.get(i).getX() - UNIT_SIZE) + Integer.toString(particleList.get(i).getY() + UNIT_SIZE)).getSignal();
						}
					catch(Exception e1){}
					try {
						westSignal =backgroundMap.get(Integer.toString(particleList.get(i).getX() - UNIT_SIZE) + Integer.toString(particleList.get(i).getY())).getSignal();
						}
					catch(Exception e1){}
					try {
						northWestSignal = backgroundMap.get(Integer.toString(particleList.get(i).getX() - UNIT_SIZE) + Integer.toString(particleList.get(i).getY() - UNIT_SIZE)).getSignal();
						}
					catch(Exception e1){}
						
					particleList.get(i).followMove(northSignal, northEastSignal, eastSignal, southEastSignal, southSignal, southWestSignal, westSignal, northWestSignal);
				} 
				

				//Set background colour of cell based on the currently occupied particle.
				backgroundMap.get(Integer.toString(particleList.get(i).getX()) + Integer.toString(particleList.get(i).getY())).setParticleColor(particleList.get(i).getColor());
				
				//Set signal to 255 for background cells occupied by a particle.
				backgroundMap.get(Integer.toString(particleList.get(i).getX()) + Integer.toString(particleList.get(i).getY())).setSignal(255);

			}
			
		}
		repaint();

		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			
			switch(e.getKeyCode()) 
			{
			/*case KeyEvent.VK_LEFT: //Go left if left key is pressed.
				direction = "W";
				break;
			case KeyEvent.VK_RIGHT: //Go right if right key is pressed.
				direction = "E";
				break;
			case KeyEvent.VK_UP: //Go up if up key is pressed.
				direction = "N";
				break;
			case KeyEvent.VK_DOWN: //Go down if down key is pressed.
				direction = "S";
				break;*/
			case KeyEvent.VK_ESCAPE: //Stop simulation if ESC key is pressed.
				if (running)
					{
						running = false;
					}
				else
				{
					running = true;
				}
				break;
			}
		}
		
	}
	
	public void simulationOver(Graphics g) {
		
		//Simulation over text
		g.setColor(Color.blue);
		g.setFont( new Font("Times New Roman", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Simulation Over", (Storage.getStorage().getScreenWidth() - metrics2.stringWidth("Simulation Over"))/2, Storage.getStorage().getScreenHeight()/2);
	}

}
