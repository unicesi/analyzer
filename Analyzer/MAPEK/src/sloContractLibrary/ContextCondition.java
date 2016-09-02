package sloContractLibrary;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ContextCondition implements Serializable {

	public final static int OPE_OBSERVATION = 1, OPE_AVERAGE = 2, OPE_SUM = 3;

	private ArrayList<String> observations;
	private ArrayList<String> dataTypes;
	private ArrayList<Object> operations;

	public ContextCondition() {
		observations = new ArrayList<String>();
		dataTypes = new ArrayList<String>();
		operations = new ArrayList<Object>();
	}

	public ArrayList<String> getObservations() {
		return observations;
	}

	public void setObservations(ArrayList<String> observations) {
		this.observations = observations;
	}

	public ArrayList<String> getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(ArrayList<String> dataTypes) {
		this.dataTypes = dataTypes;
	}

	public ArrayList<Object> getOperations() {
		return operations;
	}

	public void setOperations(ArrayList<Object> operations) {
		this.operations = operations;
	}

}
