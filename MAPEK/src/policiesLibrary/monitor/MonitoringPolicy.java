package policiesLibrary.monitor;

import java.io.Serializable;

import sloContractLibrary.*;

@SuppressWarnings("serial")
public class MonitoringPolicy implements Serializable{

	private String name;
	private ContextCondition monitoringContext;
	private Observation observation;

	public MonitoringPolicy(String name) {
		this.name = name;
	}

	public ContextCondition getMonitoringContext() {
		return monitoringContext;
	}

	public void setMonitoringContext(ContextCondition monitoringContext) {
		this.monitoringContext = monitoringContext;
	}

	public Observation getObservation() {
		return observation;
	}

	public void setObservation(Observation observation) {
		this.observation = observation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
