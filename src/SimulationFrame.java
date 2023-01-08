import java.awt.Dimension;

import javax.swing.JFrame;

//This class is responsible for creating a window inside of which the simulation is displayed.
@SuppressWarnings("serial")
public class SimulationFrame extends JFrame{
	
	SimulationFrame() {

		this.setPreferredSize(new Dimension(620, 640));	//Use 620x640 Dimension for 600x600 display window (screenWidth and screenHeight)
		this.add(new SimulationPanel()); //Add the JPanel SimulationPanel which displays the simulation
		this.setTitle("Gradient Particles"); //Title of the window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit the application when the window is closed
		this.setResizable(false); //Window is not resizable. Simulation relies on JPanel dimensions being divisible by particle size
		this.pack(); //JFrame will encompass all the components that are added to the frame
		this.setVisible(true); //Make the window visible
		this.setLocationRelativeTo(null); //Place JFrame in the centre of the screen
		
	}
}
