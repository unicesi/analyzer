package policiesLibrary.monitor;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Observation implements Serializable {

	public final static int TYPE_INVOKE = 1;

	public final static int OPE_COUNT = 2;
	public final static int OPE_AVERAGE = 3;
	public final static int OPE_SUM = 4;

	private int type;
	private int timeSeconds;
	private int operation;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getTimeSeconds() {
		return timeSeconds;
	}

	public void setTimeSeconds(int timeSeconds) {
		this.timeSeconds = timeSeconds;
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public Observation(int type, int timeSeconds, int operation) {
		this.type = type;
		this.timeSeconds = timeSeconds;
		this.operation = operation;
	}

}
