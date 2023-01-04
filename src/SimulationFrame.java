import java.awt.Dimension;

import javax.swing.JFrame;

public class SimulationFrame extends JFrame{
	
	MenuPanel menuPanel = new MenuPanel(this);
	
	SimulationFrame() {

		this.setPreferredSize(new Dimension(620, 640));	//Use 620x640 Dimension for 600x600 screenWidth and screenHeight
		//this.add(menuPanel);
		this.add(new SimulationPanel());
		this.setTitle("Gradient Particles");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack(); //JFrame will encompass all the components that are added to the frame.
		this.setVisible(true);
		this.setLocationRelativeTo(null); //Place JFrame in the centre of the screen.
		
	}
	
	//Swaps from the menu panel to the simulation panel.
	public void swapPanels()
	{
		this.getContentPane().remove(menuPanel); //Remove MenuPanel
		this.add(new SimulationPanel()); //Insert and run SimulationPanel
		
		//Resize the JFrame
		this.setPreferredSize(new Dimension(Storage.getStorage().getScreenWidth(), Storage.getStorage().getScreenHeight()));
		this.pack();
		
		//Updates the frame content so that it displays the SimulationPanel.
		this.invalidate(); 
		this.validate(); 
	}

}
