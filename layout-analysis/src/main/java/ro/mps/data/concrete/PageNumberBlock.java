package ro.mps.data.concrete;

import ro.mps.data.api.Compound;

public class PageNumberBlock extends Block {
	public PageNumberBlock(int x, int y, int height, int width) {
        super(x, y, height, width);
    }

    public PageNumberBlock(Compound parent, int x, int y, int height, int width) {
        super(parent, x, y, height, width);
    }
    
    public boolean isPageNumber(){
    	return true;
    }
    
    public String pageNumber(){
    	if (getChildren().size() > 0) {
    		Line number = (Line)(getChildren().get(0));
    		return number.getContent();
    	} else {
    		return null;
    	}
    }
    
    public void setPageNumber(String number){
    	if (getChildren().size() > 0) {
    		Line lnumber = (Line)(getChildren().get(0));
    		lnumber.setContent(number);
    	}
    }
}
