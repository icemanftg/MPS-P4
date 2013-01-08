package ro.mps.crop.image;

import java.io.IOException;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCRableImage extends CroppableImage {
	
	private Tesseract instance = Tesseract.getInstance();

	public OCRableImage(String path) throws IOException{
		super(path);
	}
	
	public String getContentOfSelection(int x, int y, int height, int width) {
        //Outside of image
        if (x > img.getWidth() || y > img.getHeight())
            return null;

        if (height + y > img.getHeight())
            height = img.getHeight() - y;

        if (width + x > img.getWidth())
            width = img.getWidth() - x;

        try {
			return instance.doOCR(img.getSubimage(x, y, width, height));
		} catch (TesseractException e) {
			e.printStackTrace();
			return null;
		}
    }
	
}
