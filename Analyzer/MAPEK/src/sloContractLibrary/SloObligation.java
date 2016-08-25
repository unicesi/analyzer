package sloContractLibrary;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SloObligation implements Serializable {

	private String contextEventType;
	private SloPredicate predicate;
	private ContextCondition contextCondition;

	
	
	
	public String getContextEventType() {
		return contextEventType;
	}

	public void setContextEventType(String contextEventType) {
		this.contextEventType = contextEventType;
	}

	

	public SloPredicate getPredicate() {
		return predicate;
	}

	public void setPredicate(SloPredicate predicate) {
		this.predicate = predicate;
	}

	public ContextCondition getContextCondition() {
		return contextCondition;
	}

	public void setContextCondition(ContextCondition contextCondition) {
		this.contextCondition = contextCondition;
	}

	public SloObligation() {

	}

}
