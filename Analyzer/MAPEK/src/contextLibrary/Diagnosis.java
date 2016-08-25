package contextLibrary;
/**
 * @author lcastane[at]icesi.edu.co Date: (05/2011)
 * @version 1.0
 * */


@SuppressWarnings("serial")
public class Diagnosis implements IContextModel{

	/**
	 * Constrains
	 */
	public final static int EVENT_TYPE = 1;
	
	/**
	 * Attributes
	 */
	private boolean adaptation;
	private ContextEntity desiredEntity;
	private ContextEntity currentEntity;
	
	/**
	 * Constructor
	 * @param adaptation is true if is required
	 * @param currentEntity is the contextEntity that requires adaptation with the current state
	 * @param desiredEntity is the expected state of the entity to adapt 
	 */
	public Diagnosis(boolean adaptation, ContextEntity desiredEntity, ContextEntity currentEntity) {
		this.adaptation = adaptation;
		this.desiredEntity = desiredEntity;
		this.currentEntity = currentEntity;
	}

	public boolean isAdaptation() {
		return adaptation;
	}

	public void setAdaptation(boolean adaptation) {
		this.adaptation = adaptation;
	}

	public ContextEntity getDesiredEntity() {
		return desiredEntity;
	}

	public void setDesiredEntity(ContextEntity desiredEntity) {
		this.desiredEntity = desiredEntity;
	}

	public ContextEntity getCurrentEntity() {
		return currentEntity;
	}

	public void setCurrentEntity(ContextEntity currentEntity) {
		this.currentEntity = currentEntity;
	}

	
	
	
}
