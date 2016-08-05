package sloContractLibrary;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class QoSProperty implements Serializable {
	
	private String name;
	private ArrayList<SloObligation> obligation;
	
	public QoSProperty(String name){
		this.name = name;
		obligation = new ArrayList<SloObligation>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<SloObligation> getObligation() {
		return obligation;
	}

	public void setObligation(ArrayList<SloObligation> obligation) {
		this.obligation = obligation;
	}
	public void addObligation(SloObligation obl){
		obligation.add(obl);
	}
	
	
}
