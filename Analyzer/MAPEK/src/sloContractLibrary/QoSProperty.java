package sloContractLibrary;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class QoSProperty implements Serializable {

	public final static String LATENCY = "Latency";
	public final static String THROUGHPUT = "Throughput";
	public final static String CAPACITY = "Capacity";
	public final static String SAFETY = "Safety";
	public final static String INTEGRITY = "Integrity";
	public final static String AVAILABILITY = "Availability";
	public final static String RELIABILITY = "Reliability";
	public final static String MAINTAINABILITY = "Maintainability";
	public final static String CONFIDENTIALITY = "Confidentiality";
	public final static String INTERACT = "Interact";
	public final static String COMPLEX = "Complex";
	public final static String COUPLING_STRENGTH = "Coupling Strength";

	public final static String PERFORMANCE = "Performance";
	public final static String SECURITY = "Security";
	public final static String DEPENDABILITY = "Dependability";

	private String name;
	private ArrayList<SloObligation> obligation;

	public QoSProperty(String name) {
		this.name = name;
		obligation = new ArrayList<SloObligation>();
	}

	public void addObligation(SloObligation obl) {
		obligation.add(obl);
	}

	public String getName() {
		return name;
	}

	public ArrayList<SloObligation> getObligation() {
		return obligation;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setObligation(ArrayList<SloObligation> obligation) {
		this.obligation = obligation;
	}

}
