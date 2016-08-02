package sloContractLibrary;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SloPredicate  implements Serializable{

private ArrayList<Object> observations;
private ArrayList<Object> values;


public SloPredicate(){
	observations = new ArrayList<Object>();
	values = new ArrayList<Object>();
}

public ArrayList<Object> getObservations() {
	return observations;
}
public void setObservations(ArrayList<Object> observations) {
	this.observations = observations;
}
public ArrayList<Object> getValues() {
	return values;
}
public void setValues(ArrayList<Object> values) {
	this.values = values;
}


	
}
