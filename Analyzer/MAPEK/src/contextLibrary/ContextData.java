package contextLibrary;

/**
 * @author lcastane[at]icesi.edu.co Date: (05/2011)
 * @version 1.0
 * */

@SuppressWarnings("serial")
public class ContextData implements IContextModel {
	/**
	 * Constrains
	 */
	public final static int TYPE_EVENT_INVOKE = 21;
	public final static int TYPE_EVENT_OCURRENCE = 22;
	public final static int TYPE_INFO_STRING_MSG = 111;

	/**
	 * Attributes
	 */
	private ContextEntity entity;
	private int observationType;
	private ContextProperty observableProperty;

	/**
	 * Constructor
	 * 
	 * @param entity
	 * @param observationType
	 * @param observableProperty
	 */
	public ContextData(ContextEntity entity, int observationType,
			ContextProperty observableProperty) {
		this.entity = entity;
		this.observationType = observationType;
		this.observableProperty = observableProperty;
	}

	public int getObservationType() {
		return observationType;
	}

	public void setObservationType(int observationType) {
		this.observationType = observationType;
	}

	public ContextProperty getObservableProperty() {
		return observableProperty;
	}

	public void setObservableProperty(ContextProperty observableProperty) {
		this.observableProperty = observableProperty;
	}

	public ContextEntity getEntity() {
		return entity;
	}

	public void setEntity(ContextEntity entity) {
		this.entity = entity;
	}

}
