package ro.mps.motion;

import ro.mps.crop.image.CroppableImage;
import ro.mps.crop.image.OCRableImage;
import ro.mps.data.api.HasPosition;
import ro.mps.data.base.CompoundNode;
import ro.mps.data.base.Node;
import ro.mps.data.base.OrphanCompoundNode;
import ro.mps.data.concrete.Block;
import ro.mps.data.concrete.Root;
import ro.mps.data.parsing.XMLWriter;
import ro.mps.error.exceptions.BadPageNumber;
import ro.mps.error.exceptions.DoenstFitException;
import ro.mps.error.gui.ErrorThrower;
import ro.mps.move_resize.RootResizeProcessor;
import ro.mps.properties.Properties;

import javax.sound.sampled.Line;
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
		btn.addActionListener(null);
		
		btn = new JButton("Right");
    	movePanel.add(btn);
		btn.addActionListener(null);
		
		btn = new JButton("Down");
    	movePanel.add(btn);
		btn.addActionListener(null);
		
		btn = new JButton("Up");
    	movePanel.add(btn);
		btn.addActionListener(null);
    	
    	movePanel.setBorder(BorderFactory.createTitledBorder("Move actions"));
    	panel.addTab("Move", movePanel);
    	
    	/**
    	 * Add resize panel
    	 */
    	btn = new JButton("+ Width");
    	resizePanel.add(btn);
		btn.addActionListener(null);
		
		btn = new JButton("- Width");
		resizePanel.add(btn);
		btn.addActionListener(null);
		
		btn = new JButton("+ Height");
		resizePanel.add(btn);
		btn.addActionListener(null);
		
		btn = new JButton("- Height");
		resizePanel.add(btn);
		btn.addActionListener(null);
    	
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
	
	            JFrame controls = new ControlsFrame("Resize Controlls",g);
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
    	if (hp != selected){
    		g.setColor(selectedColor);
    		g.drawRect(hp.getLeftUpperCornerX(),
    				hp.getLeftUpperCornerY(),
    				hp.getWidth(),
    				hp.getHeight());
    	}
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
        
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
        	
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePt = e.getPoint();
        }
    }
    
    public Root getRoot(){
    	return root;
    }

}
