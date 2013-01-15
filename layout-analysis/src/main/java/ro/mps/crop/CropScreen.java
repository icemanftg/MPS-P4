package ro.mps.crop;


import ro.mps.crop.image.OCRableImage;
import ro.mps.data.base.Node;
import ro.mps.data.concrete.Root;
import ro.mps.error.exceptions.BadPageNumber;
import ro.mps.error.exceptions.DoenstFitException;
import ro.mps.properties.Properties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

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
