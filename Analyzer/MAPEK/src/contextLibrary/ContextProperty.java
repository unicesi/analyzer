package contextLibrary;

/**
 * @author lcastane[at]icesi.edu.co Date: (05/2011)
 * @version 1.0
 * */
@SuppressWarnings("serial")
public class ContextProperty implements IContextModel {

	/**
	 * Constrains
	 */
	public final static int TYPE_STRING = 111;
	public final static int TYPE_INTEGER = 112;
	public final static int TYPE_DOUBLE = 113;
	public final static int TYPE_TIME_SECONDS = 121;
	public final static int TYPE_COUNT = 132;
	public final static int TYPE_PERCENT = 133;
	public final static int TYPE_INVOKE = 21;
	public final static int TYPE_OCURRENCE = 22;

	/**
	 * Attributes
	 */
	private int type;
	private String propertyName;
	private Object value;

	/**
	 * Constructor
	 * 
	 * @param type
	 * @param propertyName
	 * @param value
	 */
	public ContextProperty(int type, String propertyName, Object value) {
		this.type = type;
		this.propertyName = propertyName;
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		ContextProperty property = (ContextProperty) obj;
		if (property.getPropertyName().equals(propertyName)
				&& property.getType() == type)
			return true;
		return false;
	}

}
