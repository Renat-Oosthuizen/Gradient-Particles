import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;

public class BackgroundCell implements Serializable {
	
	final int UNIT_SIZE; //Size of the cells that the screen is divided into.
	int x; //Holds x coordinates of the cell.
	int y; //Holds y coordinates of the cell.
	private Color colour = new Color(0, 0, 0); //A colour of the background cell (black).
	private int signal = 0; //This signal is at 5 when occupied by a particle and decrements by 1 every tick. Used by particles to track other particles.
	private Color particleColor = new Color(255, 255, 255); //Colour of the particle on top of the cell.
	//static Random random = new Random(); //Hold an instance of the Random class.
	//private Color colour = new Color(random.nextInt(0xFFFFFF)); //A random colour for the particle.
	
	//Constructor
	BackgroundCell(int UNIT_SIZE, int x, int y)
	{
		this.UNIT_SIZE = UNIT_SIZE;
		this.x = x;
		this.y = y;
	}
	
	//Used the particle is drawn on the panel (each tick).
	public void draw(Graphics g) 
	{
		//Draw the background cell.
		g.setColor(colour);
		g.fillRect(x, y, UNIT_SIZE, UNIT_SIZE);
		
		//Draw the particle remnant using particle colour and cell signal.
		g.setColor(new Color(particleColor.getRed(), particleColor.getGreen(), particleColor.getBlue(), signal/*(signal / 6)*/));
		g.fillOval(x, y, UNIT_SIZE, UNIT_SIZE);
		
	}
	
	//This function reduces signal by 1 each tick down to 1.
	public void reduceSignal()
	{
		if (signal > 0)
		{
			signal = signal - 51;
		}
	}

	//Getters and Setters
	public int getSignal() {
		return signal;
	}

	public void setSignal(int signal) {
		this.signal = signal;
	}

	public Color getParticleColor() {
		return particleColor;
	}

	public void setParticleColor(Color particleColor) {
		this.particleColor = particleColor;
	}

}
