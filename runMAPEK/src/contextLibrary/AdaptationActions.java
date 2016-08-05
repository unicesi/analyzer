package contextLibrary;

@SuppressWarnings("serial")
public class AdaptationActions implements IContextModel{

	/**
	 * Constrains
	 */
	public final static int EXECUTION_TYPE_AFTER =41;
	public final static int EXECUTION_TYPE_BEFORE =42;
	public final static int EXECUTION_TYPE_DURING =43;
	
	
	/**
	 * Attributes
	 */
	private String[] commands;
	private int executionType;
	private int planOrderNumber;
	
	/**
	 * Constructor
	 * @param commands
	 * @param executionType
	 * @param planOrderNumber
	 */
	public AdaptationActions(String[] commands, int executionType,
			int planOrderNumber) {
		this.commands = commands;
		this.executionType = executionType;
		this.planOrderNumber = planOrderNumber;
	}

	public String[] getCommands() {
		return commands;
	}

	public void setCommands(String[] commands) {
		this.commands = commands;
	}

	public int getExecutionType() {
		return executionType;
	}

	public void setExecutionType(int executionType) {
		this.executionType = executionType;
	}

	public int getPlanOrderNumber() {
		return planOrderNumber;
	}

	public void setPlanOrderNumber(int planOrderNumber) {
		this.planOrderNumber = planOrderNumber;
	}
	
		
	
	
}
