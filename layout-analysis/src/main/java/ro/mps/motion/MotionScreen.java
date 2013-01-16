package ro.mps.motion;

import ro.mps.crop.image.CroppableImage;
import ro.mps.crop.image.OCRableImage;
import ro.mps.data.api.HasContent;
import ro.mps.data.api.HasPosition;
import ro.mps.data.base.CompoundNode;
import ro.mps.data.base.Node;
import ro.mps.data.base.OrphanCompoundNode;
import ro.mps.data.concrete.Block;
import ro.mps.data.concrete.Line;
import ro.mps.data.concrete.Root;
import ro.mps.data.parsing.XMLWriter;
import ro.mps.error.exceptions.BadPageNumber;
import ro.mps.error.exceptions.DoenstFitException;
import ro.mps.error.gui.ErrorThrower;
import ro.mps.gui.UserInterface;
import ro.mps.move_resize.RootResizeProcessor;
import ro.mps.properties.Properties;

import javax.swing.*;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

class ControlsFrame extends JFrame {
	    
    public static String[] BLOCK_TYPES = new String[] {"Blocks", "Lines"};
    
    /**
     * Attatched screen for moving
     */
    private MotionScreen attatched;
    
    JTabbedPane panel = new JTabbedPane();
	
	JPanel typePanel = new JPanel();
	JPanel buttonsPanel = new JPanel();
	JPanel resizePanel = new JPanel();
	JPanel movePanel = new JPanel();
	
	ButtonGroup typeGroup = new ButtonGroup();
    
    public ControlsFrame(String title, final MotionScreen attatched) {
		super(title);
		this.attatched = attatched;
		
		ActionListener typeChanged = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				String type = ((JRadioButton)arg0.getSource()).getText();
				if (type.equals("Blocks")){
					attatched.renderBlocks(true);
				} else {
					attatched.renderBlocks(false);
				}
			}
		};
		
		ActionListener editScreen = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
                ((JButton)arg0.getSource()).setEnabled(false);
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        UserInterface layoutGUI = new UserInterface(attatched.getRoot());
                    }
                });
			}
		};
		
		ActionListener up = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				attatched.up();
			}
		};
		
		ActionListener down = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				attatched.down();
			}
		};
		
		ActionListener left = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				attatched.left();
			}
		};
		
		ActionListener right = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				attatched.right();
			}
		};
		
		ActionListener incWidth = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				attatched.incWidth();
			}
		};
		
		ActionListener incHeight = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				attatched.incHeight();
			}
		};
		
		ActionListener decWidth = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				attatched.decWidth();
			}
		};
		
		ActionListener decHeight = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				attatched.decHeight();
			}
		};
		
		ActionListener export = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				XMLWriter writer = new XMLWriter();
				try {
					writer.writeFile(attatched.getRoot(), "exported.xml");
				} catch (TransformerConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		getContentPane().add(top);
    	
    	/**
    	 * Add the block type buttons
    	 */
    	for (String lang : BLOCK_TYPES) {
    		JRadioButton btn = new JRadioButton(lang);
    		typeGroup.add(btn);
    		typePanel.add(btn);
    		if (lang.equals("Blocks"))
    			btn.setSelected(true);
    		btn.addActionListener(typeChanged);
    	}
    	
    	typePanel.setBorder(BorderFactory.createTitledBorder("Granularity"));
    	panel.addTab("Display", typePanel);
    	
    	/**
    	 * Add move panel
    	 */
    	JButton btn = new JButton("Left");
    	movePanel.add(btn);
		btn.addActionListener(left);
		
		btn = new JButton("Right");
    	movePanel.add(btn);
		btn.addActionListener(right);
		
		btn = new JButton("Down");
    	movePanel.add(btn);
		btn.addActionListener(down);
		
		btn = new JButton("Up");
    	movePanel.add(btn);
		btn.addActionListener(up);
    	
    	movePanel.setBorder(BorderFactory.createTitledBorder("Move actions"));
    	panel.addTab("Move", movePanel);
    	
    	/**
    	 * Add resize panel
    	 */
    	btn = new JButton("+ Width");
    	resizePanel.add(btn);
		btn.addActionListener(incWidth);
		
		btn = new JButton("- Width");
		resizePanel.add(btn);
		btn.addActionListener(decWidth);
		
		btn = new JButton("+ Height");
		resizePanel.add(btn);
		btn.addActionListener(incHeight);
		
		btn = new JButton("- Height");
		resizePanel.add(btn);
		btn.addActionListener(decHeight);
    	
    	movePanel.setBorder(BorderFactory.createTitledBorder("Resize actions"));
    	panel.addTab("Resize", resizePanel);
    	
    	/**
    	 * Add actions panel
    	 */
    	btn = new JButton("Content edit");
    	buttonsPanel.add(btn);
		btn.addActionListener(editScreen);
		
		btn = new JButton("Export to XML");
		buttonsPanel.add(btn);
		btn.addActionListener(export);
    	
    	buttonsPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
    	panel.addTab("Actions", buttonsPanel);
    	
    	/**
    	 * Add the whole controls screen
    	 */
    	top.add(panel, BorderLayout.CENTER);
    	add(panel);
	}
    
}

/**
 * @author radu
 */
@SuppressWarnings("serial")
public class MotionScreen extends JComponent {

	private static Color blockColor = Color.BLUE;
	private static Color lineColor = Color.YELLOW;
	private static Color selectedColor = Color.GREEN;
	
    private Point mousePt;
    private JFrame parent;

    private Root root;
    private boolean renderBlocks = true;
    
    private ArrayList<HasPosition> blocks = new ArrayList<HasPosition>();
    private ArrayList<HasPosition> lines = new ArrayList<HasPosition>();
    private HasPosition selected;
    private ArrayList<HasPosition> availableForSelection;
    
    public void renderBlocks(boolean flag){
    	renderBlocks = flag;
    	if (renderBlocks)
    		availableForSelection = blocks;
    	else
    		availableForSelection = lines;
    }
    
    public static void startMotionSCreen(final Root root) {
    	
    EventQueue.invokeLater(new Runnable() {

        public void run() {
        		
	            JFrame f = new JFrame(Properties.APP_TITLE.getValue());
	            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            
	            MotionScreen g = null;
	            try {
	                g = new MotionScreen(f, root);
	            } catch (IOException e) {
	                System.exit(1);
	            }
	
	            JFrame controls = new ControlsFrame("Resize Controls",g);
        		controls.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            
	            f.setBounds(0, 0, root.getWidth(),
	                    root.getHeight());
	            f.setResizable(false);
	            f.add(g);
	            f.setLocationByPlatform(true);
	            f.setVisible(true);
	            controls.setBounds(0,0,400,150);
	            controls.setVisible(true);
        	}
    	});
    
	}
        
    public MotionScreen(JFrame parent, Root root) throws IOException {
        this.parent = parent;
        this.root = root;
        this.setOpaque(true);
        this.addMouseListener(new MouseHandler());
        
        /**
         * Adding all the blocks
         */
        blocks.addAll(root.getChildren());
        
        /**
         * Adding all the lines
         */
        CompoundNode cn = null;
        for (HasPosition hp : blocks) {
        	if (hp instanceof OrphanCompoundNode) {
        		cn = (CompoundNode)hp;
        		lines.addAll(cn.getChildren());
        	}
        }
        
        /**
         * Initially focus is on the blocks
         */
        renderBlocks = true;
        availableForSelection = blocks;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(root.getWidth(),
                root.getHeight());
    }
    
    private void drawBlocks(Collection<HasPosition> blocks, Graphics g) {
    	for (HasPosition hp : blocks) {
    		drawBlock(g, hp);
    	}
    }
    
    private void drawLine(Graphics g, HasPosition hp) {
    	if (hp != selected){
    		g.setColor(lineColor);
    		g.drawRect(hp.getLeftUpperCornerX(),
    				hp.getLeftUpperCornerY(),
    				hp.getWidth(),
    				hp.getHeight());
    		g.setColor(Color.BLACK);
    		g.drawString(((HasContent)hp).getContent(),
    				hp.getLeftUpperCornerX(),
    				hp.getLeftUpperCornerY()+hp.getHeight());
    	}
    }
    
    private void drawBlock(Graphics g, HasPosition hp) {
    	if (hp != selected){
    		g.setColor(blockColor);
    		g.drawRect(hp.getLeftUpperCornerX(),
    				hp.getLeftUpperCornerY(),
    				hp.getWidth(),
    				hp.getHeight());
    	}
    }
    
    private void drawSelected(Graphics g, HasPosition hp) {
    	if (hp == selected && selected != null){
    		g.setColor(selectedColor);
    		g.drawRect(hp.getLeftUpperCornerX(),
    				hp.getLeftUpperCornerY(),
    				hp.getWidth(),
    				hp.getHeight());
    		if (hp instanceof HasContent){
    			g.setColor(Color.GREEN);
    			g.drawString(((HasContent)hp).getContent(),
    					hp.getLeftUpperCornerX(),
    					hp.getLeftUpperCornerY()+hp.getHeight());
    		}
    	}
    }
    
    private void updateSelected(Point click){
    	for (HasPosition hp : availableForSelection){
    		if (hp.inside(click)){
    			selected = hp;
    			return;
    		}
    	}
    	selected = null;
    }
    
    private void drawLines(Collection<HasPosition> lines, Graphics g){
    	for (HasPosition hp : lines) {
    		drawLine(g, hp);
    	}
    }
    
    @Override
    public void paintComponent(Graphics g) {
        parent.repaint();

        /**
         * Draw components on the screen
         */
        drawLines(lines, g);
        drawBlocks(blocks, g);
        drawSelected(g, selected);
        
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
        	
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePt = e.getPoint();
            updateSelected(mousePt);
        }
    }
    
    public Root getRoot(){
    	return root;
    }
    
    /**
     * Size increase
     */
    public void incHeight(){
    	if (selected != null)
			if (selected instanceof Line){
				incHLine(selected);
			} else {
				incHBlock(selected);
			}
    }
    
    public void incWidth(){
    	if (selected != null)
			if (selected instanceof Line){
				incWLine(selected);
			} else {
				incWBlock(selected);
			}
    }
    
    private void incHLine(HasPosition l){
		Line b = (Line)l;
		b.incHeight();
		if (! b.getParent().fits(b))
			b.decHeight();
	}
	
	private void incWLine(HasPosition l){
		Line b = (Line)l;
		b.incWidth();
		if (! b.getParent().fits(b))
			b.decWidth();
	}
	
	private void incHBlock(HasPosition l){
		Block b = (Block)l;
		b.incHeight();
		if (! b.getParent().fits(b))
			b.decHeight();
	}
	
	private void incWBlock(HasPosition l){
		Block b = (Block)l;
		b.incWidth();
		if (! b.getParent().fits(b))
			b.decWidth();
	}
    
    /**
     * Size decrease
     */
    public void decHeight(){
    	if (selected != null)
			if (selected instanceof Line){
				decHLine(selected);
			} else {
				decHBlock(selected);
			}
    }
    
    public void decWidth(){
    	if (selected != null)
			if (selected instanceof Line){
				decWLine(selected);
			} else {
				decWBlock(selected);
			}
    }
    
    private void decHLine(HasPosition l){
		Line b = (Line)l;
		b.decHeight();
	}
	
	private void decWLine(HasPosition l){
		Line b = (Line)l;
		b.decWidth();
	}
	
	private void decHBlock(HasPosition l){
		Block b = (Block)l;
		b.decHeight();
		for (Node c : b.getChildren())
			if (! b.fits(c)){
				incHBlock(l);
			}
	}
	
	private void decWBlock(HasPosition l){
		Block b = (Block)l;
		b.decWidth();
		for (Node c : b.getChildren())
			if (! b.fits(c)){
				incWBlock(l);
			}
	}
    
    /**
     * Up & down movement
     */
	public void down(){
		if (selected != null)
			if (selected instanceof Line){
				downLine(selected);
			} else {
				downBlock(selected);
			}
	}

	public void up(){
		if (selected != null)
			if (selected instanceof Line){
				upLine(selected);
			} else {
				upBlock(selected);
			}
	}
	
	private void downLine(HasPosition l){
		Line b = (Line)l;
		b.down();
		if (! b.getParent().fits(l))
			upLine(l);
	}
	
	private void upLine(HasPosition l){
		Line b = (Line)l;
		b.up();
		if (! b.getParent().fits(l))
			downLine(l);
	}
	
	private void downBlock(HasPosition l){
		Block b = (Block)l;
		b.down();
		for (Node c : b.getChildren())
			c.down();
		if (! root.fits(l))
			upBlock(l);
	}
	
	private void upBlock(HasPosition l){
		Block b = (Block)l;
		b.up();
		for (Node c : b.getChildren())
			c.up();
		if (! root.fits(l))
			downBlock(l);
	}

    
    /**
     * Move on the x axis
     */
	public void left(){
		if (selected != null)
			if (selected instanceof Line){
				leftLine(selected);
			} else {
				leftBlock(selected);
			}
	}

	public void right(){
		if (selected != null)
			if (selected instanceof Line){
				rightLine(selected);
			} else {
				rightBlock(selected);
			}
	}
	
	private void rightLine(HasPosition l){
		Line b = (Line)l;
		b.right();
		if (! b.getParent().fits(l))
			leftLine(l);
	}
	
	private void leftLine(HasPosition l){
		Line b = (Line)l;
		b.left();
		if (! b.getParent().fits(l))
			rightLine(l);
	}
	
	private void rightBlock(HasPosition l){
		Block b = (Block)l;
		b.right();
		for (Node c : b.getChildren())
			c.right();
		if (! root.fits(l))
			leftBlock(l);
	}
	
	private void leftBlock(HasPosition l){
		Block b = (Block)l;
		b.left();
		for (Node c : b.getChildren())
			c.left();
		if (! root.fits(l))
			rightBlock(l);
	}
}
