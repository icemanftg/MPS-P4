package ro.mps.data.api;

import ro.mps.data.base.Node;

import java.util.List;

/**
 * Impemented by nodes that have children
 *
 * @author radu
 */
public interface Compound {

    /**
     * Returns the children list
     *
     * @return
     */
    List<Node> getChildren();

    /**
     * Checks if the current element overlaps the other
     * 
     * @param p
     * @return
     */
    boolean fits(HasPosition p);
}
