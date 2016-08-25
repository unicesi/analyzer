package kb.api;

import java.util.ArrayList;
import java.util.Calendar;

import org.osoa.sca.annotations.Service;

import policiesLibrary.monitor.MonitoringPolicy;
import sloContractLibrary.QoSContract;


@Service(KBService.class)
public abstract class AKB implements KBService {

	protected ArrayList<MonitoringPolicy> monitoringPolicies;
	
	/**
	 * @uml.property name="sloContrats"
	 * @uml.associationEnd multiplicity="(0 -1)"
	 *                     elementType="sloContractLibrary.QoSContract"
	 */
	protected ArrayList<QoSContract> sloContrats;

	/**
	 * @uml.property name="initDateTime"
	 */
	@SuppressWarnings("unused")
	protected Calendar initDateTime;
	
	@Override
	public String getAliveMessage() {
		return "KB is Alive!";
	}


	@Override
	public QoSContract[] getAnalyzingPolicies() {
		initSLOEntities();
		System.out.println("[KB Service] - Delivering QoSContracts");
		QoSContract[] qosContractArray = null;
		if (sloContrats == null)
			System.out.println("[KB Service] - Contract's array is null");
		else {
			qosContractArray = new QoSContract[sloContrats.size()];
			for (int i = 0; i < qosContractArray.length; i++) {
				qosContractArray[i] = sloContrats.get(i);
			}
		}

		// Time Stamp
		Calendar calendarTimeStamp = Calendar.getInstance();
		//System.out.println("AMC ended at: " + calendarTimeStamp.getTimeInMillis());

		return qosContractArray;
	}
	
	public abstract double[][] getHistorics();

	@Override
	public String[] getPlanningPolicies() {
		System.out
				.println("[09-2011] lcastane: This method is not implemented in this scope");
		return null;
	}

	/**
	 * This method is a service provided by the KB component and required by the
	 * Monitor component
	 */
	@Override
	public MonitoringPolicy[] getMonitoringPolicies() {
		initMonitoringPolicies();
		System.out.println("[KB Service] - Delivering Monitoring Policies");
		MonitoringPolicy[] monitoringPoliciesArray=null;
		if (monitoringPolicies != null) {
			monitoringPoliciesArray = new MonitoringPolicy[monitoringPolicies
					.size()];
			for (int i = 0; i < monitoringPoliciesArray.length; i++) {

				monitoringPoliciesArray[i] = monitoringPolicies.get(i);
			}

		} else
			System.out.println("[KB Service] - Monitoring policies array is null");
		
		// Time Stamp
				Calendar calendarTimeStamp = Calendar.getInstance();
				//System.out.println("KB ended at: " + calendarTimeStamp.getTimeInMillis());
		
		return monitoringPoliciesArray;
		
	}
	
	/**
	 * Initiates KB information. Some user interface is required to load real
	 * information about the monitoring policies.
	 * 
	 */
	protected abstract void initMonitoringPolicies() ;

	protected abstract void initSLOEntities();
}
