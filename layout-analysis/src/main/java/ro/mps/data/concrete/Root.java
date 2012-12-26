package ro.mps.data.concrete;

import ro.mps.data.base.OrphanCompoundNode;

public class Root extends OrphanCompoundNode {

    private String imageName;
    private String direction;


    public String getImageName() {
        return imageName;
    }

    public String getDirection() {
        return direction;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Root(int width, int height) {
        super("Document", 0, 0, height, width);
    }


}
