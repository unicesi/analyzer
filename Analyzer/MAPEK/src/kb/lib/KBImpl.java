package kb.lib;

import java.util.ArrayList;
import java.util.Calendar;

import kb.api.AKB;
import kb.dbconn.KBDBPostgres;
import policiesLibrary.monitor.MonitoringPolicy;
import policiesLibrary.monitor.Observation;
import sloContractLibrary.ContextCondition;
import sloContractLibrary.QoSContract;
import sloContractLibrary.QoSProperty;
import sloContractLibrary.SloObligation;
import sloContractLibrary.SloPredicate;

public class KBImpl extends AKB {
	
	private KBDBPostgres dbconn;
	
	public KBImpl() {

		/// Time Stamp
		Calendar calendarTimeStamp = Calendar.getInstance();
		//System.out.println("KB started at: " + calendarTimeStamp.getTimeInMillis());

		System.out.println("[KB Service Started]");
		monitoringPolicies = new ArrayList<MonitoringPolicy>();
		sloContrats = new ArrayList<QoSContract>();
	}

	protected void initMonitoringPolicies() {
		/*
		 * lcastane 07/2011: Replace all these lines for the required
		 * persistence connection in order to load the monitoring policies.
		 */
		System.out.println("[KB Service] - Initializing monitoring policies");
		MonitoringPolicy monitPolicyThroughput = initMonitPolicyThroughput();
		monitoringPolicies.add(monitPolicyThroughput);
	}

	private MonitoringPolicy initMonitPolicyThroughput() {

		System.out
				.println("[KB Service] - Throughput Monitoring Policies <start>");

		/*
		 * The name of the policy is the sum of the qosContract ID and the QoS
		 * Property to be assured C1-Confidentiality C2-Availability
		 * C3-Throughput
		 */
		MonitoringPolicy policyThroughput = new MonitoringPolicy(
				"C3-Throughput");

		/*
		 * ContextCondition that comes from the QoSContract C3 - Throughput at
		 * KB
		 */
		ContextCondition contextCondition = new ContextCondition();
		contextCondition.getObservations().add("DiaMes");
		contextCondition.getObservations().add("Tx/min");
		contextCondition.getDataTypes().add("int");
		contextCondition.getDataTypes().add("int");
		contextCondition.getOperations().add(ContextCondition.OPE_OBSERVATION);
		contextCondition.getOperations().add(ContextCondition.OPE_OBSERVATION);

		policyThroughput.setMonitoringContext(contextCondition);
		policyThroughput.setObservation(new Observation(
				Observation.TYPE_INVOKE, 60, Observation.OPE_COUNT));
		System.out
				.println("[KB Service] - Throughput Monitoring Policies <end>");

		return policyThroughput;
	}

	@Override
	protected void initSLOEntities() {
		QoSContract contractThrougput = initQoSContractThroughput();
		sloContrats.add(contractThrougput);

		System.out.println("[KB Service] Slo Contracts loaded");
	}
	
	private QoSContract initQoSContractThroughput() {

		System.out.println("[KB Service] - QoS Contract Throughput <start>");
		/*
		 * The name of the contract is the sum of the qosContract ID and the QoS
		 * Property to be assured C1-Confidentiality C2-Availability
		 * C3-Throughput
		 */
		QoSContract contractThrougput = new QoSContract("C3-Throughput");

		QoSProperty propertyP1 = new QoSProperty(QoSProperty.THROUGHPUT);
		contractThrougput.addProperty(propertyP1);

		SloObligation obligation = new SloObligation();
		obligation.setContextEventType("Context Event Type Undefined");

		SloPredicate predicate = new SloPredicate();
		predicate.getObservations().add("DiaMes");
		predicate.getObservations().add("Tx/min");

		dbconn = new KBDBPostgres();
		ArrayList dbQuery = dbconn
				.getDataBaseInformation("select * from \"c3predicate\"");

		if (dbQuery.size() > 0) {
//		
//		Integer[][] val = new Integer[30][2];
//		for (int i = 0; i < val.length; i++) {
//			val[i][0] = i+1;
//			val[i][1] = i<15?30:i<20?70:90;
//		}
		
			for (int i = 0; i < dbQuery.size(); i++) {
				String[] val=(String[])dbQuery.get(i);
				int dia = Integer.parseInt(val[0]);
				predicate.getValues().add(dia);
				int tx = Integer.parseInt(val[1]);
				predicate.getValues().add(tx);
			}

			obligation.setPredicate(predicate);

			ContextCondition contextCondition = new ContextCondition();
			contextCondition.getObservations().add("DiaMes");
			contextCondition.getObservations().add("Tx/min");
			contextCondition.getDataTypes().add("int");
			contextCondition.getDataTypes().add("int");
			contextCondition.getOperations().add(
					ContextCondition.OPE_OBSERVATION);
			contextCondition.getOperations().add(
					ContextCondition.OPE_OBSERVATION);
			obligation.setContextCondition(contextCondition);

			propertyP1.addObligation(obligation);

			System.out.println("[KB Service] - QoS Contract Throughput <end>");
		

			return contractThrougput;
		} else {
			
					
			System.out
					.println("[AMC Service] - QoS Contract Throughput could not load SLOContracts from data base");
		
			
			return null;
		}
	}

	@Override
	public void saveHistory(long start, long end, double diff) {
		dbconn = new KBDBPostgres();
		dbconn.saveInHistory(start, end, diff);
	}

	@Override
	public double[][] getHistorics() {
		dbconn = new KBDBPostgres();
		return dbconn.getHistorics();
	}

}
