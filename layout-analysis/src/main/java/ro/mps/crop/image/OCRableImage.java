package ro.mps.crop.image;

import java.io.IOException;
import java.util.Collection;

import ro.mps.data.*;
import ro.mps.data.base.Node;
import ro.mps.data.concrete.Block;
import ro.mps.data.concrete.Line;
import ro.mps.data.concrete.Root;
import ro.mps.error.exceptions.DoenstFitException;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCRableImage extends CroppableImage {
	
	private Tesseract instance = Tesseract.getInstance();
	
	/**
	 * Data structure that is to be built
	 * @param path
	 * @throws IOException
	 */
	private Root tree;
	
	public Collection<Node> getAddedBlocks(){
		return tree.getChildren();
	}
	
	public void resetTree(){
		tree = new Root(img.getWidth(), img.getHeight());
	}

	public OCRableImage(String path) throws IOException{
		super(path);
		tree = new Root(img.getWidth(), img.getHeight());
	}
	
	public void setLanguage(String language){
		instance.setLanguage(language);
	}
	
	public String getContentOfSelectionAsBlock(int x, int y, int height, int width) throws DoenstFitException {
        //Outside of image
        if (x > img.getWidth() || y > img.getHeight())
            return null;

        if (height + y > img.getHeight())
            height = img.getHeight() - y;

        if (width + x > img.getWidth())
            width = img.getWidth() - x;

        try {
			String content = instance.doOCR(img.getSubimage(x, y, width, height));
			
			Block new_block = new Block(tree, x, y, height, width);
			
			String[] lines = content.split("\n");
			
			int line_height = height / lines.length;
			
			for (int i=0, lineY = y; i < lines.length; i++, lineY+=line_height) {
				new_block.addChild(
						new Line(lines[i], new_block, x, lineY, line_height, width));
			}
			
			if (tree.fits(new_block))
				tree.addChild(new_block);
			else {
				System.err.println(new_block.getLeftUpperCornerX() + " " +
					new_block.getLeftUpperCornerY() + " " + new_block.getWidth() + " " + new_block.getHeight()); 
				throw new DoenstFitException();
			}
				
			
			return content;
		} catch (TesseractException e) {
			e.printStackTrace();
			return null;
		}
    }
	
	public Root getBuiltTree(){
		return tree;
	}
}
