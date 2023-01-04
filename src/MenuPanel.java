import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class MenuPanel extends JPanel{
	
	JLabel lblScreenDimensions = new JLabel("Screen Dimensions: ");
	JLabel lblParticleNumber = new JLabel("Particle Number: ");
	JLabel lblUnitSize = new JLabel("Unit Size: "); 
	JLabel lblParticleColour = new JLabel("Particle Colour: ");
	JLabel lblBackgroundColour = new JLabel("Background Colour: ");
	JLabel lblParticleBehaviour = new JLabel("Particle Behaviour: ");
	JButton btnSaveState = new JButton("Save Current State");
	JButton btnLoadState = new JButton("Load Previous State"); 
	JButton btnExitMenu = new JButton("Save and Simulate"); 
	
	JComboBox<String> screenDimensionsBox = new JComboBox<>();
	JComboBox<String> particleNumberBox = new JComboBox<>();
	JComboBox<String> unitSizeBox = new JComboBox<>();
	JComboBox<String> particleColourBox = new JComboBox<>();
	JComboBox<String> backgroundColourBox = new JComboBox<>();
	JComboBox<String> particleBehaviourBox = new JComboBox<>();
	
	//Constructor
	MenuPanel(final SimulationFrame simulationFrame)
	{
		screenDimensionsBox.addItem("400 x 400");
		screenDimensionsBox.addItem("800 x 800");
		
		particleNumberBox.addItem("1");
		particleNumberBox.addItem("20");
		particleNumberBox.addItem("100");
		
		unitSizeBox.addItem("8");
		unitSizeBox.addItem("10");
		
		particleColourBox.addItem("White");
		particleColourBox.addItem("Black");
		particleColourBox.addItem("Red");
		particleColourBox.addItem("Green");
		particleColourBox.addItem("Blue");
		particleColourBox.addItem("Random");
		
		backgroundColourBox.addItem("White");
		backgroundColourBox.addItem("Black");
		backgroundColourBox.addItem("Red");
		backgroundColourBox.addItem("Green");
		backgroundColourBox.addItem("Blue");
		backgroundColourBox.addItem("Random");
		
		particleBehaviourBox.addItem("Static Movement");
		particleBehaviourBox.addItem("Random Movement");
		particleBehaviourBox.addItem("Gradient Follow");
	    btnSaveState.addActionListener(new ActionListener() //When the button is pressed...
	    { 
			public void actionPerformed(ActionEvent arg0) 
			{
				save();
			}
		});
	    btnLoadState.addActionListener(new ActionListener() //When the button is pressed...
	    { 
			public void actionPerformed(ActionEvent arg0) 
			{
				load();
			}
		});
	    GroupLayout groupLayout = new GroupLayout(this);
	    groupLayout.setHorizontalGroup(
	    	groupLayout.createParallelGroup(Alignment.LEADING)
	    		.addGroup(groupLayout.createSequentialGroup()
	    			.addContainerGap(177, Short.MAX_VALUE)
	    			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
	    				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
	    					.addComponent(btnExitMenu, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	    					.addComponent(btnLoadState, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	    					.addComponent(btnSaveState, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	    					.addComponent(lblParticleBehaviour, Alignment.LEADING)
	    					.addComponent(lblBackgroundColour, Alignment.LEADING)
	    					.addComponent(lblParticleNumber, Alignment.LEADING)
	    					.addComponent(lblParticleColour, Alignment.LEADING)
	    					.addComponent(lblUnitSize, Alignment.LEADING))
	    				.addComponent(lblScreenDimensions))
	    			.addGap(23)
	    			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
	    				.addComponent(particleBehaviourBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(backgroundColourBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(particleNumberBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(particleColourBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(unitSizeBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(screenDimensionsBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	    			.addGap(86))
	    );
	    groupLayout.setVerticalGroup(
	    	groupLayout.createParallelGroup(Alignment.LEADING)
	    		.addGroup(groupLayout.createSequentialGroup()
	    			.addGap(9)
	    			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
	    				.addComponent(screenDimensionsBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(lblScreenDimensions))
	    			.addPreferredGap(ComponentPlacement.RELATED)
	    			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
	    				.addComponent(unitSizeBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(lblUnitSize))
	    			.addPreferredGap(ComponentPlacement.RELATED)
	    			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
	    				.addComponent(particleColourBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(lblParticleColour))
	    			.addGap(8)
	    			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
	    				.addComponent(particleNumberBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(lblParticleNumber))
	    			.addPreferredGap(ComponentPlacement.RELATED)
	    			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
	    				.addComponent(backgroundColourBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(lblBackgroundColour))
	    			.addPreferredGap(ComponentPlacement.RELATED)
	    			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
	    				.addComponent(lblParticleBehaviour)
	    				.addComponent(particleBehaviourBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	    			.addGap(18)
	    			.addComponent(btnSaveState)
	    			.addPreferredGap(ComponentPlacement.RELATED)
	    			.addComponent(btnLoadState)
	    			.addPreferredGap(ComponentPlacement.RELATED)
	    			.addComponent(btnExitMenu)
	    			.addContainerGap())
	    );
	    setLayout(groupLayout);
	    btnExitMenu.addActionListener(new ActionListener() //When the button is pressed...
	    { 
			public void actionPerformed(ActionEvent arg0) 
			{
				exitMenu(simulationFrame);
			}
		});
	    
	    
	    this.setVisible(true);
	}
	
	//This function serialises the current state of the particles.
	public void save()
	{
		try{    
			  //Creating stream and writing the object    
			  FileOutputStream fileOut = new FileOutputStream("serialised.txt");    
			  ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);    
			  objectOut.writeObject(Storage.getStorage());    
			  objectOut.flush();
			  
			  //closing the stream    
			  objectOut.close();    
			  System.out.println("Storage serialised.");
			  
			  }
		catch(Exception e)
		{
			System.out.println(e);
		}  
	}
	
	//This function deserialises the current state of the particles.
	public void load()
	{
		try
		{  
			
			  //Creating stream to read the object  
			  ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream("serialised.txt"));  
			  SimulationPanel simulationPanel  = (SimulationPanel)objectIn.readObject(); 
			  
			  //closing the stream  
			  objectIn.close();  
			  
		}
		catch(Exception e)
		{
			System.out.println(e);
		}  
	}
	
	//This function exits the menu
	public void exitMenu(SimulationFrame simulationFrame)
	{
		//Save the panel dimensions
		if (screenDimensionsBox.getSelectedIndex() == 0)
		{
			Storage.getStorage().setScreenWidth(400);
			Storage.getStorage().setScreenHeight(400);
		}
		else
		{
			Storage.getStorage().setScreenWidth(800);
			Storage.getStorage().setScreenHeight(800);
		}
		
		//Save the unit size
		Storage.getStorage().setUnitSize(Integer.parseInt((String) unitSizeBox.getSelectedItem()));
		
		//Save the number of particles
		Storage.getStorage().setParticleNumber(Integer.parseInt((String) particleNumberBox.getSelectedItem()));

		simulationFrame.swapPanels();
	}
 
}
