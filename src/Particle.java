import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;

public class Particle implements Serializable {
	
	private Random random = new Random(); //Hold an instance of the Random class.
	final int UNIT_SIZE; //Size of the cells that the screen is divided into. Effectively the size of the particle.
	final int SCREEN_WIDTH;
	final int SCREEN_HEIGHT;
	private int x; //Holds x coordinates of a particle.
	private int y; //Holds y coordinates of a particle.
	String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"}; //Array of possible directions.
	String currentDirection; //Direction that the particle is currently heading in.
	private Color color = /*new Color(255, 255, 255);*/ new Color(random.nextInt(0xFFFFFF)); //A random colour for the particle.
	private String[] validDirections = {"", "", ""};
	boolean directionChanged = false; //Tracks if direction has changed since the previous tick in followMove()
	String newDirection = null; //Direction with the highest signal. Remains null if all directions have 0 signal.

	//Constructor
	Particle(int UNIT_SIZE, int SCREEN_WIDTH, int SCREEN_HEIGHT){
		
		this.SCREEN_WIDTH = SCREEN_WIDTH;
		this.SCREEN_HEIGHT = SCREEN_HEIGHT;
		this.UNIT_SIZE = UNIT_SIZE;
		this.x = SCREEN_WIDTH/2;
		this.y= SCREEN_HEIGHT/2;
		currentDirection = directions[random.nextInt(8)]; //Select a random direction for the particle.
		
	}
	
	//This function makes particles follow other particles if the encounter their signal, or move randomly if no signal is found.
	public void followMove(int northValue, int northEastValue, int eastValue, int southEastValue, int southValue, int southWestValue, int westValue, int northWestValue)
	{
		
		int[] moveValues = {northValue, northEastValue, eastValue, southEastValue, southValue, southWestValue, westValue, northWestValue}; //Array of signals from surrounding cells in the same order as the directions array. 
		int tempValue = 0; //Used to find the highest move value in the array.
		
		
		//Find the highest moveValue within 45 degrees of current direction and then pick that as the new direction.
		validDirection();
		
		for (int i = 0; i < 8; i++)
		{
			
			if ( (moveValues[i] > tempValue) && (validDirections[0].equals(directions[i]) || validDirections[1].equals(directions[i]) || validDirections[2].equals(directions[i])) )
			{
				
				tempValue = moveValues[i];
				
				newDirection = directions[i]; //Change direction towards the highest signal.
				directionChanged = true;
				
			}
		}
		
		//If there is no new direction then move randomly, otherwise follow in the new direction towards strongest signal.
		if (newDirection != null && directionChanged)
		{
			currentDirection = newDirection;
			directionChanged = false;
			move();
		}
		else
		{
			randomMove();
		}
		
	}
	
	public void validDirection()
	{
		for (int i = 0; i < 8; i++)
			{
				if (directions[i].equals(currentDirection) && (i != 0) && (i != 7))
				{
					validDirections[0] = directions[i-1];
					validDirections[2] = directions[i+1];
				}
				else if (directions[i].equals(currentDirection) && (i == 0))
				{
					validDirections[0] = directions[7];
					validDirections[2] = directions[i+1];
				}
				else if (directions[i].equals(currentDirection) && (i == 7))
				{
					validDirections[0] = directions[i-1];
					validDirections[2] = directions[0];
				}
			}
		
		validDirections[1] = currentDirection;
	}
	
	//Move particle randomly. Pick one of 3 valid directions and then change it's coordinates.
	public void randomMove()
	{
		
		switch(currentDirection) {
		case "N":
			currentDirection = chooseDirection("NE", "N", "NW");
			move();
			break;
		case "S":
			currentDirection = chooseDirection("SE", "S", "SW");
			move();
			break;
		case "W":
			currentDirection = chooseDirection("NW", "W", "SW");
			move();
			break;
		case "E":
			currentDirection = chooseDirection("NE", "E", "SE");
			move();
			break;
		case "SE":
			currentDirection = chooseDirection("S", "SE", "E");
			move();
			break;
		case "SW":
			currentDirection = chooseDirection("S", "SW", "W");
			move();
			break;
		case "NE":
			currentDirection = chooseDirection("N", "NE", "E");
			move();
			break;
		case "NW":
			currentDirection = chooseDirection("N", "NW", "W");
			move();
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
	
	//Used for moving the particle. Particle is assigned new x and y coordinates.
	public void move() {
		
		checkCollisions();
		
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
	public void checkCollisions() {
		
		//Left border collision.
		if(x == 0)
		{
			//running = false;
			currentDirection = chooseDirection("NE", "E", "SE");
		}
		
		//Right border collision.
		if(x == SCREEN_WIDTH - UNIT_SIZE)
		{
			//running = false;
			currentDirection = chooseDirection("NW", "W", "SW");
		}
		
		//Top border collision
		if(y == 0)
		{
			//running = false;
			currentDirection = chooseDirection("SW", "S", "SE");
		}
		
		//Bottom border collision.
		if(y == SCREEN_HEIGHT - UNIT_SIZE)
		{
			//running = false;
			currentDirection = chooseDirection("NW", "N", "NE");
		}
		
		//Top left corner collision
		if(x == 0 && y == 0)
		{
			//running = false;
			currentDirection = chooseDirection("S", "SE", "E");
		}
		
		//Top right corner collision
		if(x == SCREEN_WIDTH - UNIT_SIZE && y == 0)
		{
			//running = false;
			currentDirection = chooseDirection("S", "SW", "W");
		}
		
		//Bottom left corner collision
		if(x == 0 && y == SCREEN_HEIGHT - UNIT_SIZE)
		{
			//running = false;
			currentDirection = chooseDirection("N", "NE", "E");
		}
		
		//Bottom right corner collision
		if(x == SCREEN_WIDTH - UNIT_SIZE && y == SCREEN_HEIGHT - UNIT_SIZE)
		{
			//running = false;
			currentDirection = chooseDirection("N", "NW", "W");
		}
		
		//if(!running)
		//{
		//	timer.stop();
		//}
	}
	
	//Used the particle is drawn on the panel (each tick).
	public void draw(Graphics g) {
		
			g.setColor(color);
			g.fillOval(x, y, UNIT_SIZE, UNIT_SIZE);
		
	}
			
	//Setters and getters
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}



}
