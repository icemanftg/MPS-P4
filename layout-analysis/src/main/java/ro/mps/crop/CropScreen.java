package ro.mps.crop;

import ro.mps.crop.image.CroppableImage;
import ro.mps.crop.image.OCRableImage;
import ro.mps.data.base.Node;
import ro.mps.data.concrete.Root;
import ro.mps.data.parsing.XMLWriter;
import ro.mps.error.exceptions.BadPageNumber;
import ro.mps.error.exceptions.DoenstFitException;
import ro.mps.error.gui.ErrorThrower;
import ro.mps.gui.UserInterface;
import ro.mps.motion.MotionScreen;
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
import java.util.Collection;
import java.util.LinkedList;

class ControlsFrame extends JFrame {
	    
    private static int LANG_MENU_HEIGHT = 100;
    
    public static String[] LANGS = new String[] {"eng", "deu"};
    public static String[] BLOCK_TYPES = new String[] {"Image", "Paragraph", "Page Number"};
    
    private CropScreen attatched;
    JTabbedPane panel = new JTabbedPane();
	
	JPanel langPanel = new JPanel();
	JPanel blockPanel = new JPanel();
	JPanel buttonsPanel = new JPanel();
	
	String blockType;
	
	ButtonGroup langGroup = new ButtonGroup();
	ButtonGroup blockTypeGroup = new ButtonGroup();
    
    public ControlsFrame(String title, final CropScreen attatched) {
		super(title);
		this.attatched = attatched;
		
    	ActionListener langChanged = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				ControlsFrame.this.attatched.setLanguage(((JRadioButton)arg0.getSource()).getText());
			}
		};
		
		ActionListener blockChanged = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				blockType = ((JRadioButton)arg0.getSource()).getText();
				if (blockType.equals("Image"))
					attatched.renderingColor = Color.RED;
				else if (blockType.equals("Paragraph"))
					attatched.renderingColor = Color.GREEN;
				else
					attatched.renderingColor = Color.ORANGE;
					
			}
		};
		
		ActionListener reset = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				attatched.reset();
			}
		};
		
		ActionListener next = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MotionScreen.startMotionSCreen(attatched.getRoot());
//                System.out.println("Dechid ecrane");
//                EventQueue.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        UserInterface layoutGUI = new UserInterface(attatched.getRoot());
//                    }
//                });
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
		 * Adding the lang buttons
		 */
    	for (String lang : LANGS) {
    		JRadioButton btn = new JRadioButton(lang);
    		langGroup.add(btn);
    		langPanel.add(btn);
    		if (lang.equals("eng"))
    			btn.setSelected(true);
    		btn.addActionListener(langChanged);
    	}
    	
    	/**
    	 * Add the block type buttons
    	 */
    	for (String lang : BLOCK_TYPES) {
    		JRadioButton btn = new JRadioButton(lang);
    		blockTypeGroup.add(btn);
    		blockPanel.add(btn);
    		if (lang.equals("Paragraph"))
    			btn.setSelected(true);
    		btn.addActionListener(blockChanged);
    	}
    	
    	langPanel.setBorder(BorderFactory.createTitledBorder("Choose text language"));
    	panel.addTab("Language Settings", langPanel);
    	
    	blockPanel.setBorder(BorderFactory.createTitledBorder("Choose block type"));
    	panel.addTab("Block Type", blockPanel);
    	
    	JButton btn = new JButton("Ok");
    	buttonsPanel.add(btn);
		btn.addActionListener(next);
		
		btn = new JButton("Reset");
		buttonsPanel.add(btn);
		btn.addActionListener(reset);
		
		btn = new JButton("Export to XML");
		buttonsPanel.add(btn);
		btn.addActionListener(export);
    	
    	buttonsPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
    	panel.addTab("Actions", buttonsPanel);
    	top.add(panel, BorderLayout.CENTER);
    	add(panel);
	}
    
}

/**
 * @author radu
 */
@SuppressWarnings("serial")
public class CropScreen extends JComponent {

    private Point mousePt;
    private Rectangle mouseRect = new Rectangle();
    private boolean selecting;
    private JFrame parent;
    Color renderingColor = Color.GREEN;

    private OCRableImage inputImage;
    
    public static void startOCRSCreen(final String pathToImage) {
    	
    EventQueue.invokeLater(new Runnable() {

        public void run() {
        		
	            JFrame f = new JFrame(Properties.APP_TITLE.getValue());
	            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            
	            CropScreen g = null;
	            try {
	                g = new CropScreen(f, pathToImage);
	            } catch (IOException e) {
	                System.exit(1);
	            }
	
	            JFrame controls = new ControlsFrame("OCR Controlls",g);
        		controls.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            
	            f.setBounds(0, 0, g.inputImage.asImage().getWidth(null),
	                    g.inputImage.asImage().getHeight(null));
	            f.add(g);
	            f.setLocationByPlatform(true);
	            f.setVisible(true);
	            controls.setBounds(0,0,300,200);
	            controls.setVisible(true);
        	}
    	});
    
	}
    
    public static void main(String[] args) throws Exception {
        startOCRSCreen("image_samples/test.tif");
    }
        
    public CropScreen(JFrame parent, String pathToImage) throws IOException {
        this.parent = parent;
                
        this.setOpaque(true);
        this.addMouseListener(new MouseHandler());
        this.addMouseMotionListener(new MouseMotionHandler());

        inputImage = new OCRableImage(pathToImage);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(inputImage.asImage().getWidth(null),
                inputImage.asImage().getHeight(null));
    }

    public void setLanguage(String lang){
    	if (inputImage != null)
    		inputImage.setLanguage(lang);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        parent.repaint();

        g.drawImage(inputImage.asImage(), 0, 0, null);

        if (selecting) {
            g.setColor(renderingColor);

            g.drawRect(mouseRect.x, mouseRect.y,
                    mouseRect.width, mouseRect.height);
        }
        
        g.setColor(Color.blue);
        for (Node block : inputImage.getAddedBlocks()){
        	g.drawRect(block.getLeftUpperCornerX(), block.getLeftUpperCornerY(),
        			block.getWidth(), block.getHeight());
        }
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {

        	try {
        		if (renderingColor.equals(Color.GREEN)) {
        			System.out.println(
	        			inputImage.getContentOfSelectionAsBlock(
	        					mouseRect.x, mouseRect.y,
	        					mouseRect.height, mouseRect.width));
        		} else if (renderingColor.equals(Color.RED)) {
        			System.out.println(
    	        			inputImage.getContentOfSelectionAsImageBlock(
    	        					mouseRect.x, mouseRect.y,
    	        					mouseRect.height, mouseRect.width));
        		} else {
        			System.out.println(
    	        			inputImage.getContentOfSelectionAsPageNumberBlock(
    	        					mouseRect.x, mouseRect.y,
    	        					mouseRect.height, mouseRect.width));
        		}
        	} catch (DoenstFitException ex) {
        		//Error handling code here
        	} catch (BadPageNumber pn) {
        		
        	}

            selecting = false;
            mouseRect.setBounds(0, 0, 0, 0);

            e.getComponent().repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePt = e.getPoint();
            selecting = true;
            e.getComponent().repaint();
        }
    }

    private class MouseMotionHandler extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            if (selecting) {
                mouseRect.setBounds(
                        Math.min(mousePt.x, e.getX()),
                        Math.min(mousePt.y, e.getY()),
                        Math.abs(mousePt.x - e.getX()),
                        Math.abs(mousePt.y - e.getY()));
            }
            e.getComponent().repaint();
        }
    }
    
    public Root getRoot(){
    	return inputImage.getBuiltTree();
    }
    
    public void reset(){
    	inputImage.resetTree();
    }

}
