package sloContractLibrary;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class QoSContract implements Serializable {

	private String name;
	private ArrayList<QoSProperty> property;

	public QoSContract(String name) {
		this.name = name;
		property = new ArrayList<QoSProperty>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<QoSProperty> getProperty() {
		return property;
	}

	public void setProperty(ArrayList<QoSProperty> property) {
		this.property = property;
	}

	public void addProperty(QoSProperty qosProperty1) {
		this.property.add(qosProperty1);

	}

}
