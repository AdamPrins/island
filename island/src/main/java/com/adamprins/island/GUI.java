package com.adamprins.island;

import java.awt.*;
import javax.swing.*;

import com.adamprins.island.Generate.Distribution;
import com.adamprins.island.geometry.Triangle;

import java.awt.event.*;
import java.util.ArrayList;

/**
 * This is the GUI that contains the functionality of island
 * It contains the canvas, buttons, and file menu, and handles their interaction 
 * 
 *  
 * @authors Adam Prins
 * 
 * @version 0.4.0 
 * 		Added a drop down menu for selecting a distribution method
 *		
 */
public class GUI implements ActionListener {
	
	
	/* JMenu File items */
    private JMenuItem quitItem;
    private JMenuItem clearItem;
    
    /* The output fields */
    private JLabel outputStatic;
    private JLabel output;
    
    /* The JButtons */
    private JButton newPointButton;
    private JButton newPoint10Button;
    private JButton newPoint100Button;
    private JButton newDepth0;
    private JButton newDepth1;
    
    /* The JToggleButtons */
    private JToggleButton triangleVisabilityToggle;
    private JToggleButton pointVisabilityToggle;
    private JToggleButton circleVisabilityToggle;
    
    /* The drop down menu for the distribution methods */
    private JComboBox<Distribution> distributionDropdown;
    
    
    /* The canvas where the drawing is performed */
    private Canvas canvas;
    
    /* The size of the canvas in terms of grid bag coordinates */
    private static final int CANVASE_SIZE = 10;
    
    private ArrayList<Triangle> triangles;
    
    /**
     * Constructor of the GUI 
     * Initializes the frame and configures the layouts
     * Sets all listeners
     */
	public GUI() {
		JFrame frame = new JFrame("island"); 
	    Container contentPane = frame.getContentPane(); 
	    contentPane.setLayout(new GridBagLayout());
	    // get the content pane so we can put stuff in
	    
	    createMenus(frame);
	    createCanvasPanel(contentPane);
	    createPanelSpacing(contentPane);
	    createInterfacePanel(contentPane);
	    
	    frame.setPreferredSize(new Dimension(900,600));
	    frame.pack(); // pack contents into our frame
        frame.setResizable(false); // we can resize it
        frame.setVisible(true); // make it visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //stops the program when the x is pressed
        
        try {
		} catch (Exception e) {
			output.setText(e.getMessage());
		}
        
        initilizeTriangles();
        canvas.repaint();
	}
	
	/**
	 * Creates the menus for this GUI
	 * @param frame the frame that we will add the menus to
	 */
	private void createMenus(JFrame frame) {
		JMenuBar menubar = new JMenuBar();
	    frame.setJMenuBar(menubar); // add menu bar to our frame
	
	    //Creates and adds menus to our JFrame
	    JMenu fileMenu = new JMenu("File");
	    menubar.add(fileMenu);
	    
	    //Populates the fileMenu
	    quitItem = new JMenuItem("Quit");
	    fileMenu.add(quitItem);
	    quitItem.addActionListener(this);
	    
	    clearItem = new JMenuItem("Clear");
	    fileMenu.add(clearItem);
	    clearItem.addActionListener(this);
	    
	    
	}
	
	/**
	 * Creates the content of the board Panel and adds it to the given contentPane
	 * @param contentPane the contentPane of the frame that we are adding this JPanel to
	 */
	private void createCanvasPanel(Container contentPane) {
	    canvas = new Canvas();
	    
	    GridBagConstraints c = new GridBagConstraints();
	    c.gridx=0;					c.gridy=0;
		c.gridwidth=1;				c.gridheight=CANVASE_SIZE;
	    c.ipadx = 20; 				c.ipady = CANVASE_SIZE;	//c.ipadx fully controls the space between the left and the canvas
	    c.weightx=1;
	    contentPane.add(Box.createGlue(),c);
	    
	    c = new GridBagConstraints();
	    c.anchor = GridBagConstraints.LINE_START;
	    c.gridx=1;					c.gridy=0;
	    c.gridwidth=CANVASE_SIZE;	c.gridheight=CANVASE_SIZE;
	    c.weightx=1;
	    contentPane.add(canvas,c);
	}
	
	/**
	 * Creates the spacing between the two JPanels
	 * @param contentPane the contentPane of the frame that we are adding this JPanel to
	 */
	private void createPanelSpacing(Container contentPane) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx=CANVASE_SIZE+2;		c.gridy=0;
		c.gridwidth=1;				c.gridheight=6;
	    c.ipadx = 20; 				c.ipady = 5;	//c.ipadx fully controls the space between contentPane and interfacePanel
	    c.weightx=0;
	    contentPane.add(Box.createGlue(),c);
	}
	
	/**
	 * Creates the Interface JPanel and adds it to the given contentPane
	 * @param contentPane the contentPane of the frame that we are adding this JPanel to
	 */
	private void createInterfacePanel(Container contentPane) {
		JPanel interfacePanel = new JPanel();
	    interfacePanel.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.anchor = (GridBagConstraints.LINE_START);
	    c.fill = GridBagConstraints.HORIZONTAL;
	    
	    newPointButton = new JButton("1 Point");
	    newPointButton.addActionListener(this);
	    c.gridx = 0;			c.gridy = 3;
	    interfacePanel.add(newPointButton,c);
	    
	    newPoint10Button = new JButton("10 Points");
	    newPoint10Button.addActionListener(this);
	    c.gridx = 1;			c.gridy = 3;
	    interfacePanel.add(newPoint10Button,c);
	    
	    newPoint100Button = new JButton("100 Points");
	    newPoint100Button.addActionListener(this);
	    c.gridx = 2;			c.gridy = 3;
	    interfacePanel.add(newPoint100Button,c);
	    
	    newDepth0 = new JButton("New Ocean");
	    newDepth0.addActionListener(this);
	    c.gridx = 0;			c.gridy = 4;
	    interfacePanel.add(newDepth0,c);
	    
	    newDepth1 = new JButton("New Coast");
	    newDepth1.addActionListener(this);
	    c.gridx = 1;			c.gridy = 4;
	    interfacePanel.add(newDepth1,c);
	    
	    pointVisabilityToggle = new JToggleButton("Points");
	    pointVisabilityToggle.addActionListener(this);
	    pointVisabilityToggle.setSelected(false);
	    c.gridx = 0;			c.gridy = 5;
	    interfacePanel.add(pointVisabilityToggle,c);
	    
	    triangleVisabilityToggle = new JToggleButton("Triangles");
	    triangleVisabilityToggle.addActionListener(this);
	    triangleVisabilityToggle.setSelected(false);
	    c.gridx = 1;			c.gridy = 5;
	    interfacePanel.add(triangleVisabilityToggle,c);
	    
	    circleVisabilityToggle = new JToggleButton("Circles");
	    circleVisabilityToggle.addActionListener(this);
	    circleVisabilityToggle.setSelected(false);
	    c.gridx = 2;			c.gridy = 5;
	    interfacePanel.add(circleVisabilityToggle,c);
	    
	    outputStatic = new JLabel("Output: ");
	    c.gridx = 0;			c.gridy = 6;
	    interfacePanel.add(outputStatic,c);
	    
	    c.gridx = 0;			c.gridy = 7;
	    c.weightx=1;
	    c.gridwidth=4;
	    output = new JLabel(" ");
	    output.setPreferredSize(new Dimension(150,30));
	    interfacePanel.add(output,c);
	    
        distributionDropdown = new JComboBox<Distribution>(Distribution.values());
	    c.gridx = 1;			c.gridy = 8;
	    interfacePanel.add(distributionDropdown,c);
	    
		c.gridx=CANVASE_SIZE+3;	c.gridy=0;
		c.gridwidth=4;			c.gridheight=CANVASE_SIZE;
	    c.ipadx = 5; 			c.ipady = 5;
	    c.weightx=1;
	    contentPane.add(interfacePanel,c);
	}
	
	
	/** 
	 * This action listener is called when the user clicks on 
     * any of the GUI's buttons. 
     */
    public void actionPerformed(ActionEvent e)
    {
        Object o = e.getSource(); // get the action 
        
        // see if it's a JButton
        if (o instanceof JButton) {
        	JButton button = (JButton) o;
        	 actionOnJButton(button);
        }
        // see if it's a JToggleButton
        else if (o instanceof JToggleButton) {
        	JToggleButton button = (JToggleButton) o;
        	 actionOnJToggleButton(button);
        }
        // see if its a JMenuItem
        else if (o instanceof JMenuItem){
            JMenuItem item = (JMenuItem)o;
            actionOnJMenu(item);
        }
    }
    
    /**
     * This method handles the pressing of a JButton
     * 
     * @param button the button that was pressed
     */
	private void actionOnJButton(JButton button) {
		int limit=0;
		
		if (button == newPointButton) {
		}
		else if (button == newPoint10Button) {
			limit=10;
		}
		else if (button == newPoint100Button) {
			limit=100;
		}
		else if (button == newDepth0) {
			limit=1;
		}
		else if (button == newDepth1) {
			limit=1;
		}
		
		for (int i=0; i<limit; i++) {
			Point newPoint = Generate.point((Distribution) distributionDropdown.getSelectedItem());
			
			if 		(button == newDepth0) Triangle.addDepth0Point(newPoint);
			else if (button == newDepth1) Triangle.addDepth1Point(newPoint);
			
			triangles = Generate.triangulation(triangles, newPoint);
		}
		
		canvas.setArray(triangles);
		canvas.repaint();
	}
	
	/**
     * This method handles the pressing of a JToggleButton
     * 
     * @param button the button that was pressed
     */
	private void actionOnJToggleButton(JToggleButton button) {
		if (button == pointVisabilityToggle) {
			canvas.togglePaintPointBool();
			canvas.repaint();
		}
		else if (button == triangleVisabilityToggle) {
			canvas.togglePaintTriangleBool();
			canvas.repaint();
		}
		else if (button == circleVisabilityToggle) {
			canvas.togglePaintCircleBool();
			canvas.repaint();
		}
	}
	
	/**
	 * This method handles the pressing of a JMenuItem
	 * 
	 * @param item the item that was selected
	 */
	private void actionOnJMenu(JMenuItem item) {
		if (item == quitItem) {
            System.exit(0);
        }
		else if (item == clearItem) {
			triangles = Generate.triangulation(Generate.points(0, Distribution.even));
            canvas.setArray(triangles);
            canvas.repaint();
        }
	}
	
	/**
	 * Draw the canvas
	 */
	public void drawCanvas() {
		canvas.repaint();
	}
	
	private void initilizeTriangles() {
        triangles = Generate.triangulation(Generate.points(0, Distribution.even));
        canvas.setArray(triangles);
	}
    
}
