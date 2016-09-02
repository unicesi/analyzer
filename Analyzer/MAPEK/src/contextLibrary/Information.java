package contextLibrary;

/**
 * @author lcastane[at]icesi.edu.co Date: (05/2011)
 * @version 1.0
 * */
@SuppressWarnings("serial")
public class Information implements IContextModel {

	/**
	 * Constrains
	 */
	// stores result
	public final static int TYPE_INFO_STRING = 111;
	public final static int TYPE_INFO_INT = 112;
	public final static int TYPE_INFO_DOUBLE = 113;

	// requires operation to store the result
	public final static int TYPE_SUM = 121;
	public final static int TYPE_COUNT = 122;
	public final static int TYPE_INVOKE = 21;
	public final static int TYPE_OCURRENCE = 22;

	/**
	 * Attributes
	 */
	private int type;
	private String qosPropertyName;
	private ContextProperty result;
	private ContextEntity entity;

	/**
	 * Constructor
	 * 
	 * @param type
	 *            of operation
	 * @param qosPropertyName
	 *            of the operation, should be unique
	 * @param entity
	 *            to operate. Usually the observation should be not null
	 */
	public Information(int type, String qosPropertyName, ContextEntity entity) {
		this.type = type;
		this.qosPropertyName = qosPropertyName;
		this.entity = entity;
	}

	public ContextEntity getEntity() {
		return entity;
	}

	public void setEntity(ContextEntity entity) {
		this.entity = entity;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getQosPropertyName() {
		return qosPropertyName;
	}

	public void setQosPropertyName(String qosPropertyName) {
		this.qosPropertyName = qosPropertyName;
	}

	public ContextProperty getResult() {
		return result;
	}

	public void setResult(ContextProperty result) {
		this.result = result;
	}

	private void operationCount() {
		if (result == null || ((Integer) result.getValue()).intValue() == 0)
			result = new ContextProperty(TYPE_COUNT, "methodCount", 1);
		else
			result.setValue(((Integer) result.getValue()).intValue() + 1);

	}

	private void operationOcurrance(String name) {
		result = new ContextProperty(TYPE_OCURRENCE, name, true);
	}

	private void operationInvoke(String methodName, Object value) {
		result = new ContextProperty(TYPE_INVOKE, methodName, value);

	}

	private void operationSum(double num1, double num2) {
		result.setValue(num1 + num2);
	}

	public void executeOperation(Object[] args) {
		switch (type) {
		case TYPE_COUNT:
			operationCount();
			break;
		case TYPE_SUM:
			if (args.length == 2) {
				int num1 = ((Integer) args[0]).intValue();
				int num2 = ((Integer) args[1]).intValue();
				operationSum(num1, num2);
			}
			break;
		case TYPE_OCURRENCE:
			operationOcurrance((String) args[0]);
			break;
		case TYPE_INVOKE:
			operationInvoke((String) args[0], args[1]);
			break;

		default:
			result.setValue(args[0]);
		}
	}

}
