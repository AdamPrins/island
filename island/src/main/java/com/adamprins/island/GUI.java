package com.adamprins.island;

import java.awt.*;
import javax.swing.*;

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
 * @version 0.1.0 
 * 		Initial Build
 *		
 */
public class GUI implements ActionListener {
	
	
	/* JMenu File items */
    private JMenuItem quitItem;
    
    /* The output fields */
    private JLabel outputStatic;
    private JLabel output;
    
    /* The JButtons */
    private JButton newPointButton;
    private JButton circleVisabilityToggle;
    
    /* The canvas where the drawing is performed */
    private Canvas canvas;
    
    /* The size of the canvas in terms of grid bag coordinates */
    private static final int CANVASE_SIZE = 10;
    
    private ArrayList<Triangle> triangles;
    private ArrayList<Point> points;
    
    /**
     * Constructor of the GUI 
     * Initializes the frame and configures the layouts
     * Sets all listeners
     */
	public GUI() {
		JFrame frame = new JFrame("JumpIN"); 
	    Container contentPane = frame.getContentPane(); 
	    contentPane.setLayout(new GridBagLayout());
	    // get the content pane so we can put stuff in
	    
	    createMenus(frame);
	    createCanvasPanel(contentPane);
	    createPanelSpacing(contentPane);
	    createInterfacePanel(contentPane);
	    
	    frame.setPreferredSize(new Dimension(900,700));
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
	    c.ipadx = 100; 				c.ipady = CANVASE_SIZE;	//c.ipadx fully controls the space between the left and the canvas
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
	    
	    newPointButton = new JButton("New Point");
	    newPointButton.addActionListener(this);
	    c.gridx = 0;			c.gridy = 3;
	    interfacePanel.add(newPointButton,c);
	    
	    circleVisabilityToggle = new JButton("Circle Visibility");
	    circleVisabilityToggle.addActionListener(this);
	    c.gridx = 1;			c.gridy = 3;
	    interfacePanel.add(circleVisabilityToggle,c);
	    
	    outputStatic = new JLabel("Output: ");
	    c.gridx = 0;			c.gridy = 5;
	    interfacePanel.add(outputStatic,c);
	    
	    c.gridx = 0;			c.gridy = 6;
	    c.weightx=1;
	    c.gridwidth=4;
	    output = new JLabel(" ");
	    output.setPreferredSize(new Dimension(150,30));
	    interfacePanel.add(output,c);
	    
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
        else if (o instanceof JMenuItem){ // it's a JMenuItem
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
		if (button == newPointButton) {
			Point newPoint = Generate.point();
			triangles = Generate.triangulation(triangles, newPoint);
			canvas.setArray(triangles);
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
	}
	
	/**
	 * Draw the canvas
	 */
	public void drawCanvas() {
		canvas.repaint();
	}
	
	private void initilizeTriangles() {
        triangles = Generate.triangulation(Generate.points(0));
        canvas.setArray(triangles);
	}
    
}
