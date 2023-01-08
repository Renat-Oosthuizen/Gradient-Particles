//This class stores all the editable data. It uses a singleton pattern
public class Storage {
	
	private static final Storage INSTANCE = new Storage(); //Class stores it's own instance for singleton pattern.
	
	int screenWidth = 600; //Screen width (pixels)
	int screenHeight = 600; //Screen height (pixels)
	int unitSize = 10; //Size of each cell in the simulation
	int particleNumber = 100; //Number of particles in the simulation
	
	//Inaccessible constructor for singleton pattern
	private Storage()
	{
		
	}
	
	//Getter for the class instance
	public static Storage getStorage()
	{
		return INSTANCE;
	}

	//---------------------------GETTERS--------------------------------
	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}
	
	public int getUnitSize() {
		return unitSize;
	}

	public int getParticleNumber() {
		return particleNumber;
	}

	//---------------------------SETTERS--------------------------------
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public void setUnitSize(int unitSize) {
		this.unitSize = unitSize;
	}

	public void setParticleNumber(int particleNumber) {
		this.particleNumber = particleNumber;
	}

}
