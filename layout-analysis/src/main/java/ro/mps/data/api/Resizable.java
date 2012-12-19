package ro.mps.data.api;


public interface Resizable extends Moveable {

    /**
     * Resizes the current node by added the provided params to its previous isze
     *
     * @param deltaH
     * @param deltaW
     */
    void resize(int deltaH, int deltaW);


}
