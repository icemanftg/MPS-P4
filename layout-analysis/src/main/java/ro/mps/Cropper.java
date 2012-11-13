
import draw.GraphPanel;
import java.awt.EventQueue;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 * @see http://stackoverflow.com/a/11944233/230513
 */
public class Cropper extends JPanel {

    public Cropper() {
        super(new GridLayout());
        
        //JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        GraphPanel graphPanel = new GraphPanel();      
        //split.setTopComponent(new JScrollPane(graphPanel));
        //split.setBottomComponent(graphPanel.getControlPanel());
        
        this.add(graphPanel);
        this.add(graphPanel.getControlPanel());
    }

    private void display() {
    	
        JFrame f = new JFrame("SplitGraph");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Cropper().display();
            }
        });
    }
}
