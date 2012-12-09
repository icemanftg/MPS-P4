package ro.mps.data.api;

/**
 * Interface implemented by nodes that sit witin a Copound node e.g. Lines within Blocks
 * @author radu
 *
 */
public interface HasParent {

	Compound getParent();
	void setParent(Compound p);
	
}
