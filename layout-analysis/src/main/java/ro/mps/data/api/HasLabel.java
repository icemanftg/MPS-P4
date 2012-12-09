package ro.mps.data.api;

/**
 * 
 * Interface for keeping track of the mapping between XML nodes
 * and our data stucture nodes.
 * 
 * @author radu
 *
 */
public interface HasLabel {

	String getLabel();
	void setLabel(String newLabel);
	
}
