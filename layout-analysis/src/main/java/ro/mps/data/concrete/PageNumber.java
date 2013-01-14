package ro.mps.data.concrete;

import ro.mps.data.api.Compound;

public class PageNumber extends Line {
	
	private void setLabel(){
		label = "PageNumber";
	}
	
	public PageNumber(int x, int y, int height, int width) {
        super(x, y, height, width);
    }

    public PageNumber(Compound p, int x, int y, int height, int width) {
        super(p, x, y, height, width);
    }

    public PageNumber(String content, Compound p, int x, int y, int height, int width) {
        super(content, p, x, y, height, width);
    }

}
