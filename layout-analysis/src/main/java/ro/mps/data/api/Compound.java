package ro.mps.data.api;

import java.util.List;

import ro.mps.data.base.Node;

/**
 * Impemented by nodes that have children
 * @author radu
 *
 */
public interface Compound {
	
	/**
	 * Returns the children list
	 * @return
	 */
	List<Node> getChildren();

}
