package ro.mps.crop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;

import ro.mps.crop.image.CroppableImage;
import ro.mps.properties.Properties;

/**
 * @author radu
 */
@SuppressWarnings("serial")
public class CropScreen extends JComponent {

	private Point mousePt;
	private Rectangle mouseRect = new Rectangle();
	private boolean selecting;
	private JFrame parent;

	private CroppableImage inputImage;

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				JFrame f = new JFrame(Properties.APP_TITLE.getValue());
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				CropScreen g = null;
				try {
					g = new CropScreen(f);
				} catch (IOException e) {
					System.exit(1);
				}

				f.setBounds(0, 0, g.inputImage.asImage().getWidth(null),
						g.inputImage.asImage().getHeight(null));
				f.add(g);
				f.setLocationByPlatform(true);
				f.setVisible(true);
			}
		});
	}

	public CropScreen(JFrame parent) throws IOException {
		this.parent = parent;
		this.setOpaque(true);
		this.addMouseListener(new MouseHandler());
		this.addMouseMotionListener(new MouseMotionHandler());

		inputImage = new CroppableImage("image.jpg");
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(inputImage.asImage().getWidth(null),
				inputImage.asImage().getHeight(null));
	}

	@Override
	public void paintComponent(Graphics g) {
		parent.repaint();

		g.drawImage(inputImage.asImage(), 0, 0, null);

		if (selecting) {
			g.setColor(Color.orange);

			g.drawRect(mouseRect.x, mouseRect.y,
					mouseRect.width, mouseRect.height);
		}
	}

	private class MouseHandler extends MouseAdapter {

		@Override
		public void mouseReleased(MouseEvent e) {

			inputImage.writeCroppedImage("new_image", 
					mouseRect.x, mouseRect.y,
					mouseRect.height, mouseRect.width);

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

}
