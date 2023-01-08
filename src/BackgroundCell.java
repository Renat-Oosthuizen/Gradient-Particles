import java.awt.Color;
import java.awt.Graphics;

//This is a square cell that the screen is divided into. Cells store signal of particles that travel through them. The signal degrades. Signal is used by particles to follow each other. 
//Signal is visualised as transparent particles. The weaker the signal, the more transparent the particle.
public class BackgroundCell {
	
	final int UNIT_SIZE; //Size of the cells that the screen is divided into.
	int x; //Holds x coordinates of the cell.
	int y; //Holds y coordinates of the cell.
	private int signal = 0; //This signal is at 255 when occupied by a particle and decrements by 51 every tick. Used by particles to track other particles.
	private Color particleColor = new Color(255, 255, 255); //Colour of the particle on top of the cell.
	
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
		//Draw the particle remnant using particle colour and cell signal. Signal is used as the alpha component (transparency)
		g.setColor(new Color(particleColor.getRed(), particleColor.getGreen(), particleColor.getBlue(), signal));
		g.fillOval(x, y, UNIT_SIZE, UNIT_SIZE);
		
	}
	
	//This function reduces signal from 255 by 51 each tick down to 0 (6 possible signal levels)
	public void reduceSignal()
	{
		if (signal > 0)
		{
			signal = signal - 51; //Reduce signal
		}
	}

	//---------------------------GETTERS--------------------------------
	public int getSignal() {
		return signal;
	}

	public Color getParticleColor() {
		return particleColor;
	}
	
	//---------------------------SETTERS--------------------------------

	public void setSignal(int signal) {
		this.signal = signal;
	}
	
	public void setParticleColor(Color particleColor) {
		this.particleColor = particleColor;
	}

}
