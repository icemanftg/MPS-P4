package ro.mps.smart.textbox;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.MouseInputListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;

/**
 *
 * @author alexandra
 */
public class SmartTextBox extends Box  {
//implements MouseInputListener {

    private JTextArea textArea;
    private ComponentResizer resizer;
    private ComponentMover mover;
    private String content, visibleContent;
    private JLabel jl;
    private String[] lines;
    
//    private final static Dimension MINIMUM_SIZE = new Dimension(10, 10);
//	private final static Dimension MAXIMUM_SIZE =
//			new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
//	private static Map<Integer, Integer> cursors = new HashMap<Integer, Integer>();
//
//	static {
//		cursors.put(1, Cursor.N_RESIZE_CURSOR);
//		cursors.put(2, Cursor.W_RESIZE_CURSOR);
//		cursors.put(4, Cursor.S_RESIZE_CURSOR);
//		cursors.put(8, Cursor.E_RESIZE_CURSOR);
//		cursors.put(3, Cursor.NW_RESIZE_CURSOR);
//		cursors.put(9, Cursor.NE_RESIZE_CURSOR);
//		cursors.put(6, Cursor.SW_RESIZE_CURSOR);
//		cursors.put(12, Cursor.SE_RESIZE_CURSOR);
//	}
//	private Insets dragInsets;
//	private Dimension snapSize;
//	private int direction;
//	protected static final int NORTH = 1;
//	protected static final int WEST = 2;
//	protected static final int SOUTH = 4;
//	protected static final int EAST = 8;
//	private Cursor sourceCursor;
//	private boolean resizing;
//	private Rectangle bounds;
//	private Point pressed;
//	private boolean autoscrolls;
//	private Dimension minimumSize = MINIMUM_SIZE;
//	private Dimension maximumSize = MAXIMUM_SIZE;

    public SmartTextBox(int w, int h) {
        super(BoxLayout.Y_AXIS);
        setLocation(new Point(0, 0));

        Container cp = new Container();
        cp.setLayout(new FlowLayout(FlowLayout.RIGHT));

        jl = new JLabel("\n");
        cp.add(jl);
        add(cp);

        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(w, h - 20));
        textArea.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                content = textArea.getText();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                content = textArea.getText();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                content = textArea.getText();
            }
        });

        add(textArea);

//        dragInsets = new Insets(5, 5, 5, 5);
//        snapSize = new Dimension(1, 1);
        
//        addMouseListener(this);
//        addMouseMotionListener(this);
//        
        setPreferredSize(new Dimension(w, h));
        setBorder(BorderFactory.createLineBorder(Color.black));

        resizer = new ComponentResizer(this);
        mover = new ComponentMover(this, cp);
    }

    public void handleResize() {
//        try {
            textArea.setPreferredSize(new Dimension(getWidth(), getHeight() - jl.getHeight()));
            textArea.setMaximumSize(textArea.getPreferredSize());
            // textArea.setBounds(getLocation().x, getLocation().y+20, getSize().width, getSize().height);
            setBorder(BorderFactory.createLineBorder(Color.black));
            //  textArea.setBorder(getBorder());

            textArea.setMargin(new Insets(0, 0, 0, 0));

//            Rectangle r = textArea.getVisibleRect();
//            Font font = textArea.getFont();
//            int h = getGraphics().getFontMetrics(font).getAscent();
//            int nLines = textArea.getText().split("\n").length;
//
//            System.out.println(nLines + " lines ");
//
//            Point l0 = new Point(r.x, r.y), r0 = new Point(r.x + r.width, h + r.y);
//            int s0 = textArea.viewToModel(l0), o0 = textArea.viewToModel(r0) - s0;
//
//            if (getLocation().y + getHeight() <= getLocation().y + jl.getHeight() + h * nLines) {
//                //textArea.setRows(nLines-1);
//                System.out.println((getLocation().y + getHeight()) + " " + "*** " + (getLocation().y + jl.getHeight() + h * nLines));
//            }
//
//            String t = "";
//
//
//            for (int i = 0; i < nLines; ++i) {
//                Point topLeft = new Point(r.x, r.y + i * h);
//                Point bottomRight = new Point(r.x + r.width, r.y + (i + 1) * h);
//                int start = textArea.viewToModel(topLeft), end = textArea.viewToModel(bottomRight);
//                //System.out.println(i + " : (" + start + " - " + (end - start) + ") : " + textArea.getText(start, end - start) + "\n");
//                t += "\n" + textArea.getText(start, end - start);
//                /*
//                 System.out.println(i + " : (" + s0 + " - " + (s0+o0) + ") : " + textArea.getText(s0, o0));
//                 s0 += o0+1;
//                 */
//            }
            //System.out.println(t + "\n//////////");
            /*
            for(String s : textArea.getText().split("\n")) {
                System.out.println(s.substring(0, Math.min(s.length(), maxChars)));
            }
            */
            for(String l : lines) {
                System.out.println(l);
            }

//        } catch (BadLocationException ex) {
//            Logger.getLogger(SmartTextBox.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @Override
    public void validate() {
        Graphics g = getGraphics();
        
        final FontMetrics fontMetrics = g.getFontMetrics();

        String[] allLines = content.split("\n");
        int allLinesN = allLines.length;
        int nLines = 0;
        
        int h = 0;
        for(int i = 0; i < allLinesN; ++i) {
            h += fontMetrics.getHeight();
            if(h <= textArea.getPreferredSize().height) {
                ++nLines;
            } else {
                break;
            }
        }
        
        
        lines = new String[nLines];
        visibleContent = "";
        
        for(int i = 0; i < lines.length; ++i) {
            String l = "";
            String lin = allLines[i];
            int w = 0;
            for(char c : lin.toCharArray()) {
                w += fontMetrics.charWidth(c);
                if(w <= textArea.getPreferredSize().width) {
                    l += c;
                } else {
                    break;
                }
            }
            lines[i] = l;
            visibleContent += l + "\n";
        }
        
        textArea.setText(visibleContent.trim());
    }
    
//    @Override
//    public void paint(Graphics g) {
//        final FontMetrics fontMetrics = g.getFontMetrics();
//
//        String[] allLines = content.split("\n");
//        int allLinesN = allLines.length;
//        int nLines = 0;
//        
//        int h = 0;
//        for(int i = 0; i < allLinesN; ++i) {
//            h += fontMetrics.getHeight();
//            if(h <= textArea.getPreferredSize().height) {
//                ++nLines;
//            } else {
//                break;
//            }
//        }
//        
//        
//        lines = new String[nLines];
//        visibleContent = "";
//        
//        for(int i = 0; i < lines.length; ++i) {
//            String l = "";
//            String lin = allLines[i];
//            int w = 0;
//            for(char c : lin.toCharArray()) {
//                w += fontMetrics.charWidth(c);
//                if(w <= textArea.getPreferredSize().width) {
//                    l += c;
//                } else {
//                    break;
//                }
//            }
//            lines[i] = l;
//            visibleContent += l + "\n";
//        }
//        
//        textArea.setText(visibleContent.trim());
//        
//        super.paint(g);
//        
//    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public ComponentResizer getResizer() {
        return resizer;
    }

    public void setResizer(ComponentResizer resizer) {
        this.resizer = resizer;
    }

    public ComponentMover getMover() {
        return mover;
    }

    public void setMover(ComponentMover mover) {
        this.mover = mover;
    }

    public String getText() {
        return textArea.getText();
    }

    public void setText(String text) {
        textArea.setText(text);
        content = text;
    }

    public String[] getLines() {
        return lines;
    }

    public String[] getWords() {
        ArrayList<String> words = new ArrayList<String>();
        for(String line : lines) {
            Collections.addAll(words, line.split(" ,.:;()[]/"));
        }
        return words.toArray(new String[words.size()]);
    }
    
//    	@Override
//	public void mouseMoved(MouseEvent e) {
//		Component source = e.getComponent();
//		Point location = e.getPoint();
//		direction = 0;
//
//		if (location.x < dragInsets.left) {
//			direction += WEST;
//		}
//
//		if (location.x > source.getWidth() - dragInsets.right - 1) {
//			direction += EAST;
//		}
//
//		if (location.y < dragInsets.top) {
//			direction += NORTH;
//		}
//
//		if (location.y > source.getHeight() - dragInsets.bottom - 1) {
//			direction += SOUTH;
//		}
//
//		//  Mouse is no longer over a resizable border
//
//		if (direction == 0) {
//			source.setCursor(sourceCursor);
//		} else // use the appropriate resizable cursor
//		{
//			int cursorType = cursors.get(direction);
//			Cursor cursor = Cursor.getPredefinedCursor(cursorType);
//			source.setCursor(cursor);
//		}
//	}
//
//	@Override
//	public void mouseEntered(MouseEvent e) {
//		if (!resizing) {
//			Component source = e.getComponent();
//			sourceCursor = source.getCursor();
//		}
//	}
//
//	@Override
//	public void mouseExited(MouseEvent e) {
//		if (!resizing) {
//			Component source = e.getComponent();
//			source.setCursor(sourceCursor);
//		}
//	}
//
//	@Override
//	public void mousePressed(MouseEvent e) {
//		//	The mouseMoved event continually updates this variable
//
//		if (direction == 0) {
//			return;
//		}
//
//		//  Setup for resizing. All future dragging calculations are done based
//		//  on the original bounds of the component and mouse pressed location.
//
//		resizing = true;
//
//		Component source = e.getComponent();
//		pressed = e.getPoint();
//		SwingUtilities.convertPointToScreen(pressed, source);
//		bounds = source.getBounds();
//
//		//  Making sure autoscrolls is false will allow for smoother resizing
//		//  of components
//
//		if (source instanceof JComponent) {
//			JComponent jc = (JComponent) source;
//			autoscrolls = jc.getAutoscrolls();
//			jc.setAutoscrolls(false);
//		}
//	}
//
//	@Override
//	public void mouseReleased(MouseEvent e) {
//		resizing = false;
//
//		Component source = e.getComponent();
//		source.setCursor(sourceCursor);
//                
//		if (source instanceof JComponent) {
//			((JComponent) source).setAutoscrolls(autoscrolls);
//		}
//	}
//
//	
//	@Override
//	public void mouseDragged(MouseEvent e) {
//		if (resizing == false) {
//			return;
//		}
//
//		Component source = e.getComponent();
//		Point dragged = e.getPoint();
//		SwingUtilities.convertPointToScreen(dragged, source);
//
//		changeBounds(source, direction, bounds, pressed, dragged);
//	}
//
//	protected void changeBounds(Component source, int direction, Rectangle bounds, Point pressed, Point current) {
//		//  Start with original locaton and size
//
//		int x = bounds.x;
//		int y = bounds.y;
//		int width = bounds.width;
//		int height = bounds.height;
//
//		//  Resizing the West or North border affects the size and location
//
//		if (WEST == (direction & WEST)) {
//			int drag = getDragDistance(pressed.x, current.x, snapSize.width);
//			int maximum = Math.min(width + x, maximumSize.width);
//			drag = getDragBounded(drag, snapSize.width, width, minimumSize.width, maximum);
//
//			x -= drag;
//			width += drag;
//		}
//
//		if (NORTH == (direction & NORTH)) {
//			int drag = getDragDistance(pressed.y, current.y, snapSize.height);
//			int maximum = Math.min(height + y, maximumSize.height);
//			drag = getDragBounded(drag, snapSize.height, height, minimumSize.height, maximum);
//
//			y -= drag;
//			height += drag;
//		}
//
//		//  Resizing the East or South border only affects the size
//
//		if (EAST == (direction & EAST)) {
//			int drag = getDragDistance(current.x, pressed.x, snapSize.width);
//			Dimension boundingSize = getBoundingSize(source);
//			int maximum = Math.min(boundingSize.width - x, maximumSize.width);
//			drag = getDragBounded(drag, snapSize.width, width, minimumSize.width, maximum);
//			width += drag;
//		}
//
//		if (SOUTH == (direction & SOUTH)) {
//			int drag = getDragDistance(current.y, pressed.y, snapSize.height);
//			Dimension boundingSize = getBoundingSize(source);
//			int maximum = Math.min(boundingSize.height - y, maximumSize.height);
//			drag = getDragBounded(drag, snapSize.height, height, minimumSize.height, maximum);
//			height += drag;
//		}
//
//		source.setBounds(x, y, width, height);
//		source.setPreferredSize(new Dimension(width, height));
//		source.setLocation(x, y);
//		source.validate();
//                
//                if(source instanceof SmartTextBox) {
//                    ((SmartTextBox)source).handleResize();
//                }
//
//	}
//
//	private int getDragDistance(int larger, int smaller, int snapSize) {
//		int halfway = snapSize / 2;
//		int drag = larger - smaller;
//		drag += (drag < 0) ? -halfway : halfway;
//		drag = (drag / snapSize) * snapSize;
//
//		return drag;
//	}
//
//	private int getDragBounded(int drag, int snapSize, int dimension, int minimum, int maximum) {
//		while (dimension + drag < minimum) {
//			drag += snapSize;
//		}
//
//		while (dimension + drag > maximum) {
//			drag -= snapSize;
//		}
//
//
//		return drag;
//	}
//
//	private Dimension getBoundingSize(Component source) {
//		if (source instanceof Window) {
//			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
//			Rectangle bounds = env.getMaximumWindowBounds();
//			return new Dimension(bounds.width, bounds.height);
//		} else {
//			return source.getParent().getSize();
//		}
//	}
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
//    }

}