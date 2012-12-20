package ro.mps.data.api;

import ro.mps.data.base.Node;

import java.util.List;

/**
 * Impemented by nodes that have children
 *
 * @author radu
 */
public interface Compound<T extends Node> {

    /**
     * Returns the children list
     *
     * @return
     */
    List<T> getChildren();

    void removeChildren(List<T> children);

    void removeChild(T child);
}
