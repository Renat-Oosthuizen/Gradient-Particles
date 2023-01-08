import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

//Class holds all the data on a particle being simulated
public class Particle {
	
	final int UNIT_SIZE; //Size of the cells that the screen is divided into. Effectively the size of the particle.
	final int SCREEN_WIDTH; //Width of the screen
	final int SCREEN_HEIGHT; //Height of the screen
	private int x; //Holds x coordinates of a particle.
	private int y; //Holds y coordinates of a particle.
	String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"}; //Array of possible directions.
	String currentDirection; //Direction that the particle is currently heading in.
	private Random random = new Random(); //Hold an instance of the Random class.
	private Color color = new Color(random.nextInt(0xFFFFFF)); //A random colour for the particle. new Color(255, 255, 255) would also work
	private String[] validDirections = {"", "", ""}; //Valid directions for a particle to go in
	boolean directionChanged = false; //Tracks if direction has changed since the previous tick in followMove()
	String newDirection = null; //Direction with the highest signal. Remains null if all directions have 0 signal.

	//Constructor
	Particle(int UNIT_SIZE, int SCREEN_WIDTH, int SCREEN_HEIGHT)
	{
		this.SCREEN_WIDTH = SCREEN_WIDTH;
		this.SCREEN_HEIGHT = SCREEN_HEIGHT;
		this.UNIT_SIZE = UNIT_SIZE;
		this.x = SCREEN_WIDTH/2; //Place the particle at the centre of the screen 
		this.y= SCREEN_HEIGHT/2; //Place the particle at the centre of the screen 
		currentDirection = directions[random.nextInt(8)]; //Select a random direction for the particle.
	}
	
	//This function makes particles follow other particles if they encounter their signal, or move randomly if no signal is found.
	public void followMove(int northValue, int northEastValue, int eastValue, int southEastValue, int southValue, int southWestValue, int westValue, int northWestValue)
	{
		
		int[] moveValues = {northValue, northEastValue, eastValue, southEastValue, southValue, southWestValue, westValue, northWestValue}; //Array of signals from surrounding cells in the same order as the directions array. 
		int tempValue = 0; //Used to find the highest move value in the array.
		
		validDirection(); //Determine valid directions to move in (current direction and those 45 degrees to each side of current direction)
		
		//Find the highest moveValue within 90 degrees of current direction and then pick that as the new direction.
		for (int i = 0; i < 8; i++) //For each direction...
		{
			//...if direction is valid to move in and has a higher signal that the previously best known signal...
			if ( (moveValues[i] > tempValue) && (validDirections[0].equals(directions[i]) || validDirections[1].equals(directions[i]) || validDirections[2].equals(directions[i])) )
			{
				
				tempValue = moveValues[i]; //...this is the highest known signal
				
				newDirection = directions[i]; //Change direction towards the highest signal
				directionChanged = true; //Direction has been changed
				
			}
		}
		
		//If direction has been changed because a signal has been encountered then move towards the strongest signal, otherwise move randomly
		if (directionChanged)
		{
			currentDirection = newDirection; //Current direction is the direction with the highest signal
			directionChanged = false; //Reset the direction changed indicator to be used for signal detection in the next tick
			move(); //Move towards the direction with the highest signal
		}
		else
		{
			randomMove(); //Move randomly
		}
		
	}
	
	//Function used to determine valid directions. Valid directions are current direction and the two directions 45 degrees to each side
	public void validDirection()
	{
		for (int i = 0; i < 8; i++) //Iterate through all 8 possible directions
			{
				//If current direction is not at the start and end of the array then valid directions are current directions and the two adjacent directions in the array
				if (directions[i].equals(currentDirection) && (i != 0) && (i != 7)) 
				{
					validDirections[0] = directions[i-1];
					validDirections[2] = directions[i+1];
				}
				//If current direction is at the start of the array then valid directions are current direction, next direction and direction at the end of the array
				else if (directions[i].equals(currentDirection) && (i == 0))
				{
					validDirections[0] = directions[7];
					validDirections[2] = directions[i+1];
				}
				//If current direction is at the end of the array then valid directions are current direction, previous direction and direction at the start of the array
				else if (directions[i].equals(currentDirection) && (i == 7))
				{
					validDirections[0] = directions[i-1];
					validDirections[2] = directions[0];
				}
			}
		
		validDirections[1] = currentDirection; //Add current direction to validDirections
	}
	
	//Randomly select one of 3 directions for the particle to move and then call the move method to move the particle in that direction.
	//Move particle randomly within a 90-degree arc of current direction. This means that particles will make progress on the screen rather than generally staying in the same area
	public void randomMove()
	{
		
		switch(currentDirection) 
		{
		case "N":
			currentDirection = chooseDirection("NE", "N", "NW"); //Select direction
			move(); //Move
			break;
		case "S":
			currentDirection = chooseDirection("SE", "S", "SW"); //Select direction
			move(); //Move
			break;
		case "W":
			currentDirection = chooseDirection("NW", "W", "SW"); //Select direction
			move(); //Move
			break;
		case "E":
			currentDirection = chooseDirection("NE", "E", "SE"); //Select direction
			move(); //Move
			break;
		case "SE":
			currentDirection = chooseDirection("S", "SE", "E"); //Select direction
			move(); //Move
			break;
		case "SW":
			currentDirection = chooseDirection("S", "SW", "W"); //Select direction
			move(); //Move
			break;
		case "NE":
			currentDirection = chooseDirection("N", "NE", "E"); //Select direction
			move(); //Move
			break;
		case "NW":
			currentDirection = chooseDirection("N", "NW", "W"); //Select direction
			move(); //Move
			break;
		}
	}
	
	//Randomly selects 1 of 3 provided directions. Used for random particle movement and redirection during collisions.
	public String chooseDirection(String direction1, String direction2, String direction3) {
		
		String[] directions = {direction1, direction2, direction3};
		String newDirection;
		
		newDirection = directions[random.nextInt(3)];
		
		return newDirection;
	}
	
	//Used to move the particle based on it's currently selected movement direction. Particle is assigned new x and y coordinates.
	public void move() {
		
		checkCollisions(); //If at border then select direction that avoids exiting screen bounds
		
		switch(currentDirection) {
		case "N":
			y = y - UNIT_SIZE;
			break;
		case "S":
			y = y + UNIT_SIZE;
			break;
		case "W":
			x = x - UNIT_SIZE;
			break;
		case "E":
			x = x + UNIT_SIZE;
			break;
		case "SE":
			x = x + UNIT_SIZE;
			y = y + UNIT_SIZE;
			break;
		case "SW":
			x = x - UNIT_SIZE;
			y = y + UNIT_SIZE;
			break;
		case "NE":
			x = x + UNIT_SIZE;
			y = y - UNIT_SIZE;
			break;
		case "NW":
			x = x - UNIT_SIZE;
			y = y - UNIT_SIZE;
			break;
		}
	}
	
	//Checks if particle has collided with the edge of the screen (panel). If it has then the particle is redirected away from the edge.
	public void checkCollisions() 
	{
		//Check if a particle is touching an individual border
		if(x == 0) //Check if particle touches left border
		{
			currentDirection = chooseDirection("NE", "E", "SE"); //Give it a right-heading direction
		}
		else if(x == (SCREEN_WIDTH - UNIT_SIZE)) //Check if particle touches right border
		{
			currentDirection = chooseDirection("NW", "W", "SW"); //Give it a left-heading direction
		}
		else if(y == 0) //Check if particle touches top border
		{
			currentDirection = chooseDirection("SW", "S", "SE"); //Give it a bottom-heading direction
		}
		else if(y == (SCREEN_HEIGHT - UNIT_SIZE)) //Check if particle touches bottom border
		{
			currentDirection = chooseDirection("NW", "N", "NE"); //Give it an upward-heading direction
		}
		
		//Check if particle is in a corner
		if(x == 0 && y == 0) //Check if particle touches top left corner
		{
			currentDirection = "SE"; //Give it a South-East direction
		}
		else if(x == (SCREEN_WIDTH - UNIT_SIZE) && y == 0) //Check if particle touches top right corner
		{
			currentDirection = "SW"; //Give it a South-West direction
		}
		else if(x == 0 && y == (SCREEN_HEIGHT - UNIT_SIZE)) //Check if particle touches bottom left corner
		{
			currentDirection = "NE"; //Give it a North-East direction
		}
		else if(x == (SCREEN_WIDTH - UNIT_SIZE) && y == (SCREEN_HEIGHT - UNIT_SIZE)) //Check if particle touches bottom right corner
		{
			currentDirection = "NW"; //Give it a North-West direction
		}
	}
	
	//Used for drawing the graphics/particles of the simulation each tick.
	public void draw(Graphics g) 
	{
			g.setColor(color); //Set the colour of the Graphics class that will be used to draw the particle
			g.fillOval(x, y, UNIT_SIZE, UNIT_SIZE); //Draw the particle (an oval at the provided x and y coordinates (upper left corner) with provided width and height)
	}
			
	//--------------------------------SETTERS--------------------------------
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	//--------------------------------GETTERS--------------------------------
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Color getColor() {
		return color;
	}

}
