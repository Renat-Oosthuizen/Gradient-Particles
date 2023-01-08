import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.Timer;

//This class is responsible for displaying the simulation. It is the panel that goes inside the window/JFrame
@SuppressWarnings("serial")
public class SimulationPanel extends JPanel implements ActionListener {

	int UNIT_SIZE = Storage.getStorage().getUnitSize(); //Size of each square cell in the simulation (pixels). Particles occupy one of these cells
	static final int DELAY = 75; //Used for timer. A frame update/tick occurs every 75ms
	boolean running = false; //Tracks if the simulation is running
	Timer timer; //Will hold an instance of the Timer class. Timer will repaint the panel every tick by firing of an ActionEvent at each interval
	int particleNumber = Storage.getStorage().getParticleNumber(); //Number of particles simulated by the application
	LinkedList<Particle> particleList = new LinkedList<Particle>(); //Holds a list of particles that must be simulated
	HashMap<String, BackgroundCell> backgroundMap = new HashMap<String, BackgroundCell>(); //HashMap holding cells occupied by particles as value with their top-left corner coordinates as keys
	int counter = 10; //A delay (number of ticks/frames) that occurs at the start of the simulation before follow behaviour is implemented. Allows the particles to spread out 
	
	//Constructor
	SimulationPanel()
	{
		this.setPreferredSize(new Dimension(Storage.getStorage().getScreenWidth(), Storage.getStorage().getScreenHeight())); //Set the panel dimensions
		this.setBackground(Color.black); //Make the panel black
		this.setFocusable(true); //Panel is focusable so that it can accept keyboard inputs (unnecessary, focusable be default)
		this.addKeyListener(new MyKeyAdapter()); //Panel has key listeners allowing it to accept keyboard inputs
		
		startSimulation(); //Start the simulation
	}
	
	//Create instances of Particles and BackgroundCells and start the simulation
	public void startSimulation() 
	{
		
		//Create instances of background cells and add them to backgroundMap
		for (int x = 0; x <= Storage.getStorage().getScreenWidth(); x = x + UNIT_SIZE) //For each set of x and y coordinates that are a UNIT_WIDTH apart create a new instance of background cell. Store in HashMap.
		{
			for (int y = 0; y <= Storage.getStorage().getScreenHeight(); y = y + UNIT_SIZE)
			{
				BackgroundCell backgroundCell = new BackgroundCell(UNIT_SIZE, x, y); //Create instance of BackgroundCell
				backgroundMap.put(Integer.toString(x) + Integer.toString(y), backgroundCell); //Store BackgroundCell in HashMap with top-left corner coordinates as keys
			}
		}
		
		//Create instances of particle and add them to particleList
		for (int i = 0; i < particleNumber; i++)
		{
			Particle particle = new Particle(UNIT_SIZE, Storage.getStorage().getScreenWidth(), Storage.getStorage().getScreenHeight()); //Instantiate the particle
			particleList.add(particle); //Add particle to the particle list
		}
		
		running = true; //Set variable running to true as the simulation is now starting is now starting
		timer = new Timer(DELAY, this); //Create a new instance of timer with a delay of 75 milliseconds between each tick 
		timer.start(); //Start the timer
	}
	
	//Method fires every tick of the Timer. If simulation is running reduce the cell signal, move each particle and repaint the screen.
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) 
		{
			
			counter--; //Reduce the delay 
			
			//Iterate though every cell in backgroundMap and reduce the cell signal of that cell
			for (String i : backgroundMap.keySet()) 
			{
				backgroundMap.get(i).reduceSignal(); //Reduce the signal of the cell
			}

			
			//Move particles and update signal of background cells.
			for (int i = 0; i < particleNumber; i++) //For each particle
			{
				
				if (counter > 0) //If delay has not run out then move the particles randomly
				{
					particleList.get(i).randomMove(); //Move particles randomly.
				}
				else //Implement follow behaviour after the delay has run out
				{
					//Set the signal from each direction to 0
					int northSignal = 0;
					int northEastSignal = 0;
					int eastSignal = 0;
					int southEastSignal = 0;
					int southSignal = 0;
					int southWestSignal = 0;
					int westSignal = 0;
					int northWestSignal = 0;
					
					//Get the signals of the surround cells for the current particle
					//For the current particle get it's x and y coordinate, from that calculate the coordinates of the surrounding cells (their HashMap key) and get the signals from those cells 
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
						
					particleList.get(i).followMove(northSignal, northEastSignal, eastSignal, southEastSignal, southSignal, southWestSignal, westSignal, northWestSignal); //Move the particle to the cell with the highest signal
				} 
				

				//Set background colour of cell based on the currently occupied particle.
				backgroundMap.get(Integer.toString(particleList.get(i).getX()) + Integer.toString(particleList.get(i).getY())).setParticleColor(particleList.get(i).getColor());
				
				//Set signal to 255 for background cells occupied by a particle. Cell containing a particle has the highest possible signal value
				backgroundMap.get(Integer.toString(particleList.get(i).getX()) + Integer.toString(particleList.get(i).getY())).setSignal(255);

			}
			
		}
		repaint(); //Update the JPanel. Triggers paintComponent(Graphics g)
	}
	
	//Method overriding so that it calls the draw(g) method. Method is automatically called by Java Swing GUI each tick. 
	//The screen is drawn in layers so the previous position of the particles still exists below the black background.
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g); //Perform the normal painting operation
		drawScreen(g); //Call the method for drawing the graphics (particles) of the simulation
	}

	//Used for drawing the graphics of the simulation each tick. Calls the draw(Graphics g) method inside each particle and background cell in order to paint them
	public void drawScreen(Graphics g) 
	{
		
		if (running) 
		{
			//Iterate through HashMap (backgroundMap) and draw background cells.
			for (String i : backgroundMap.keySet()) 
			{
				backgroundMap.get(i).draw(g); //Call the draw(Graphics g) method inside a background cell in order to paint it
			}
			
			//Draw particles
			for (int i = 0; i < particleNumber; i++)
			{
				particleList.get(i).draw(g); //Call the draw(Graphics g) method inside a particle in order to paint it
			}
		}
		else //Otherwise run the simulationOver(g) method
		{
			simulationOver(g);
		}
		
	}
	
	//Method draws the screen for when the simulation is not running (running = false)
	public void simulationOver(Graphics g) 
	{
		//Simulation over text
		g.setColor(Color.blue); //Text colour is blue
		g.setFont( new Font("Times New Roman", Font.BOLD, 75)); //Bold text of size 75 and font = "Times New Roman"
		FontMetrics metrics2 = getFontMetrics(g.getFont()); //Data about the font that has been set above. Used below for correct positioning
		g.drawString("Simulation Over", (Storage.getStorage().getScreenWidth() - metrics2.stringWidth("Simulation Over"))/2, Storage.getStorage().getScreenHeight()/2); //Place the "Simulation Over" text in the centre of the screen
	}
	
	//Inner class that has access to SimulationPanel variables and methods. It is responsible for reacting to key presses
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) 
		{
			switch(e.getKeyCode()) 
			{
			
			case KeyEvent.VK_ESCAPE: //Stop/start simulation if ESC key is pressed.
				if (running == true)
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

}
