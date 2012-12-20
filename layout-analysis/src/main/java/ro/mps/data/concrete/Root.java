package ro.mps.data.concrete;

import ro.mps.data.base.OrphanCompoundNode;

public class Root extends OrphanCompoundNode {

    public Root(int width, int height) {
        super("Document", 0, 0, height, width);
    }

}
