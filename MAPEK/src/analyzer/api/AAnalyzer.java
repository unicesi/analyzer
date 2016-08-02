package analyzer.api;

import java.util.ArrayList;

import kb.api.KBService;

import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import sloContractLibrary.ContextCondition;
import sloContractLibrary.QoSContract;
import contextLibrary.Diagnosis;
import contextLibrary.IContextModel;
import contextLibrary.Information;

@Service(AnalyzerService.class)
public abstract class AAnalyzer implements AnalyzerService {

	/*
	 * Attributes
	 */
	protected QoSContract[] qosContracts;
	protected Information[] information;
	protected ArrayList<Diagnosis> diagnosis;

	/*
	 * References to other components in the MAPE loop
	 */
	protected KBService kbService;


	@Reference
	public void setKbService(KBService kbService) {
		this.kbService = kbService;
	}

	@Override
	public void reportContextInformation(IContextModel[] contextInformation) {
		/*
		 * This method receives information that is required to be analyzed
		 */
		System.out.println("[Analyzer Service] - Context information received");
		information = (Information[]) contextInformation;

		/*
		 * Step 1: Starts to analyze the information sent by the monitoring
		 * component my matching the with the qosContracts
		 */
		startAnalyzing();

		System.out.println("[Analyzer Service Stopped]");
	}

	
	/**
	 * Performs the analyzing actions
	 */
	protected void startAnalyzing() {
		System.out.println("[Analyzer Service] - Starting Analyzing");
		/*
		 * Step 2: Connects to the AMC component to gather the QoSContracts
		 */
		loadSLOcontracts();

		/*
		 * Step 2.1 : Connects to the KB knowledge to gather the analyzing
		 * policies [lcastane 04/2012] Not implemented. Out of the scope
		 * Attribute type: AnalyzingPolicy Method: loadAnalyzingPolicies();
		 */

		/*
		 * Step 3: Builds the diagnosis objects after analysis
		 */
		buildDiagnosis();

	}
	
	/**
	 * This method connects the Analyzer component to the AMC component to load
	 * the SLO contracts as QoSContract objects
	 */
	protected void loadSLOcontracts() {
		qosContracts = kbService.getAnalyzingPolicies();

			System.out.println("[Analyzer Service] - " + qosContracts.length
					+ " Slo contracts loaded from the AMC component");

	}
	
	/**
	 * This method searches if the name of the information is in any
	 * QoSContract. In that case the qosContract is returned
	 * 
	 * @param qosPropertyName
	 * @param observationName
	 * @return the qosContract
	 */
	protected QoSContract searchInSloContracts(String qosPropertyName, String observationName) {
		/*
		 * [lcastane 21/03/2012] Future extension of this method: The same
		 * Information might be required for more than one qosContract,
		 * therefore this method should search return all the qosContracts where
		 * this Information is found. This method assumes that exists only one
		 * contract for each qosProperty. [Scope limits]
		 */
		for (int i = 0; i < qosContracts.length; i++) {
			QoSContract contract = qosContracts[i];
			if (contract.getName().equals(qosPropertyName)) {
				ContextCondition contCond= contract.getProperty().get(0).getObligation().get(0).getContextCondition();
				for (int j = 0; j < contCond.getObservations().size() ; j++) {
					String obs = (String)contCond.getObservations().get(j);
					if(obs.equals(observationName))
						return contract;
				}
				
			}
		}
		return null;
	}
	
	/**
	 * This method build the Diagnosis objects by matching the informations sent
	 * by the monitor against the QoSContracts gathered from the AMC component
	 */
	protected void buildDiagnosis() {

		System.out.println("[Analyzer Service] - Start Building Diagnosis");
		ArrayList<QoSContract> contracts = new ArrayList<QoSContract>();
		// 1. It searches for the contracts to be analyzed
		for (int i = 0; i < information.length; i++) {
			Information info = information[i];
			// 1.1
			QoSContract contract = searchInSloContracts(info.getQosPropertyName(), info.getEntity().getName());
			if (contract != null && !contracts.contains(contract))
				contracts.add(contract);

		}
		// 2. Analyzes the contracts against the information
		ArrayList<Object> infoToAnalyze = null;
		for (int i = 0; i < contracts.size(); i++) {
			infoToAnalyze = new ArrayList<Object>();
			infoToAnalyze.add(contracts.get(i));
			for (int j = 0; j < information.length; j++) {
				if (information[j].getQosPropertyName().equals(
						contracts.get(i).getName())) {
					infoToAnalyze.add(information[j]);
				}
			}
			Diagnosis diag = contractVsInformation(infoToAnalyze);
			
			diagnosis.add(diag);

		}

	}
	
	/**
	 * This method compares de information delivered by the Monitor against the
	 * QoSContract.
	 * 
	 * @param infoToAnalyze
	 *            where [0] is the contract and [1 to n] is the information
	 *            required to be compared
	 * @return A Diagnosis with false or true for adaptation. Null if info does not match either.
	 * 
	 * specialize this method with the analyzing properties from the KB component
	 */
	protected abstract Diagnosis contractVsInformation(ArrayList<Object> infoToAnalyze);
	
	
	@Override
	public String getAliveMessage() {
		return "Analyzer is Alive!";
	}


}
