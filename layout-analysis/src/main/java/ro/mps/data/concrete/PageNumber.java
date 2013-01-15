package ro.mps.data.concrete;

import ro.mps.data.api.Compound;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 1/15/13
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
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
