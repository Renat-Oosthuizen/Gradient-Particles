import java.awt.Color;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

//This class stores all the editable data. It get serialised and deserialised. It uses a singleton pattern.
public class Storage implements Serializable {
	
	private static final Storage INSTANCE = new Storage(); //Class stores it's own instance for singleton pattern.
	
	int screenWidth = 600; //1280;
	int screenHeight = 600; //720;
	int unitSize = 10; //Size of each cell in the game.
	LinkedList<Particle> particleList = new LinkedList<Particle>(); //List containing all the simulated particles.
	HashMap<String, BackgroundCell> backgroundMap = new HashMap<String, BackgroundCell>();
	int particleNumber = 100;
	Color particleColour = new Color(0, 0, 0); //A colour of the background cell (black).
	Color backgroundColour = new Color(0, 0, 0); //A colour of the background cell (black).
	String behaviour = "Random Movement"; //This controls what kind of behaviour the particles display.
	
	
	//Inaccessible constructor for singleton pattern
	private Storage()
	{
		
	}
	
	//Getter for the class instance
	public static Storage getStorage()
	{
		return INSTANCE;
	}

	//Getters
	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}
	
	public int getUnitSize() {
		return unitSize;
	}
	
	public LinkedList<Particle> getParticleList() {
		return particleList;
	}

	public HashMap<String, BackgroundCell> getBackgroundMap() {
		return backgroundMap;
	}

	public int getParticleNumber() {
		return particleNumber;
	}

	public Color getParticleColour() {
		return particleColour;
	}

	public Color getBackgroundColour() {
		return backgroundColour;
	}

	public String getBehaviour() {
		return behaviour;
	}

	//Setters
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public void setUnitSize(int unitSize) {
		this.unitSize = unitSize;
	}

	public void setParticleList(LinkedList<Particle> particleList) {
		this.particleList = particleList;
	}

	public void setBackgroundMap(HashMap<String, BackgroundCell> backgroundMap) {
		this.backgroundMap = backgroundMap;
	}

	public void setParticleNumber(int particleNumber) {
		this.particleNumber = particleNumber;
	}

	public void setParticleColour(Color particleColour) {
		this.particleColour = particleColour;
	}

	public void setBackgroundColour(Color backgroundColour) {
		this.backgroundColour = backgroundColour;
	}

	public void setBehaviour(String behaviour) {
		this.behaviour = behaviour;
	}

}
